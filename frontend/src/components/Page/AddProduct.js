import React from "react";
import { withRouter } from "../../common/with-router";
import "../css/CreateProduct.css";

import { Button } from "../Button";

class AddProduct extends React.Component {
  constructor(props) {
    super(props);
    this.changeTitleHandler = this.changeTitleHandler.bind(this);
    this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
    this.changeContentHandler = this.changeContentHandler.bind(this);
    this.changePriceHandler = this.changePriceHandler.bind(this);
    this.changeStockHandler = this.changeStockHandler.bind(this);
    this.changeSrcHandler = this.changeSrcHandler.bind(this);
    this.changeCategoryHandler = this.changeCategoryHandler.bind(this);

    this.validateForm = this.validateForm.bind(this);
    this.cancel = this.cancel.bind(this);
    this.newProduct = this.newProduct.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.state = {
      id: null,
      title: "",
      description: "",
      content: "",
      price: "",
      stock: "",
      src: "",
      categories: [],

      titleError: "",
      priceError: "",
      stockError: "",
      descriptionError: "",
      categoryError: "",

      published: false,
      submitted: false,
    };
  }

  changeTitleHandler = (event) => {
    this.setState({ title: event.target.value });
  };

  changeDescriptionHandler = (event) => {
    this.setState({ description: event.target.value });
  };

  changeContentHandler = (event) => {
    this.setState({ content: event.target.value });
  };

  changePriceHandler = (event) => {
    this.setState({ price: event.target.value });
  };
  changeStockHandler = (event) => {
    this.setState({ stock: event.target.value });
  };

  changeSrcHandler = (event) => {
    this.setState({ src: event.target.value });
  };

  changeCategoryHandler = (event) => {
    this.setState({ category: event.target.value });
  };

  componentDidMount() {
    fetch(process.env.REACT_APP_API_URL +"/category")
      .then((response) => response.json())
      .then((data) => this.setState({ categories: data }));
  }

  //Post fetch
  handleSubmit(event) {
    event.preventDefault();
    let product = {
      title: this.state.title,
      description: this.state.description,
      content: this.state.content,
      price: this.state.price,
      stock: this.state.stock,
      src: this.state.src,
      category: {
        id: this.state.category,
      },
    };

    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(product),
    };

    const isValid = this.validateForm();
    if (isValid) {
      fetch("http://localhost:8080/api/products", requestOptions)
        .then((response) => response.json())
        .then((jsonData) => {
          // wir überprüfen die ID in der Konsole
          console.log(jsonData);
        });
      this.setState({
        submitted: true,
      });
    }
  }

  //cancel
  cancel() {
    this.props.router.navigate("/products");
  }

  //click on the button then the information will be deleted
  newProduct() {
    this.setState({
      id: null,
      title: "",
      description: "",
      content: "",
      price: "",
      stock: "",
      src: "",
      category: "",

      published: false,
      submitted: false,
    });
  }

  validateForm() {
    let titleError = "";
    let priceError = "";
    let stockError = "";
    let descriptionError = "";
    let categoryError = "";

    var title = this.state.title;
    if (title === null || title === "") {
      titleError = "Please select a title for this product!";
      this.setState({ titleError });
      return false;
    } else {
      this.setState({ titleError: "" });
    }

    var price = this.state.price;
    if (price === null || price === "") {
      priceError = "invalid price";
      this.setState({ priceError });
      return false;
    } else {
      this.setState({ priceError: "" });
    }

    if (price < 0) {
      priceError = "the price cant not be negative";
      this.setState({ priceError });
      return false;
    } else {
      this.setState({ priceError: "" });
    }

    var stock = this.state.stock;
    if (stock === null || stock === "") {
      stockError = "Please enter how many products there are!";
      this.setState({ stockError });
      return false;
    } else {
      this.setState({ stockError: "" });
    }

    if (stock < 0) {
      stockError = "the stock can not be negative";
      this.setState({ stockError });
      return false;
    } else {
      this.setState({ stockError: "" });
    }

    var description = this.state.description;
    if (description === null || description === "") {
      descriptionError = "write a short description please!";
      this.setState({ descriptionError });
      return false;
    } else {
      this.setState({ descriptionError: "" });
    }

    if (description.length < 0 || description.length > 30) {
      descriptionError = "the description must be at least 30 characters long";
      this.setState({ descriptionError });
      return false;
    } else {
      this.setState({ descriptionError: "" });
    }

    var category = this.state.categories;
    if (category === null || category === "") {
      categoryError = "select a category!";
      this.setState({ categoryError });
      return false;
    } else {
      this.setState({ categoryError: "" });
    }

    return true;
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={this.newProduct}>
              Back
            </button>
          </div>
        ) : (
          <div className="create_product">
            <div className="upload">
              <input
                type="file"
                name="file"
                id="file_up"
                value={this.state.src}
                onChange={this.changeSrcHandler}
              />
            </div>
            <form onSubmit={this.handleSubmit}>
              <div className="row">
                <label htmlFor="title">Title*</label>
                <input
                  type="text"
                  name="title"
                  placeholder="Title"
                  value={this.state.title}
                  onChange={this.changeTitleHandler}
                />
                <div style={{ fontSize: 12, color: "red" }}>
                  {this.state.titleError}
                </div>
              </div>

              <div className="row">
                <label htmlFor="price">Price*</label>
                <input
                  type="number"
                  name="price"
                  placeholder="Price"
                  value={this.state.price}
                  onChange={this.changePriceHandler}
                />
                <div style={{ fontSize: 12, color: "red" }}>
                  {this.state.priceError}
                </div>
              </div>

              <div className="row">
                <label htmlFor="stock">Stock*</label>
                <input
                  type="number"
                  name="stock"
                  placeholder="how much in stock"
                  value={this.state.stock}
                  onChange={this.changeStockHandler}
                />
                <div style={{ fontSize: 12, color: "red" }}>
                  {this.state.stockError}
                </div>
              </div>

              <div className="row">
                <label htmlFor="description">Description*</label>
                <textarea
                  type="text"
                  name="description"
                  id="description"
                  placeholder="Description..."
                  value={this.state.description}
                  rows="5"
                  onChange={this.changeDescriptionHandler}
                />
                <div style={{ fontSize: 12, color: "red" }}>
                  {this.state.descriptionError}
                </div>
              </div>

              <div className="row">
                <label htmlFor="content">Content</label>
                <textarea
                  type="text"
                  name="content"
                  id="content"
                  placeholder="Content..."
                  value={this.state.content}
                  rows="7"
                  onChange={this.changeContentHandler}
                />
              </div>

              <div className="row">
                <label htmlFor="categories">Categories:* </label>
                <select
                  name="category"
                  value={this.state.category}
                  onChange={this.changeCategoryHandler}
                >
                  <option value="100">Please select a category</option>
                  {this.state.categories.map((category) => (
                    <option value={category.id} key={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
                <div style={{ fontSize: 12, color: "red" }}>
                  {this.state.categoryError}
                </div>
              </div>
              <Button buttonStyle="btn--update" type="submit">
                save
              </Button>
              <Button buttonStyle="btn--delete" onClick={this.cancel}>
                cancel
              </Button>
              <div>* Required fields</div>
            </form>
          </div>
        )}
      </div>
    );
  }
}
export default withRouter(AddProduct);
