import React from "react";
import {Link, Navigate} from "react-router-dom";
import { Button } from "../Button";
import "../css/Products.css";
import Auth from "../services/Auth";
import authHeader from "../services/Auth-header";
import axios from "axios";


// get the current user
const currentUser = Auth.getCurrentUser();
class Products extends React.Component {
  constructor(props) {
    super(props);
    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);

    this.searchTitle = this.searchTitle.bind(this);

    this.state = {
      products: [],
      currentProduct: null,
      currentIndex: -1,
      searchTitle: "",
      redirect: null,
      userReady: false,
    };
  }

  onChangeSearchTitle(e) {
    const searchTitle = e.target.value;

    this.setState({
      searchTitle: searchTitle,
    });
  }

  // fetch get all products endpoint for login user
  componentDidMount() {
    // if there is no current user redirect to login page
    if (!currentUser) this.setState({ redirect: "/login" });
    this.setState({ currentUser: currentUser, userReady: true })

    // fetch the jwt token from local storage
    const config = {
      headers:
          authHeader()
    };

    axios.get(process.env.REACT_APP_API_URL +"/products/seller/" + currentUser.id, config)
        .then(response => response.data)
        .then(data => this.setState({products: data}))
        .catch(err => {console.log(err)})
  }

  //search for title
  searchTitle() {
    this.setState({
      currentProduct: null,
      currentIndex: -1,
    });
    let title = this.state.searchTitle;
    // fetch the jwt token from local storage
    const config = {
      headers:
          authHeader()
    };
    
    axios.get(process.env.REACT_APP_API_URL+"/products/user/" + currentUser.id +"?title=" + title, config)
        .then(response => response.data)
        .then(data => this.setState({products: data}))
        .catch(err => {console.log(err)})
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }
    const { searchTitle, products } = this.state;
    if (this.state.products == null || this.state.products.length === 0) {
      return (
        <h2 style={{ margin: "100px", textAlign: "center" }}>
          you have not created any products yet
        </h2>
      );
    } else {
      return (
        <div>
          <h4>Created products by {currentUser.username} </h4>
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
                  <img src={product.imagePaths[0]} alt="picture" />
                </Link>
                <div className="content">
                  <Link to={`/products/detail/${product.id}`}>
                    <h3>{product.title}</h3>
                  </Link>
                  <span>CHF {product.price}.00</span>
                  <p>{product.description}</p>
                  <Link to={`/editProduct/${product.id}`}>
                    <button>Edit</button>
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </div>
      );
    }
  }
}
export default Products;
