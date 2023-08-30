import React from "react";
import { Link } from "react-router-dom";
import { Button } from "../Button";
import "../css/Products.css";
import axios from "axios";
import authHeader from "../services/Auth-header";

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
    this.searchTitle = this.searchTitle.bind(this);

    this.state = {
      products: [],
      currentProduct: null,
      currentIndex: -1,
      searchTitle: "",
    };
  }

  onChangeSearchTitle(e) {
    const searchTitle = e.target.value;

    this.setState({
      searchTitle: searchTitle,
    });
  }

  //sechrch for title
  searchTitle() {
    this.setState({
      currentProduct: null,
      currentIndex: -1,
    });
    let title = this.state.searchTitle;
    fetch(process.env.REACT_APP_API_URL +"/products/published?title=" + title)
      .then((response) => response.json())
      .then((data) => this.setState({ products: data }));
  }

  // get all published products
  componentDidMount() {
    if (this.state.products == null || this.state.products.length === 0) {
      fetch(process.env.REACT_APP_API_URL +"/products/published")
        .then((response) => response.json())
        .then((data) => this.setState({ products: data }));
    }
  }

  addProductToCart(productId) {
    const config = {
      headers: authHeader()
    };

    const payload = {
      productId: productId,
      quantity: 1 // Anzahl kann nach Bedarf geändert werden
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
    const { searchTitle, products } = this.state;
    if (this.state.products == null || this.state.products.length === 0) {
      return (
        <h2 style={{ margin: "100px", textAlign: "center" }}>
          No more products, all sold out
        </h2>
      );
    } else {
      return (
        <div>
          <h4>Welcome to shop.ch</h4>
          <div className="search">
            <div>
              <input
                type="text"
                className="search-input"
                placeholder="Search by title"
                value={searchTitle}
                onChange={this.onChangeSearchTitle}
              />

              <Button
                buttonStyle="btn--normal"
                buttonSize="btn-small"
                type="button"
                onClick={this.searchTitle}
              >
                Search
              </Button>
            </div>
          </div>

          <div id="product">
            {products.map((product) => (
              <div className="card" key={product.id}>
                <Link to={`/products/detail/${product.id}`}>
                  <img src=".\img\blob.jpg" alt="Product_Picture" />
                </Link>
                <div className="content">
                  <Link to={`/products/detail/${product.id}`}>
                    <h3>{product.title}</h3>
                  </Link>
                  <span>CHF {product.price}.00</span>
                  <p>{product.description}</p>
                  <button onClick={() => this.addProductToCart(product.id)}>Add to cart</button>
                </div>
              </div>
            ))}
          </div>
        </div>
      );
    }
  }
}
export default Home;
