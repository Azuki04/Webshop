import React from "react";
import { Link } from "react-router-dom";

import "../css/ProductDetails.css";
import "../css/Cart.css";

class Cart extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      cart: [],
    };
  }

  render() {
    const { cart, total } = this.state;
    if (cart.length === 0) {
      return (
        <h2 style={{ margin: "100px", textAlign: "center" }}>No products</h2>
      );
    } else {
      return (
        <div>
          {cart.map((product) => (
            <div className="details cart" key={product.id}>
              <img src={product.src} alt="" />
              <div className="box">
                <div className="row">
                  <h2>{product.title}</h2>
                  <span>CHF {product.price * product.count}.00</span>
                </div>
                <p>{product.description}</p>
                <p>{product.content}</p>
                <div className="amount">
                  <button className="count"> - </button>
                  <span>{product.count}</span>
                  <button className="count"> + </button>
                  <span>{product.stock} in stock</span>
                </div>
              </div>
              <div className="delete">X</div>
            </div>
          ))}
          <div className="total">
            <Link to="/payment">Payment</Link>
            <h3>Total: CHF {total}.00</h3>
          </div>
        </div>
      );
    }
  }
}

export default Cart;
