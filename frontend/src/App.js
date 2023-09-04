import React from "react";

import {Outlet} from "react-router-dom";

import GlobalNavigation from "./components/GlobalNavigation";
import Section from "./components/Section";
import GlobalFooter from "./components/GlobalFooter";
import {GlobalStorageProvider} from "./components/services/GlobalStorage";

function App(props) {
    return (
        <div className="app">
            <GlobalStorageProvider>
                <GlobalNavigation/>
                <Outlet/>
                <Section/>
                <GlobalFooter/>
            </GlobalStorageProvider>
        </div>
    );
}

export default App;
