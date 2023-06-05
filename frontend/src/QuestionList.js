import React, { useState, useEffect } from "react"
import Button from "./Button"

const QuestionList = (props) => {

    const [questions, setQuestions] = useState([])

    useEffect(()=>{
        console.log("question fun mounted")
        loadDataAsync()        
    },[])

    const loadDataAsync = async() => {
        setQuestions([])
        if (localStorage.getItem("user")){
            // fetch the jwt token
            const token = JSON.parse(localStorage.getItem("user")).accessToken;
            try {
                fetch(process.env.REACT_APP_API_URL+"/question", {
                        // add JWT token to request
                        headers: {Authorization: "Bearer "+ token} 
                    })
                    .then(response => { console.log(response); return response.json()})
                    .then(data => setQuestions(data))
                    .catch(err => {console.log(err)})
                } 
                catch(ex){
                    console.log("Mistigkeit: "+ex)
                }
        } else {
            console.log("not login, no data...")
        }
    }

    useEffect(()=>{
        console.log("questions updated")
    },[questions])

    const onQuestionsClick = (e) => {
        console.log(e.target.attributes["data"].value )
    }

    const renderQuestions = () => {
        return questions.map((question)=>{
            return(
                <tr key={"qid_"+question.id} >
                    <td>{question.id}</td>
                    <td>{question.question} 
                        <button onClick={ onQuestionsClick } data = { question.id } > Edit </button>
                    </td>
                    <td>
                        <ul>
                        {question.answers.map((answer)=>{
                            return(
                                <li key={"aid_"+answer.id}>{answer.answer}</li>
                            );
                        })}
                        </ul>
                    </td>
                </tr>
            );
        });
    }

    return (
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Question</th>
                        <th>Answers</th>
                    </tr>
                </thead>
                <tbody>
                    { renderQuestions() }
                </tbody>
            </table>
        );
}

export default QuestionList;