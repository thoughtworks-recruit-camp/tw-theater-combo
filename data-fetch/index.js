const [http, EventEmitter, fs] = [require("http"), require("events").EventEmitter, require('fs')];

const REMOTE_ROOT = "http://api.douban.com/v2/movie";
const KEY = "0df993c66c0c636e29ecbb5344252a4a";

const [moviesDb, imagesDb, photosLinks, photosDb] = [new Map(), new Map(), new Map(), new Map()];
const dataChunks = [];
let detailsCount = 0;
let imageCount = 0;
let photosCount = 0;
let totalPhotos = 0;
let photosHandler = new EventEmitter();
let finishHandler = new EventEmitter();
let jobHandler = new EventEmitter();

for (let index = 0; index < 250; index += 10) {
  http.get(`${REMOTE_ROOT}/top250?start=${index}&count=10&apikey=${KEY}`, (res) => {
    let rawData = "";
    res.setEncoding("utf-8");
    res.on("data", (chunk => {
      rawData += chunk;
    }));
    res.on("end", () => mergeData(JSON.parse(rawData)));
  });
}

function mergeData(data) {
  dataChunks.push(data);
  process.stdout.write(`\rTop 250 basics loading progress: ${dataChunks.length * 10}/250`);
  if (dataChunks.length === 25) {
    process.stdout.write(" ...completed!\n");

    dataChunks.sort((a, b) => a.start - b.start);
    dataChunks.map(chunk => (chunk.subjects)).flat().forEach((element => moviesDb.set(element.id, element)));

    for (let id of moviesDb.keys()) {
      http.get(`${REMOTE_ROOT}/subject/${id}?apikey=${KEY}`, (res => {
        let rawData = "";
        res.setEncoding("utf-8");
        res.on("data", (chunk => {
          rawData += chunk;
        }));
        res.on("end", () => {
          Object.assign(moviesDb.get(id), JSON.parse(rawData));
          moviesDb.get(id).id = id;
          // moviesDb.get(id).summary = JSON.parse(rawData).summary;
          const photos = JSON.parse(rawData).photos.map(data => data.cover);
          moviesDb.get(id).photos = photos;
          photosLinks.set(id, photos);
          jobHandler.emit("details");
        });
      }));
      jobHandler.emit("image");
      // http.get(moviesDb.get(id).images.large, res => {
      //   let data = Buffer.from([]);
      //   res.on("data", chunk => {
      //     data = Buffer.concat([data, chunk]);
      //   });
      //   res.on("end", () => {
      //     imagesDb.set(id, data);
      //     jobHandler.emit("image");
      //   });
      // });
    }
  }
}

const detailsMessage = (detailsCount, imageCount) => {
  return `\rTop 250 data loading progress: details ${detailsCount}/250 | images ${imageCount}/250`;
};

jobHandler.on("details", () => {
  process.stdout.write(detailsMessage(++detailsCount, imageCount));
  if (detailsCount === 250 && imageCount === 250) {
    process.stdout.write(" ...completed!\n");
    finishHandler.emit("finished");
  }
});
jobHandler.on("image", () => {
  process.stdout.write(detailsMessage(detailsCount, ++imageCount));
  if (detailsCount === 250 && imageCount === 250) {
    process.stdout.write(" ...completed!\n");
    finishHandler.emit("finished");
  }
});
finishHandler.on("finished", () => {
  const allData = Array.from(moviesDb.values());
  const partialData = allData.map(value => (
    {
      id: parseInt(value.id),
      title: value.title,
      original_title: value.original_title,
      rating: value.rating.average,
      genres: value.genres,
      year: value.year,
      pubdates: value.pubdates,
      image: value.images.medium,
      summary: value.summary,
      durations: value.durations,
      photos: value.photos,
      album: `https://movie.douban.com/subject/${value.id}/all_photos`,
      cast: value.casts.map(cast => cast.name).slice(0, 5)
    }
  ));
  const photoLinks = new Map();
  photosLinks.forEach((arr, key) =>
    arr.map((value, index) =>
      photoLinks.set(`${key}-${index + 1}`, value))
  );
  totalPhotos = photoLinks.size;
  for (let [id, link] of photoLinks.entries()) {
    link = "http" + link.substring(5);
    http.get(link, res => {
      let data = Buffer.from([]);
      res.on("data", chunk => {
        data = Buffer.concat([data, chunk]);
      });
      res.on("end", () => {
        photosDb.set(id, data);
        fs.writeFileSync(`.\\photos\\${id}.jpg`, data);
        jobHandler.emit("photo");
      });
    });
  }


  fs.writeFileSync(".\\allData.json", JSON.stringify(allData, null, 2));
  fs.writeFileSync(".\\partialData.json", JSON.stringify(partialData, null, 2));
  imagesDb.forEach((value, key) =>
    fs.writeFileSync(`.\\imgs\\${key}.jpg`, value));
});
jobHandler.on("photo", () => {
  process.stdout.write((`\rPhotos progress: ${++photosCount}/${totalPhotos}`));
  if (photosCount === totalPhotos) {
    process.stdout.write(" ...completed!\n");
    photosHandler.emit("finished");
  }
});
photosHandler.on("finished", () => {
  process.stdout.write("finished");

})

module.exports = {finishHandler, moviesDb, imagesDb};
