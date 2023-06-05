import React from 'react';
import { Link } from 'react-router-dom';
import AuthService from "./services/auth.service";

class GlobalNavigation extends React.Component{
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.state = {
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showModeratorBoard: user.roles.includes("ROLE_MODERATOR"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }
  }

  logOut() {
    AuthService.logout();
    this.setState({
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  render(){
    const { currentUser, showModeratorBoard, showAdminBoard } = this.state;

    return(
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <ul>          
          <li><Link to="/home">Home</Link></li>
          <li><Link to="/quiz">Quiz</Link></li>
          <li><Link to="/rules">Rules</Link></li>
          <li><Link to="/aboutus">About Us</Link></li>

          {showModeratorBoard && (
            <li><Link to="/questionlist">Questions</Link></li>
          )}

          {showAdminBoard && (
            <li><Link to={"/admin"}> Admin Board </Link></li>
          )}

          {currentUser && (
            <li><Link to={"/user"}> User </Link></li>
          )}

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li><Link to={"/profile"}> {currentUser.username} </Link></li>
            <li><a href="/login" onClick={this.logOut}> LogOut </a></li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li><Link to={"/login"}> Login </Link></li>

            <li><Link to={"/register"}> Sign Up </Link></li>
          </div>
        )}
        </ul>
      </nav>
    )
  }
}

export default GlobalNavigation;