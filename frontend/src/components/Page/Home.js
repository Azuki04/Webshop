import React, { Component } from "react";
import { Link } from "react-router-dom";
import { Button } from "../Button";
import "../css/SalesMonitoring.css";
import axios from "axios";
import authHeader from "../services/Auth-header";
import GlobalNavigation from "../GlobalNavigation";

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: [],
      searchTitle: "",
      categoryTree: [],
      selectedCategory: 'All', // Track the selected category
    };

    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
    this.searchTitle = this.searchTitle.bind(this);
    this.searchCategory = this.searchCategory.bind(this);
    this.addProductToCart = this.addProductToCart.bind(this);
    this.handleCategoryChange = this.handleCategoryChange.bind(this);
    this.renderCategories = this.renderCategories.bind(this);
  }

  componentDidMount() {
    this.fetchProducts();
    this.fetchCategories();
  }

  fetchProducts() {
    fetch(process.env.REACT_APP_API_URL + "/products/published")
        .then((response) => response.json())
        .then((data) => this.setState({ products: data }));
  }

  fetchCategories() {
    fetch(process.env.REACT_APP_API_URL + "/category/categoryTree")
        .then((response) => response.json())
        .then((data) => this.setState({ categoryTree: data }));
  }



  onChangeSearchTitle(e) {
    this.setState({
      searchTitle: e.target.value,
    });
  }

  searchCategory(categoryId) {
    fetch(process.env.REACT_APP_API_URL + `/category/products/${categoryId}`)
        .then((response) => response.json())
        .then((data) => this.setState({ products: data }));
  }

  searchTitle() {
    const title = this.state.searchTitle;
    fetch(process.env.REACT_APP_API_URL + "/products/published?title=" + title)
        .then((response) => response.json())
        .then((data) => this.setState({ products: data }));
  }

  addProductToCart(productId) {
    const config = {
      headers: authHeader(),
    };

    const payload = {
      productId: productId,
      quantity: 1,
    };

    axios
        .post(process.env.REACT_APP_API_URL + "/cart/add", payload, config)
        .then(() => {
          this.fetchCartData(); // Update cart data
          window.location.reload();


        })
        .catch((err) => {
          console.log(err);
        });
  }

  handleCategoryChange(event) {
    const selectedCategoryId = event.target.value;

    // Call your fetchProductsByCategoryId function here
    if (selectedCategoryId === 'All') {
      // Fetch all products
      this.fetchProducts();
    } else {
      // Fetch products by category ID
      this.searchCategory(selectedCategoryId)
    }

    this.setState({ selectedCategory: selectedCategoryId });
  }

  //recursion function to render categories
  renderCategories = (categories) => {
    return categories.map((category) => (
        <React.Fragment key={category.id}>
          <option className="optionGroup" value={category.id}>
            {category.name}
          </option>
          {category.subCategory.length > 0 &&
              this.renderCategories(category.subCategory)}
        </React.Fragment>
    ));
  };

  render() {
    const { searchTitle, products, categoryTree, selectedCategory } = this.state;

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
              <div className="search">
                <div>
                  <select
                      className="form-control"
                      id="category"
                      name="category"
                      value={selectedCategory}
                      onChange={this.handleCategoryChange}
                  >
                    <option value="All">All</option>
                    {this.renderCategories(categoryTree)}
                  </select>
                </div>
              </div>

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
                    <button onClick={() => this.addProductToCart(product.id)}>
                      Add to cart
                    </button>
                  </div>
                </div>
            ))}
          </div>
        </div>
    );
  }
}

export default Home;
