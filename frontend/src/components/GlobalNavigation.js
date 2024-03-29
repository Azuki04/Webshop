import React from "react";
import Menu from "./svg/bars-solid.svg";
import Close from "./svg/times-solid.svg";
import CartIcon from "./svg/shopping-cart-solid.svg";
import User from "./svg/user.svg";
import { Link } from "react-router-dom";
import "./css/GlobalNavigation.css";
import Auth from "./services/Auth";
import authHeader from "./services/Auth-header";
import axios from "axios";
import GlobalStorageContext from "./services/GlobalStorage";

class GlobalNavigation extends React.Component {
  static contextType = GlobalStorageContext;
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.state = {
      showSellerBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
      cartItems: [], // Hier werden die Warenkorbpositionen gespeichert
      totalCost: 0, // Hier wird die Gesamtkosten gespeichert
    };
  }

  state = {
    toggle: false,
  };

  menuToggle = () => {
    this.setState({ toggle: !this.state.toggle });
  };

  componentDidMount() {
    const user = Auth.getCurrentUser();
    const { cartItems } = this.context;
    console.log(cartItems);

    if (user) {
      this.setState({
        currentUser: user,
        showSellerBoard: user.roles.includes("ROLE_SELLER"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }

  }

  getTotalProductCount() {
    const { cartItems } = this.context;
    //  return this.state.cartItems.reduce((total, cartItem) => total + cartItem.quantity, 0);
    return cartItems.length;
  }


  logOut() {
    Auth.logout();
    this.setState({
      showSellerBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  render() {
    const { toggle } = this.state;
    const { currentUser, showSellerBoard, showAdminBoard } = this.state;
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
            {(showSellerBoard) && (
                <li>
                  <Link to="/products">My ads</Link>
                </li>
            )}
            {(showAdminBoard) && (
                <li>
                  <Link to="/admin">Dashboard</Link>
                </li>
            )}
            <li>
              <Link to="/contact">Contact</Link>
            </li>
            <li>
              <Link to="/about">About</Link>
            </li>
            {(showSellerBoard) && (
                <li>
                  <Link to="/add">Create</Link>
                </li>
            )}
            {(currentUser || showSellerBoard || showAdminBoard) && (
                <li>
                  <li><Link to="/login" onClick={this.logOut}> LogOut </Link></li>
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
              </ul>
          ) : (
              <ul>
                <li><Link to={"/login"}> Login </Link></li>

                <li><Link to={"/register"}> Sign Up </Link></li>
              </ul>
          )}

          <div className="nav-cart">
            <span>{this.getTotalProductCount()}</span>
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
