import React from "react";
import "./css/GlobalFooter.css";
import { Button } from "./Button";

class GlobalFooter extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      emailId: "",
    };
    this.changeEmailIdHandler = this.changeEmailIdHandler.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    let emailId = { emailId: this.state.emailId };
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(emailId),
    };

    fetch("http://localhost:8080/api/newslatter", requestOptions)
      .then((response) => response.json())
      .then((jsonData) => {
        // wir überprüfen die ID in der Konsole
        console.log(jsonData);
      });
    alert(
      "You have successfully registered. Have fun cleaning up your spam box"
    );
    this.setState({
      emailId: "",
    });
  }

  changeEmailIdHandler = (event) => {
    this.setState({ emailId: event.target.value });
  };

  render() {
    return (
      <div className="footer-container">
        <div className="footer-subscription">
          <p className="footer-heading">Sign up for discounts and news</p>
          <p className="footer-text">You can unsubscribe at any time.</p>

          <form>
            <input
              className="footer-input"
              name="emailId"
              type="email"
              value={this.state.emailId}
              onChange={this.changeEmailIdHandler}
              placeholder="Your Email"
            />
            <Button
              buttonStyle="btn--submit"
              buttonSize="btn--medium"
              onClick={this.handleSubmit}
            >
              Subscribe
            </Button>
          </form>
        </div>
        <div className="copyright">
          <div className="copyright-wrap">
            <small className="text-color">LB_Sy_Viet © 2022</small>
          </div>
        </div>
      </div>
    );
  }
}

export default GlobalFooter;
