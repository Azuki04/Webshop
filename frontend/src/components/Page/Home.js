import React, { Component } from "react";
import { Link } from "react-router-dom";
import { withRouter } from "../../common/with-router";
import { Button } from "../Button";
import "../css/SalesMonitoring.css";
import axios from "axios";
import authHeader from "../services/Auth-header";
import GlobalStorageContext from "../services/GlobalStorage";

class Home extends Component {
  static contextType = GlobalStorageContext;

  state = {
    products: [],
    searchTitle: "",
    categoryTree: [],
    selectedCategory: "All", // Track the selected category
    noProductsFound: false, // Track if no products were found for the search
  };

  componentDidMount() {
    const params = this.props.router.location.search

    const searchTitle = new URLSearchParams(params).get("searchTitle")
    console.log(searchTitle)
    // parse search params
    if (searchTitle) {
      this.setState({ searchTitle }, () => {
        this.searchTitle(searchTitle);
        console.log(this.state.searchTitle)
      })


    } else {
      this.fetchProducts().then(r => console.log(r));
    }

    this.fetchCategories().then(r => console.log(r));
  }


  fetchData = async () => {
    const { searchTitle } = new URLSearchParams(
        this.props.router.location.search
    );

    if (searchTitle) {
      await this.setState({ searchTitle });
      await this.searchTitle();
    } else {
      this.fetchProducts();
    }

    this.fetchCategories();
  };

  fetchProducts = async (searchTitle = "") => {
    try {
      const response = await fetch(
          `${process.env.REACT_APP_API_URL}/products/published`
      );
      const data = await response.json();
      this.setState({ products: data });
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  fetchCategories = async () => {
    try {
      const response = await fetch(
          `${process.env.REACT_APP_API_URL}/category/categoryTree`
      );
      const data = await response.json();
      this.setState({ categoryTree: data });
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  onChangeSearchTitle = (e) => {
    this.setState({
      searchTitle: e.target.value,
    });
  };

  searchCategory = async (categoryId) => {
    try {
      const response = await fetch(
          `${process.env.REACT_APP_API_URL}/category/products/${categoryId}`
      );
      const data = await response.json();
      this.setState({ products: data });
    } catch (error) {
      console.error("Error searching by category:", error);
    }
  };

  searchTitle = async () => {
    this.props.router.navigate(
        `${this.props.router.location.pathname}?searchTitle=${this.state.searchTitle}`,
        { replace: true }
    );
    const title = this.state.searchTitle;
    try {
      const response = await fetch(
          `${process.env.REACT_APP_API_URL}/products/published?title=${title}`
      );

      if (response.ok) {
        const responseData = await response.text();
        try {
          const data = JSON.parse(responseData);
          if (Array.isArray(data)) {
            // Products found, update state with the results
            this.setState({ products: data, noProductsFound: data.length === 0 });
          } else {
            // Invalid response, set noProductsFound to true
            this.setState({ products: [], noProductsFound: true });
          }
        } catch (jsonError) {
          // Invalid JSON response, set noProductsFound to true
          console.error("Error parsing JSON:", jsonError);
          this.setState({ products: [], noProductsFound: true });
        }
      } else {
        console.error("Error searching by title:", response.statusText);
      }
    } catch (error) {
      console.error("Error searching by title:", error);
    }
  };





  addProductToCart = async (productId) => {
    const config = {
      headers: authHeader(),
    };

    const payload = {
      productId: productId,
      quantity: 1,
    };

    try {
      const response = await axios.post(
          `${process.env.REACT_APP_API_URL}/cart`,
          payload,
          config
      );

      this.addExampleProductToCart(payload);
    } catch (error) {
      console.error("Error adding product to cart:", error);
    }
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

  handleCategoryChange = (event) => {
    const selectedCategoryId = event.target.value;

    if (selectedCategoryId === "All") {
      this.fetchProducts();
    } else {
      this.searchCategory(selectedCategoryId);
    }

    this.setState({ selectedCategory: selectedCategoryId });
  };

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
    const { searchTitle, products, categoryTree, selectedCategory, noProductsFound } = this.state;
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
            {noProductsFound ? (
                <h4>No products found for the search: {searchTitle}</h4>
            ) : (
                products.map((product) => (
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
                ))
            )}
          </div>

        </div>
    );
  }
}

export default withRouter(Home);
