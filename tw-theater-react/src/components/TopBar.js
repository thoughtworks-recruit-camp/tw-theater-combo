import React from "react";
import logo from '../img/tw-logo.svg';
import '../styles/TopBar.css';
import {Link} from "react-router-dom";

class TopBar extends React.Component {
  render() {
    return (
      <nav id="top-bar">
        <Link to="/" className="logo">
          <img src={logo} alt="logo"/>
        </Link>
        <Link to="/" className="page-title">
          <p>思沃影院 TW Theater</p>
        </Link>
        <div className="search-bar">
          <form action="/search">
            <input type="search" name="keyword" className="search-box" placeholder="搜索电影名"/>
            <input type="submit" className="search-button"/>
          </form>
        </div>
      </nav>
    )
  }
}

export default TopBar;