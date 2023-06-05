import React, { useState } from "react"
import './GameSelection.css';
import { getTopics } from './CategoriesApi';
import GameSession from "./GameSession";

/**
* handles a set of Questions
*/
export default function GameSelection() {
    
    const [catid, setCatid, getCatid] = useState("-1")
    const [content, setContent] = useState(renderCategoriesSelection());



    function setSelection(e){
        setCatid(e.target.value)
        switch (e.target.value){
            case "-1": setContent(renderCategoriesSelection()); break;
            default: setContent(<GameSession catId = { e.target.value } />); break;
        }        
    }

    return(
        <div>
            <h4>Game Selection</h4>
            <div>Cat-Id: { catid }</div>
            <div>{ content }</div>
        </div>        
                
    );

    function renderCategoriesSelection(){
        let items = getTopics().map((val) => {
            return( <li key={"catid_"+val.id} value={ val.id } onClick={ setSelection }> 
                {val.name.toUpperCase()} </li>)
        })
        return (
            <div>
                <h4>Choose your topic</h4>
                <ul className="topics">{ items }</ul>
            </div>        
        )
    }
}