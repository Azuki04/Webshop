import React from "react";
import axios from "axios";
import { withRouter } from "../../common/with-router";
import authHeader from "../services/Auth-header";

import { Button } from "../Button";

class EditProduct extends React.Component {
  constructor(props) {
    super(props);
    this.changeTitleHandler = this.changeTitleHandler.bind(this);
    this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
    this.changeContentHandler = this.changeContentHandler.bind(this);
    this.changePriceHandler = this.changePriceHandler.bind(this);
    this.changeStockHandler = this.changeStockHandler.bind(this);
    this.changeSrcHandler = this.changeSrcHandler.bind(this);
    this.changeCategoryHandler = this.changeCategoryHandler.bind(this);

    this.getProduct = this.getProduct.bind(this);
    this.updatePublished = this.updatePublished.bind(this);
    this.updateProduct = this.updateProduct.bind(this);
    this.deleteProduct = this.deleteProduct.bind(this);

    this.state = {
      currentProduct: {
        id: null,
        title: "",
        description: "",
        content: "",
        price: "",
        stock: "",
        src: "",
        category: "",

        published: false,
      },
      categories: [],
      message: "",
    };
  }

  componentDidMount() {
    this.getProduct(this.props.router.params.id);
    fetch(process.env.REACT_APP_API_URL +"/category")
      .then((response) => response.json())
      .then((data) => this.setState({ categories: data }));
  }

  changeTitleHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        title: event.target.value,
      },
    }));
  }

  changeDescriptionHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        description: event.target.value,
      },
    }));
  }

  changeContentHandler = (event) => {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        content: event.target.value,
      },
    }));
  };

  changePriceHandler = (event) => {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        price: event.target.value,
      },
    }));
  };

  changeStockHandler = (event) => {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        stock: event.target.value,
      },
    }));
  };

  changeSrcHandler = (event) => {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        src: event.target.value,
      },
    }));
  };

  changeCategoryHandler = (event) => {
    this.setState({ category: event.target.value });
  };

  //get products with axios fetch with jwt token
  getProduct(id) {
    const config = {
      headers:
          authHeader()
    };
    axios
        .get(process.env.REACT_APP_API_URL +"/products/admin/" + id, config)
        .then((response) => {
          this.setState({
            currentProduct: response.data,
            });
            console.log(response.data); // response.data is the product
        });
  }

  // publish fetch product with jwt token
  updatePublished(status) {
    const config = {
      headers:
          authHeader()
    };
    var data = {
      id: this.state.currentProduct.id,
      title: this.state.currentProduct.title,
      description: this.state.currentProduct.description,
      content: this.state.currentProduct.content,
      price: this.state.currentProduct.price,
      stock: this.state.currentProduct.stock,
      src: this.state.currentProduct.src,
      category: this.state.currentProduct.category,
      published: status,
    };
    axios
        .put(process.env.REACT_APP_API_URL +"/products/admin/" + this.state.currentProduct.id, data, config)
        .then((response) => {
          this.setState((prevState) => ({
            currentProduct: {
              ...prevState.currentProduct,
              published: status,
            },
          }));
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
  }

  // UPDATE fetch product with jwt token
  updateProduct() {
    const config = {
      headers:
          authHeader()
    };
    axios
        .put(process.env.REACT_APP_API_URL +"/products/" + this.state.currentProduct.id, this.state.currentProduct, config)
        .then((response) => {
          console.log(response.data);
          this.setState({
            message: "The product was updated successfully!",
          });
        })
        .catch((e) => {
          console.log(e);
        });
  }

  // delete fetch with jwt token
  deleteProduct() {
    let id = this.state.currentProduct.id;
    const config = {
      headers:
          authHeader()
    };
    axios
        .delete(process.env.REACT_APP_API_URL +"/products/admin/" + id, config)
        .then((response) => {
          console.log(response.data);
          this.props.router.push("/admin");
          window.location.reload();
        })
        .catch((e) => {
          console.log(e);
        });
  }


  render() {
    const { currentProduct } = this.state;
    if (
      this.state.currentProduct == null ||
      this.state.currentProduct.length === 0
    ) {
      return (
        <h2 style={{ margin: "100px", textAlign: "center" }}>
          this Product not exist
        </h2>
      );
    } else {
      return (
        <div>
          <h4>Product edit</h4>

          <div className="create_product">
            <div className="upload">
              <input
                type="file"
                name="file"
                id="file_up"
                defaultValue={currentProduct.src}
                onChange={this.changeSrcHandler}
              />
            </div>

            <form>
              <div className="row">
                <label htmlFor="title">Title</label>
                <input
                  type="text"
                  name="title"
                  placeholder="Title"
                  defaultValue={currentProduct.title}
                  onChange={this.changeTitleHandler}
                />
              </div>

              <div className="row">
                <label htmlFor="price">Price</label>
                <input
                  type="number"
                  name="price"
                  placeholder="Price"
                  defaultValue={currentProduct.price}
                  onChange={this.changePriceHandler}
                />
              </div>

              <div className="row">
                <label htmlFor="stock">Stock</label>
                <input
                  type="number"
                  name="stock"
                  placeholder="how much in stock"
                  defaultValue={currentProduct.stock}
                  onChange={this.changeStockHandler}
                />
              </div>

              <div className="row">
                <label htmlFor="description">Description</label>
                <textarea
                  type="text"
                  name="description"
                  id="description"
                  placeholder="Description..."
                  defaultValue={currentProduct.description}
                  rows="5"
                  onChange={this.changeDescriptionHandler}
                />
              </div>

              <div className="row">
                <label htmlFor="content">Content</label>
                <textarea
                  type="text"
                  name="content"
                  id="content"
                  placeholder="Content..."
                  defaultValue={currentProduct.content}
                  rows="7"
                  onChange={this.changeContentHandler}
                />
              </div>

              <div className="row">
                <label htmlFor="categories">Categories: </label>
                <select
                  name="category"
                  value={currentProduct.category.id}
                  onChange={this.changeCategoryHandler}
                >
                  <option defaultValue="">
                    {currentProduct.category.name}
                  </option>
                  {this.state.categories.map((category) => (
                    <option value={category.id} key={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>
            </form>
          </div>
          <div className="button-group">
            {currentProduct.published ? (
              <Button onClick={() => this.updatePublished(false)}>
                UnPublish
              </Button>
            ) : (
              <Button
                buttonStyle="btn--normal"
                onClick={() => this.updatePublished(true)}
              >
                Publish
              </Button>
            )}

            <Button
              buttonStyle="btn--delete"
              buttonSize={"btn--small"}
              onClick={this.deleteProduct}
            >
              Delete
            </Button>

            <Button
              type="submit"
              buttonStyle="btn--update"
              buttonSize={"btn--small"}
              onClick={this.updateProduct}
            >
              Update
            </Button>
          </div>
          <p>{this.state.message}</p>
        </div>
      );
    }
  }
}

export default withRouter(EditProduct);
