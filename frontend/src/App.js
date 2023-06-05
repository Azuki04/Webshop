import React from "react";
import './App.css';
import {
  Routes,
  Route,
  Outlet
} from 'react-router-dom'

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AboutUs from './components/aboutus.component';
import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";

import Rules from "./Rules"
import QuestionList from "./QuestionList"
import NotFound from "./NotFound"
import GlobalNavigation from "./GlobalNavigation"
import BoardUser from "./components/board-user.component";
import GameSelection from "./GameSelection";

//import BoardUser from "./components/board-user.component";
//import BoardModerator from "./components/board-moderator.component";
//import BoardAdmin from "./components/board-admin.component";

const App = () => {  
  return (
    <div>
      <GlobalNavigation />
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/user" element={<BoardUser />} />
          <Route path="/quiz" element={<GameSelection />} />
          <Route path="/rules" element={<Rules />} />
          <Route path="/aboutus" element={<AboutUs />} />
          <Route path="/questionlist" element={<QuestionList />} />
          <Route path="*" element={<NotFound />}/>
        </Route>      
    </Routes>
    </div>
  );
}


const Layout = () => {
  return (
    <div className="App">      
      <div className="content">
        <header className="App-header">
          <h1>Welcome to the Wiss-Quiz</h1>
          <hr/>
          <Outlet />
          <hr/>
        </header>
      </div>
    </div>
  );
}

export default App;