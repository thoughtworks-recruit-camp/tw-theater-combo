import React, {useContext, useEffect, useState} from "react";
import {useLocation} from "react-router";
import BaseContext from "../BaseContext";
import LoadingMask from "./LoadingMask";
import '../styles/Details.css';
import {Link} from "react-router-dom";

function Details() {
  const location = useLocation();
  const API_ROOT = useContext(BaseContext).API_ROOT
  const [data, setData] = useState([]);
  const [isReady, setReady] = useState(false);
  const id = new URLSearchParams(location.search).get("id");
  const title = data.title;

  useEffect(() => {
    setReady(false);
    fetch(`${API_ROOT}/details/${id}`)
      .then(res => res.json())
      .then(
        (data) => {
          setData(data);
          document.title = `影片详情 - ${title}`;
        })
      .then(() => setReady(true));
  }, [title, id, API_ROOT]);
  if (!isReady) {
    return <LoadingMask/>
  } else {
    return (
      <div id="details">
        <Title data={data}/>
        <Info data={data}/>
        <h3>剧情简介</h3>
        <Summary data={data}/>
        <h3>相似电影推荐</h3>
        <Recommend data={data}/>
      </div>
    );
  }
}

function Title(props) {
  const data = props.data;
  const mergeTitles = (titleA, titleB) =>
    titleA === titleB
      ? titleA
      : titleA + "/" + titleB;
  return (
    <h2>
      {mergeTitles(data.title, data.original_title)}{data.year}
    </h2>
  );
}

function Info(props) {
  const API_ROOT = useContext(BaseContext).API_ROOT;
  const data = props.data;
  return (
    <div className="info">
      <img className="poster" src={`${API_ROOT}/poster/${data.id}`} width="300px" alt={data.title}/>
      <InfoContent data={data}/>
      <InfoPhotos data={data}/>
    </div>
  )
}

function InfoContent(props) {
  const data = props.data;
  return (
    <div className="info-text">
      <span><b>类型</b>: {data.genres.split(",").join("/")}</span>
      <span><b>主演:</b> {data.cast.split(",").join("/")}</span>
      <span><b>上映日期:</b> <br/>{data.pubdates.split(",").join("/\n")}</span>
      <span><b>片长:</b><br/> {data.durations.split(",").join("/")}</span>
      <span><b>豆瓣评分:</b> {data.rating}</span>
    </div>
  )
}

function InfoPhotos(props) {
  const API_ROOT = useContext(BaseContext).API_ROOT;
  const data = props.data;
  console.log(data);
  if (data.photos) {
    const photos = data.photos.split(",")
      .map((photo, index) => (<Photo url={`${API_ROOT}/photo/${data.id}/${index + 1}`}/>));
    return (
      <div className="photos">
        <div className="photo-header">
          <h2>剧照</h2>`
          <p>
            <a href={data.album}>
              {">查看更多<"}
            </a>
          </p>
        </div>
        {photos}
      </div>
    );
  } else {
    return null;
  }
}

function Photo(props) {
  const url = props.url;
  return (
    <div className="photo-container">
      <img src={url} width="160px" alt={url}/>
    </div>)
}

function Summary(props) {
  return (
    <p className="summary">
      {props.data.summary}
    </p>
  )
}

function Recommend(props) {
  const data = props.data;
  let recItems = null;
  if (data.recommends) {
    recItems = data.recommends.map(item => (
      <RecItem data={item}/>
    ));
  }
  return (
    <div className="recommends">
      {recItems}
    </div>
  )
}

function RecItem(props) {
  const API_ROOT = useContext(BaseContext).API_ROOT;
  const data = props.data;
  return (
    <div className="recommend-items">
      <Link to={`/details?id=${data.id}`}>
        <img src={`${API_ROOT}/poster/${data.id}`} alt="poster" width="160px" height="240px"/>
      </Link>
      <p>
        <Link to={`/details?id=${data.id}`}>
          {data.title}
        </Link>
      </p>
    </div>
  )
}

export default Details;