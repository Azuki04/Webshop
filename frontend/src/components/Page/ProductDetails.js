import React from "react";
import { Link } from "react-router-dom";
import "../css/ProductDetails.css";
import { withRouter } from "../../common/with-router";
import Auth from "../services/Auth";
import authHeader from "../services/Auth-header";
import axios from "axios";
import GlobalStorageContext from "../services/GlobalStorage";
const currentUser = Auth.getCurrentUser();
class ProductDetails extends React.Component {
  static contextType = GlobalStorageContext;
  constructor(props) {
    super(props);
    this.getProduct = this.getProduct.bind(this);
    this.handleQuantityDecrease = this.handleQuantityDecrease.bind(this);
    this.handleQuantityIncrease = this.handleQuantityIncrease.bind(this);
    this.addProductToCart = this.addProductToCart.bind(this);
    this.addExampleProductToCart = this.addExampleProductToCart.bind(this);

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
    if(currentUser == null || currentUser.roles.includes("ROLE_CUSTOMER")){
      this.getPublishedProduct(this.props.router.params.id);
    }else{
      if (currentUser.roles.includes("ROLE_ADMIN")) {
        this.getProduct(this.props.router.params.id);
      }else{
        this.getUserProduct(this.props.router.params.id);
      }
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
        .get(`${process.env.REACT_APP_API_URL}/products/seller/${currentUser.id}/${id}`,config)
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
          // Daten aktualisieren
          this.addExampleProductToCart(payload);
        })
        .catch(err => {
          console.log(err);
        });
  }


  addExampleProductToCart(product) {
    const { cartItems, setCartItems } = this.context;

    // Überprüfe, ob das Produkt bereits im Warenkorb existiert
    const existingCartItem = cartItems.find(item => item.productId === product.productId);

    if (!existingCartItem) {
      // Das Produkt ist nicht im Warenkorb, füge es hinzu
      setCartItems([...cartItems, product]);
    } else {
      // Das Produkt ist bereits im Warenkorb, erhöhe die Anzahl um 1 (oder füge es erneut hinzu, wie du es möchtest)
      existingCartItem.quantity += 1;
      setCartItems([...cartItems]);
    }
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
