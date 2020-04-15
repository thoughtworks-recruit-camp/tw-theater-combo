import React, {useContext, useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import BaseContext from "../BaseContext";
import FilterContext from "../FilterContext";
import LoadingMask from "./LoadingMask";
import '../styles/Gallery.css';

function Gallery() {
  const sortingMap = new Map([
    ["综合", "top"],
    ["随机", "random"]]);
  const {filter} = useContext(FilterContext);
  const [data, setData] = useState([]);
  const [isReady, setReady] = useState(false);

  const url = `${(useContext(BaseContext).API_ROOT)}/movies?genre=${filter.genre}&sorting=${sortingMap.get(filter.sorting)}&limit=${filter.limit}`;

  useEffect(() => {
    setReady(false);
    fetch(url)
      .then(r => r.json())
      .then(data => setData(data))
      .then(() => setReady(true));
  }, [url]);
  return !isReady
    ? <LoadingMask/>
    : (
      <section id="gallery">
        {data.map(value => <Tile key={value.id} data={value}/>)}
      </section>
    );
}

function Tile(props) {
  const API_ROOT = useContext(BaseContext).API_ROOT;
  const {genres, id, rating, summary, title, year} = props.data;
  return (
    <article className="movie-tile">
      <img src={`${API_ROOT}/poster/${id}`} alt={title} width="200px" height="300px"/>
      <span className="rating-tag">豆瓣评分: {rating}</span>
      <div className="brief-box">
        <ul>
          <li>类型: {genres.split(",").join("/")}</li>
          <li>年代: {year}</li>
          <li>
            <p>
              {summary.replace("\n", "")}
            </p>
          </li>
        </ul>
        <DetailLink className="details-button" id={id} desc="查看详情"/>
      </div>
      <DetailLink id={id} desc={<h3>{title}</h3>}/>
    </article>
  );
}

function DetailLink(props) {
  return (
    <Link to={`/details?id=${props.id}`} className={props.className} target="_blank" rel="noopener noreferrer">
      {props.desc}
    </Link>
  );
}

export default Gallery;