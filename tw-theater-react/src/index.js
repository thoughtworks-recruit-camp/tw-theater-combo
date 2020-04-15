import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router} from 'react-router-dom';
import {Switch, Route} from 'react-router';
import * as serviceWorker from './serviceWorker';
import Home from "./components/Home";
import Search from "./components/Search";
import TopBar from "./components/TopBar";
import Details from "./components/Details";
import './styles/index.css';
import './styles/iconfont/iconfont.css';


ReactDOM.render(
  <React.StrictMode>
    <Router>
      <Switch>
        <Route path={"/search"}>
          <TopBar/>
          <Search/>
        </Route>
        <Route path={"/details"}>
          <TopBar/>
          <Details/>
        </Route>
        <Route path={"/"}>
          <TopBar/>
          <Home/>
        </Route>
      </Switch>
    </Router>
  </React.StrictMode>,
  document.getElementById('root')
);
serviceWorker.unregister();
