import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";

import "./components/css/index.css";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
document.title = 'Shop'
root.render(
  <BrowserRouter>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </BrowserRouter>
);
