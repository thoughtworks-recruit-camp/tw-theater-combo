import React, {useState} from "react";
import SideBar from "./SideBar";
import Gallery from "./Gallery";
import FilterContext from "../FilterContext";

function Home() {
  const [filter, setFilter] = useState({
    genre: "ALL",
    sorting: "综合",
    limit: 16,
  });
  const updateFilter = (update) => {
    setFilter(Object.assign({}, filter, update));
  }
  document.title = "TW Theater - 思沃影院";
  return (
    <FilterContext.Provider value={{filter, updateFilter}}>
      <div>
        <SideBar/>
        <Gallery/>
      </div>
    </FilterContext.Provider>
  );
}

export default Home;