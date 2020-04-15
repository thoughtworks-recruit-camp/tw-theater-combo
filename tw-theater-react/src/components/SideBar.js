import React, {useContext, useState} from "react";
import BaseContext from "../BaseContext";
import FilterContext from "../FilterContext";
import '../styles/SideBar.css';

function SideBar() {
  return (
    <nav id="side-bar">
      <h2>排序方式</h2>
      <hr/>
      <SortingSelect/>
      <h2>分类</h2>
      <hr/>
      <GenreSelect/>
      <hr/>
      <DisplayLimiter/>
    </nav>
  );
}

function SortingSelect() {
  const {filter, updateFilter} = useContext(FilterContext);
  return (
    <ul id="sorting-options">
      {["综合", "随机"].map(sorting => {
          const isSelected = filter.sorting === sorting;
          return (
            <li className={isSelected ? "selected" : "unselected"}
                onClick={() => updateFilter({sorting: sorting})}>
              <span className={`iconfont icon-sort ${isSelected ? "selected-icon" : ""}`}/>
              {sorting}
            </li>);
        }
      )}
    </ul>
  );
}

function GenreSelect() {
  const GENRES = useContext(BaseContext).GENRES;
  const {filter, updateFilter} = useContext(FilterContext);
  const selected = filter.genre;
  return (
    <ul id="genres-list">
      <li className={selected === "ALL" ? "selected" : "unselected"}
          onClick={() => updateFilter({genre: "ALL"})}>
        <span className={`iconfont icon-tags ${selected === "ALL" ? "selected-icon" : ""}`}/>
        {"全部"}
      </li>
      {GENRES.map(genre => {
        return (
          <li className={selected === genre ? "selected" : "unselected"}
              onClick={() => updateFilter({genre: genre})}>
            <span className={`iconfont icon-tag ${selected === genre ? "selected-icon" : ""}`}/>
            {genre}
          </li>);
      })}
    </ul>
  );
}

function DisplayLimiter() {
  const {updateFilter} = useContext(FilterContext);
  const [limit, setLimit] = useState("16");
  const handleChange = (event) => {
    const limit = event.target.value;
    setLimit(limit);
    updateFilter({limit: limit});
  }
  return (
    <p>
      <label htmlFor="limit-select">显示数量</label>
      <select name="show-limit" id="limit-select" value={limit} onChange={handleChange}>
        <option value="16">16部</option>
        <option value="32">32部</option>
        <option value="0">无限制</option>
      </select>
    </p>
  );
}

export default SideBar;