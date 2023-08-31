import React from "react";
import { Link } from "react-router-dom";
import "../css/ProductDetails.css";
import { withRouter } from "../../common/with-router";
import Auth from "../services/Auth";
import authHeader from "../services/Auth-header";
import axios from "axios";
const currentUser = Auth.getCurrentUser();
class ProductDetails extends React.Component {
  constructor(props) {
    super(props);
    this.getProduct = this.getProduct.bind(this);
    this.handleQuantityDecrease = this.handleQuantityDecrease.bind(this);
    this.handleQuantityIncrease = this.handleQuantityIncrease.bind(this);
    this.addProductToCart = this.addProductToCart.bind(this);

    this.state = {
      currentProduct: {
        id: null,
        title: "",
        description: "",
        published: false,
        price: 0,
        stock: 0,
        imagePaths: [],
      },
      quantity: 1, // Set a default quantity
      message: "",
    };
  }

  handleQuantityDecrease() {
    if (this.state.quantity > 1) {
      this.setState({ quantity: this.state.quantity - 1 });
    }
  }

  handleQuantityIncrease() {
    if (this.state.quantity < this.state.currentProduct.stock) {
      this.setState({ quantity: this.state.quantity + 1 });
    }
  }


  componentDidMount() {
    if (currentUser.roles.includes("ROLE_ADMIN")) {
      this.getProduct(this.props.router.params.id);

    } else {
      this.getPublishedProduct(this.props.router.params.id);
      this.getUserProduct(this.props.router.params.id);
    }
  }

  //get productsDetails from the current productList
    getProduct(id) {
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
    }

  getUserProduct(id) {
    const currentUser = Auth.getCurrentUser();

    const config = {
      headers: authHeader(),
    };
    axios
        .get(`${process.env.REACT_APP_API_URL}/products/user/${currentUser.id}/${id}`,config)
        .then((response) => {
          this.setState({ currentProduct: response.data });
        })
        .catch((err) => {
          console.log(err);
        });
  }

  getPublishedProduct(id) {
    const currentUser = Auth.getCurrentUser();

    const config = {
      headers: authHeader(),
    };
    axios
        .get(`${process.env.REACT_APP_API_URL}/products/published/${id}`,config)
        .then((response) => {
          this.setState({ currentProduct: response.data });
        })
        .catch((err) => {
          console.log(err);
        });
  }


  addProductToCart(productId) {
    const config = {
      headers: authHeader()
    };

    const payload = {
      productId: productId,
      quantity: this.state.quantity // Anzahl kann nach Bedarf geändert werden
    };

    axios.post(process.env.REACT_APP_API_URL + "/cart/add", payload, config)
        .then(() => {
          this.fetchCartData(); // Daten aktualisieren
          window.location.reload();
        })
        .catch(err => {
          console.log(err);
        });
  }


  render() {
    const { currentProduct, quantity } = this.state;
    return (
        <div className="details">
          <div className="image-container">
            <img src={currentProduct.imagePaths[0]} alt="picture" className="main-image" />
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
