import React from "react";
import { withRouter } from "../../common/with-router";
import axios from "axios";
import authHeader from "../services/Auth-header";
import Auth from "../services/Auth";
import "../css/CreateProduct.css";

import { Button } from "../Button";
import AuthService from "../services/Auth";
import {Navigate} from "react-router-dom";


class AddProduct extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      id: null,
      title: "",
      description: "",
      content: "",
      price: "",
      stock: "",
      files: [],
      categories: [],
      titleError: "",
      priceError: "",
      stockError: "",
      descriptionError: "",
      categoryError: "",
      published: false,
      submitted: false,
      redirect: null,
      userReady: false,
    };

    // Bind methods
    this.changeTitleHandler = this.changeTitleHandler.bind(this);
    this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
    this.changeContentHandler = this.changeContentHandler.bind(this);
    this.changePriceHandler = this.changePriceHandler.bind(this);
    this.changeStockHandler = this.changeStockHandler.bind(this);
    this.changeSrcHandler = this.changeSrcHandler.bind(this);
    this.changeCategoryHandler = this.changeCategoryHandler.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.cancel = this.cancel.bind(this);
    this.newProduct = this.newProduct.bind(this);
  }

  changeTitleHandler(event) {
    this.setState({ title: event.target.value });
  }

  changeDescriptionHandler(event) {
    this.setState({ description: event.target.value });
  }

  changeContentHandler(event) {
    this.setState({ content: event.target.value });
  }

  changePriceHandler(event) {
    this.setState({ price: event.target.value });
  }

  changeStockHandler(event) {
    this.setState({ stock: event.target.value });
  }

  changeSrcHandler(event) {
    const files = event.target.files;
    const filesArray = [];

    for (let i = 0; i < files.length; i++) {
      filesArray.push({
        file: files[i],
        url: URL.createObjectURL(files[i]),
      });
    }

    this.setState({ files: filesArray });
  }



  changeCategoryHandler(event) {
    this.setState({ category: event.target.value });
  }

  /**
   * Fetches the categories from the API and sets them in the state
   */
  componentDidMount() {
    // get the current user from local storage
    const currentUser = AuthService.getCurrentUser();
    // if there is no current user redirect to login page
    if (!currentUser) this.setState({ redirect: "/login" });
    this.setState({ currentUser: currentUser, userReady: true })

    fetch(process.env.REACT_APP_API_URL +"/category")
      .then((response) => response.json())
      .then((data) => this.setState({ categories: data }));
  }


  /**
   * Handles the form submission and sends a POST request to create a new product
   */
  handleSubmit = async (event) => {
    event.preventDefault();

    const userId = Auth.getCurrentUser().id;

    const product = {
      title: this.state.title,
      description: this.state.description,
      content: this.state.content,
      price: parseInt(this.state.price), // Convert to number
      stock: parseInt(this.state.stock), // Convert to number
      category: {
        id: this.state.category,
      },
      user: {
        id: userId,
      },
    };

    const formData = new FormData();
    formData.append("data", JSON.stringify(product));

    for (let i = 0; i < this.state.files.length; i++) {
      formData.append("file", this.state.files[i].file); // Append the image file
    }

    try {
      const isValid = this.validateForm();
      if (isValid) {
      const response = await axios.post(
          process.env.REACT_APP_API_URL + "/products/seller",
          formData,
          {
            headers: {
              ...authHeader(),
              "Content-Type": "multipart/form-data", // Set the proper content type
            },
          }
      );

      if (response.status === 200) {
        // Handle successful response
        this.setState({
          // Update state with response data
          submitted: true
        });
      } else {
        // Handle error response
        console.log("Response error:", response);
      }
      }
    } catch (error) {
      console.log(error);
      alert("An error occurred while submitting the form.");
      // Handle other errors, like authentication, redirection, etc.
    }
  };


  /**
   * Resets the state to empty values when the "Cancel" button is clicked
   */
  cancel() {
    this.props.router.navigate("/products");
  }

  /**
   * Resets the state to empty values when the "Back" button is clicked after successful submission
   */
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
      files: [],

      published: false,
      submitted: false,
    });
  }

  /**
   * Validates the form inputs and displays error messages if any
   * Returns true if the form is valid, otherwise false
   */
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
    // if the user is not logged in redirect to login page
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }
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
                  name="files"
                  id="file_up"
                  multiple
                  onChange={this.changeSrcHandler}
              />
            </div>
            <div className="preview">
              {this.state.files.map((file, index) => (
                  <img
                      key={index}
                      src={file.url}
                      alt={`Preview ${index}`}
                      style={{ maxWidth: "100px", maxHeight: "100px", margin: "10px" }}
                  />
              ))}
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