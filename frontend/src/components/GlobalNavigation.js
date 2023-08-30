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

class GlobalNavigation extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.state = {
      showModeratorBoard: false,
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
    if (user) {
      this.setState({
        currentUser: user,
        showModeratorBoard: user.roles.includes("ROLE_MODERATOR"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }


    const config = {
      headers: authHeader()
    };

    // API-Aufruf, um Warenkorbdaten abzurufen
    axios.get(process.env.REACT_APP_API_URL + "/cart", config)
        .then(response => response.data)
        .then(data => {
          // Die Warenkorbpositionen und Gesamtkosten aus den API-Daten extrahieren
          const cartItems = data.cartItems || [];
          const totalCost = data.totalCost || 0;

          // Zustand aktualisieren
          this.setState({ cartItems, totalCost });
        })
        .catch(err => {
          console.log(err);
        });


  }

  getTotalProductCount() {
    //  return this.state.cartItems.reduce((total, cartItem) => total + cartItem.quantity, 0);
    return this.state.cartItems.length;
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
            {(currentUser) && (
                <li>
                  <Link to="/products">My ads</Link>
                </li>
            )}
            {(showModeratorBoard || showAdminBoard) && (
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
            {(currentUser || showModeratorBoard || showAdminBoard) && (
                <li>
                  <Link to="/add">Create</Link>
                </li>
            )}
            {(currentUser || showModeratorBoard || showAdminBoard) && (
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
