import React from "react";
import { Link } from "react-router-dom";

import "../css/ProductDetails.css";
import "../css/Cart.css";
import AuthService from "../services/Auth";
import axios from "axios";
import authHeader from "../services/Auth-header";
import GlobalNavigation from "../GlobalNavigation";
import Auth from "../services/Auth";
import GlobalStorageContext from "../services/GlobalStorage";

class Cart extends React.Component {
    static contextType = GlobalStorageContext;
    constructor(props) {
        super(props);

        this.state = {
            cartItems: [], // Hier werden die Warenkorbpositionen gespeichert
            totalCost: 0, // Hier wird die Gesamtkosten gespeichert
            currentUser: Auth.getCurrentUser(),
            hasNotCustomerRole: false
        };
    }

    componentDidMount() {
        // Prüfen, ob der aktuelle Benutzer die Rolle "ROLE_CUSTOMER" hat
        if (this.state.currentUser === null) {
            this.setState({ hasNotCustomerRole: true });
        } else if (this.state.currentUser.roles.includes("ROLE_ADMIN") ||this.state.currentUser.roles.includes("ROLE_SELLER")) {
            this.setState({ hasNotCustomerRole: true });
        }

        // JWT-Token aus dem lokalen Speicher abrufen
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

    updateCartItemQuantity(cartItemId, newQuantity) {
        const cartItem = this.state.cartItems.find(item => item.id === cartItemId);

        if (newQuantity >= 0 && newQuantity <= cartItem.product.stock) {
            const payload = {
                id: cartItemId,
                productId: cartItem.product.id,
                quantity: newQuantity
            };

            axios.put(process.env.REACT_APP_API_URL + `/cart/${cartItemId}`, payload, {
                headers: authHeader()
            })
                .then(() => {
                    this.fetchCartData();

                })
                .catch(error => {
                    console.error("Error updating cart item quantity:", error);
                });
        }
    }

    deleteCartItem(cartItemId) {
        axios.delete(process.env.REACT_APP_API_URL + `/cart/${cartItemId}`, {
            headers: authHeader()
        })
            .then(() => {
                // After successful deletion, fetch updated cart data
                this.fetchCartData();
            })
            .catch(error => {
                console.error("Error deleting cart item:", error);
            });
    }



    fetchCartData() {
        const config = {
            headers: authHeader()
        };

        axios.get(process.env.REACT_APP_API_URL + "/cart", config)
            .then(response => response.data)
            .then(data => {
                const cartItems = data.cartItems || [];
                const totalCost = data.totalCost || 0;
                this.setState({ cartItems, totalCost });

                this.deleteProductFromCart(cartItems);
            })
            .catch(err => {
                console.log(err);
            });
    }

    deleteProductFromCart(productId) {
        const { setCartItems } = this.context;

        // Aktualisiere das cartItems-Array im Kontext mit den verbleibenden Produkten
        setCartItems(productId);
    }



    render() {
    const {cartItems, totalCost, hasNotCustomerRole} = this.state;
    if(hasNotCustomerRole){
        return (
            <div>
                <h2 style={{ margin: "100px", textAlign: "center" }}>No products</h2>
                <div style={{ margin: "40px", textAlign: "center" }}>Please log in as a customer to add the product to your shopping cart</div>
                <div className="login">
                    <Link to="/login">Login</Link>
                    <Link to="/register">Sign Up</Link>
                </div>
            </div>

        );
    }
    if (cartItems.length === 0) {
      return (
              <h2 style={{ margin: "100px", textAlign: "center" }}>No products</h2>
      );
    } else {
      return (
        <div>
          {cartItems.map((cartItem) => (
            <div className="details cart" key={cartItem.product.id}>
                <img src={cartItem.product.src} alt="" />
              <div className="box">
                <div className="row">
                  <h2>{cartItem.product.title}</h2>
                  <span>CHF {cartItem.product.price * cartItem.quantity}.00</span>
                </div>
                <p>{cartItem.product.description}</p>
                <p>{cartItem.product.content}</p>
                <div className="amount">
                    <button
                        className="count"
                        onClick={() => this.updateCartItemQuantity(cartItem.id, cartItem.quantity - 1)}
                    >
                        -
                    </button>
                    <span>{cartItem.quantity}</span>
                    <button
                        className="count"
                        onClick={() => this.updateCartItemQuantity(cartItem.id, cartItem.quantity + 1)}
                    >
                        +
                    </button>
                  <span>{cartItem.product.stock} in stock</span>
                </div>
              </div>
                <div className="delete" onClick={() => this.deleteCartItem(cartItem.id)}>X</div>
            </div>
          ))}
          <div className="total">
            <Link to="/payment">Payment</Link>
            <h3>Total: CHF {totalCost}.00</h3>
          </div>
        </div>
      );
    }
  }
}

export default Cart;
