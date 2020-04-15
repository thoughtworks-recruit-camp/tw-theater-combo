import React, {useContext, useEffect, useState} from "react";
import {useLocation} from "react-router";
import BaseContext from "../BaseContext";
import failed from "../img/fail.svg";
import '../styles/Search.css';
import LoadingMask from "./LoadingMask";
import {Link} from "react-router-dom";

function Search() {
  const location = useLocation();
  const API_ROOT = useContext(BaseContext).API_ROOT;
  const [data, setData] = useState([]);
  const [isReady, setReady] = useState(false);
  const keyword = new URLSearchParams(location.search).get("keyword");

  useEffect(() => {
    setReady(false);
    fetch(`${API_ROOT}/search?keyword=${keyword}`)
      .then(res => res.json())
      .then(
        (result) => {
          setData(result);
          document.title = `搜索结果 - ${keyword}`;
        })
      .then(() => setReady(true));
  }, [keyword, API_ROOT]);

  const items = data.length > 0
    ? data.map(value => <Item key={value.id} data={value}/>)
    : (
      <div className="search-fail"><img src={failed} alt="logo"/>
        <p>未找到相关电影!</p>
      </div>
    );

  return !isReady
    ? <LoadingMask/>
    : <Results keyword={keyword} length={data.length} items={items}/>;
}

function Results(props) {
  return (
    <div id="results">
      <div>
        <h2>搜索：<i>{props.keyword}</i></h2>
        {props.length > 0 && (<p>找到<b>{props.length}</b>个相关结果</p>)}
      </div>
      {props.items}
    </div>
  );
}

function Item(props) {
  const API_ROOT = useContext(BaseContext).API_ROOT;
  const data = props.data;
  const splitWithSlash = (s) => s.split(",").join("/");
  const mergeTitles = (titleA, titleB) =>
    titleA === titleB
      ? titleA
      : titleA + "/" + titleB;

  return (
    <div className="search-div">
      <Link to={`./details?id=${data.id}`}>
        <img src={`${API_ROOT}/poster/${data.id}`} alt="poster"/>
      </Link>
      <p className="search-info">
      <span className="info-title">
        <Link to={`./details?id=${data.id}`}>
      {data.title} {data.year}
        </Link>
      </span>
        <span><b>评分:</b> {data.rating}</span>
        <span><i>{splitWithSlash(data.genres)}</i></span>
        <span><i>{mergeTitles(data.title, data.original_title)}</i></span>
        <span><i>{splitWithSlash(data.cast)}</i></span>
        <span className="search-summary">{data.summary}</span>
      </p>
    </div>
  );
}

export default Search;