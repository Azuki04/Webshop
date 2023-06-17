import React from "react";

import { Outlet } from "react-router-dom";

import GlobalNavigation from "./components/GlobalNavigation";
import Section from "./components/Section";
import GlobalFooter from "./components/GlobalFooter";
function App(props) {
  return (
    <div className="app">
      <GlobalNavigation />
      <Outlet />
      <Section />
      <GlobalFooter />
    </div>
  );
}

export default App;
