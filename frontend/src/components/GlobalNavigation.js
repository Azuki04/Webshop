import React from "react";
import Menu from "./svg/bars-solid.svg";
import Close from "./svg/times-solid.svg";
import CartIcon from "./svg/shopping-cart-solid.svg";
import User from "./svg/user.svg";
import { Link } from "react-router-dom";
import "./css/GlobalNavigation.css";
import Auth from "./services/Auth";

class GlobalNavigation extends React.Component {
  state = {
    toggle: false,
  };

  menuToggle = () => {
    this.setState({ toggle: !this.state.toggle });
  };

  componentDidMount() {
    const user = Auth.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showModeratorBoard: user.roles.includes("ROLE_MODERATOR"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }
  }

  logOut() {
    Auth.logout();
    this.setState({
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  render() {
    const { toggle } = this.state;
    const { currentUser, showModeratorBoard, showAdminBoard } = this.state;
    return (
      <header>
        <div className="menu" onClick={this.menuToggle}>
          <img src={Menu} alt="" width="20" />
        </div>
        <div className="logo">
          <h1>
            <Link to="/">Shop.ch</Link>
          </h1>
        </div>
        <nav>
          <ul className={toggle ? "toggle" : ""}>
            <li>
              <Link to="/">Home</Link>
            </li>
            {currentUser || showModeratorBoard || showAdminBoard && (
                <li>
                  <Link to="/products">my_Products</Link>
                </li>
            )}
            <li>
              <Link to="/contact">Contact</Link>
            </li>
            <li>
              <Link to="/about">About</Link>
            </li>
            {currentUser || showModeratorBoard || showAdminBoard && (
                <li>
                  <Link to="/add">Create</Link>
                </li>
            )}
            <li className="close" onClick={this.menuToggle}>
              <img src={Close} alt="" width="20" />
            </li>
          </ul>
          {currentUser ? (
              <ul>
                <div className="nav-user">
                  <Link to="/profile">
                    <img src={User} alt="user" width="25" />
                  </Link>
                </div>
                <li><a href="/login" onClick={this.logOut}> LogOut </a></li>
              </ul>
          ) : (
              <ul>
                <li><Link to={"/login"}> Login </Link></li>

                <li><Link to={"/register"}> Sign Up </Link></li>
              </ul>
          )}

          <div className="nav-cart">
            <span>0</span>
            <Link to="/cart">
              <img src={CartIcon} alt="" width="20" />
            </Link>
          </div>
        </nav>
      </header>
    );
  }
}

export default GlobalNavigation;
