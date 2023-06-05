import React from "react"

import Button from './Button.js'


class Question extends React.Component {
  constructor(props){
    super(props)
    this.state = {
      question: props.question,
      answers: props.answers,
      callback: props.callback,
    }
  }

  render() {
    const buttons = this.state.answers.map((answer) =>
      <Button
        label = { answer.answer }
        key = { answer.answer } //identifies each object
        onClick = { this.state.callback }
      />
    )

    return (
      <div className="question">
          <h2>{ this.state.question }</h2>
          <img src="img/question_smiley.png" alt="" width="150px"/>
          <hr/>
          <div className="button-bar">
            { buttons }
          </div>
      </div>
    )
  }
}

export default Question
