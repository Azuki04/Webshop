import React from "react"
import './GameSession.css';
import Question from "./Question.js"

/**
* handles a set of Questions
*/
class GameSession extends React.Component{

    constructor(props){
      super(props)
      this.state = {
        cat_id: props.catId,
        score: 0,
        index: 0,
        questions: typeof props.questions==='undefined' ? [] : props.questions,
        correct: null,
      }
      this.fetchAnswer = this.fetchAnswer.bind(this)
      this.nextQuestion =this.nextQuestion.bind(this)
      this.componentDidMount = this.componentDidMount.bind(this)
    }

    componentDidMount(){
      if(this.state.questions.length === 0 && this.state.cat_id > 0 ){
        fetch(process.env.REACT_APP_API_URL + "/quiz?cat_id="+ this.state.cat_id)
          .then(response => { console.log(response); return response.json()})
          .then(data => this.setState({ questions: data }))
          .catch(err => { console.log(err) })
      }
      else {
        console.log("nothing to do")
      }
    }

    fetchAnswer(answer){
      if (this.state.correct === null){
        const currentCorrectAnswer = this.state.questions[this.state.index].answers.filter(function(x){ return x.correct===true})[0].answer
        let correct = false
        let points = 0;
        answer.target.classList.add('wrong')

        if (currentCorrectAnswer === answer.target.innerHTML){
          correct = true
          points = 100
          answer.target.classList.add('correct')
        }

        this.setState({
          correct: correct,
          score: this.state.score + points
        })
      }
    }

    nextQuestion(){
      this.setState({
        index: this.state.index+1,
        correct: null,
      })
    }

    render(){
      console.log("Q-Index:" + this.state.index)

      let feedback = ""
      if (this.state.correct !== null){
        feedback = <div className="feedback">
          <h4>{ this.state.correct?"Hurra":"Ohh nein" }</h4>
          <button onClick={ this.nextQuestion }>Next</button>
        </div>
      }

      let q = ""
      if (this.state.index < this.state.questions.length){
        q = <Question
        key = { this.state.index } //extrem wichtig für refresh beim weiterschalten
        question = { this.state.questions[this.state.index].question }
        answers = { this.state.questions[this.state.index].answers }
        callback = { this.fetchAnswer }
      />} else if (this.state.questions.length === 0){
        q = <div>
          <h1>Error fetching questions</h1>
          <h2>Please fix me</h2>
        </div>
      } else {
        q = <div>
          <h1>Congrats, you made it!</h1>
          <h2>No more questions</h2>
        </div>
      }
      return (
        <div className="game-session">
          <div className="session-status">
            <span className="left">Question: { Math.min(this.state.questions.length, this.state.index+1) } / { this.state.questions.length }</span>
            <span className="right">Score: { this.state.score }</span>
          </div>
          { q }
          <div>
            { feedback }
          </div>
        </div>
      )
    }

}

export default GameSession
