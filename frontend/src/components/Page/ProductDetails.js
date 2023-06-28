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

    this.state = {
      currentProduct: {
        id: null,
        title: "",
        description: "",
        published: false,
      },
      message: "",
    };
  }

  componentDidMount() {
    if (currentUser.roles.includes("ROLE_ADMIN")) {
      this.getProduct(this.props.router.params.id);

    } else {
      this.getPublishedProduct(this.props.router.params.id);
      this.getUserProduct(this.props.router.params.id);
    }
  }

  //get productsDetails from the current productlsList
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


  render() {
    const { currentProduct } = this.state;
    return (
      <div className="details">
        <img src="\img\blob.jpg" alt="Product_Picture" />
        <div className="box">
          <div className="row">
            <h2>{currentProduct.title}</h2>
            <span>CHF {currentProduct.price}.00</span>
          </div>
          <p>{currentProduct.description}</p>
          <p>{currentProduct.content}</p>
          <div className="row">
            <Link to="/cart" className="cart">
              Add to cart
            </Link>
            <span>{currentProduct.stock} in stock</span>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(ProductDetails);
