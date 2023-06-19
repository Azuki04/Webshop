import React from "react";
import { Link } from "react-router-dom";
import "../css/ProductDetails.css";
import { withRouter } from "../../common/with-router";

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
    this.getProduct(this.props.router.params.id);
  }

  //get productsDetails from the current productlsList
  getProduct(id) {
    fetch(process.env.REACT_APP_API_URL +"/products/product/" + id)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        this.setState({
          currentProduct: data,
        });
      })
      .catch((e) => {
        console.log(e);
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
