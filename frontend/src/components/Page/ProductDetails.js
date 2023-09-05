import React, { Component } from "react";
import { Link } from "react-router-dom";
import "../css/ProductDetails.css";
import { withRouter } from "../../common/with-router";
import Auth from "../services/Auth";
import authHeader from "../services/Auth-header";
import axios from "axios";
import GlobalStorageContext from "../services/GlobalStorage";

class ProductDetails extends Component {
  static contextType = GlobalStorageContext;

  state = {
    currentProduct: {
      id: null,
      title: "",
      description: "",
      published: false,
      price: 0,
      stock: 0,
      imagePaths: [],
    },
    quantity: 1,
    message: "",
  };

  componentDidMount() {
    const { id } = this.props.router.params;
    const currentUser = Auth.getCurrentUser();

    if (currentUser == null || currentUser.roles.includes("ROLE_CUSTOMER")) {
      this.getPublishedProduct(id);
    } else {
      if (currentUser.roles.includes("ROLE_ADMIN")) {
        this.getPublishedProduct(id);
        this.getProduct(id);
      } else {
        this.getPublishedProduct(id);
        this.getUserProduct(id);
      }
    }
  }

  handleQuantityDecrease = () => {
    if (this.state.quantity > 1) {
      this.setState({ quantity: this.state.quantity - 1 });
    }
  };

  handleQuantityIncrease = () => {
    if (this.state.quantity < this.state.currentProduct.stock) {
      this.setState({ quantity: this.state.quantity + 1 });
    }
  };

  getProduct = (id) => {
    const config = {
      headers: authHeader(),
    };

    axios
        .get(process.env.REACT_APP_API_URL + "/products/admin/" + id, config)
        .then((response) => {
          this.setState({ currentProduct: response.data });
        })
        .catch((err) => {
          console.log(err);
        });
  };

  getUserProduct = (id) => {
    const currentUser = Auth.getCurrentUser();

    const config = {
      headers: authHeader(),
    };

    axios
        .get(
            `${process.env.REACT_APP_API_URL}/products/seller/${currentUser.id}/${id}`,
            config
        )
        .then((response) => {
          this.setState({ currentProduct: response.data });
        })
        .catch((err) => {
          console.log(err);
        });
  };

  getPublishedProduct = (id) => {
    const currentUser = Auth.getCurrentUser();

    const config = {
      headers: authHeader(),
    };

    axios
        .get(`${process.env.REACT_APP_API_URL}/products/published/${id}`, config)
        .then((response) => {
          this.setState({ currentProduct: response.data });
        })
        .catch((err) => {
          console.log(err);
        });
  };

  addProductToCart = (productId) => {
    const config = {
      headers: authHeader(),
    };

    const payload = {
      productId: productId,
      quantity: this.state.quantity,
    };

    axios
        .post(process.env.REACT_APP_API_URL + "/cart", payload, config)
        .then(() => {
          this.addExampleProductToCart(payload);
        })
        .catch((err) => {
          console.log(err);
        });
  };

  addExampleProductToCart = (product) => {
    const { cartItems, setCartItems } = this.context;

    const existingCartItem = cartItems.find(
        (item) => item.productId === product.productId
    );

    if (!existingCartItem) {
      setCartItems([...cartItems, product]);
    } else {
      existingCartItem.quantity += 1;
      setCartItems([...cartItems]);
    }
  };

  render() {
    const { currentProduct, quantity } = this.state;

    return (
        <div className="details">
          <div className="image-container">
            <img
                src={currentProduct.imagePaths[0]}
                alt="picture"
                className="main-image"
            />
            <div className="thumbnail-container">
              {currentProduct.imagePaths.map((imagePath, index) => (
                  <img
                      src={imagePath}
                      alt={`thumbnail ${index}`}
                      className="thumbnail"
                      key={index}
                  />
              ))}
            </div>
          </div>
          <div className="box">
            <div className="row">
              <h2>{currentProduct.title}</h2>
              <span>CHF {currentProduct.price}.00</span>
            </div>
            <p>{currentProduct.description}</p>
            <p>{currentProduct.content}</p>
            <div className="row">
              <div className="amount">
                <button className="count" onClick={this.handleQuantityDecrease}>
                  -
                </button>
                <span>{quantity}</span>
                <button className="count" onClick={this.handleQuantityIncrease}>
                  +
                </button>
                <span>{currentProduct.stock} in stock</span>
              </div>
            </div>
            <Link
                to="/cart"
                className="cart"
                style={{ marginTop: "40px" }}
                onClick={() => this.addProductToCart(currentProduct.id)}
            >
              Add to cart
            </Link>
          </div>
        </div>
    );
  }
}

export default withRouter(ProductDetails);
