import React from "react";
import axios from "axios";
import { withRouter } from "../../common/with-router";
import authHeader from "../services/Auth-header";
import { Button } from "../Button";
import Auth from "../services/Auth";
import AuthService from "../services/Auth";
import {Navigate} from "react-router-dom";

class EditProduct extends React.Component {
  constructor(props) {
    super(props);
    // Bindung der Methoden an die Komponente
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
      redirect: null,
      userReady: false,

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
    // get the current user from local storage
    const currentUser = AuthService.getCurrentUser();
    // if there is no current user redirect to login page
    if (!currentUser) this.setState({ redirect: "/login" });
    this.setState({ currentUser: currentUser, userReady: true })

    if (currentUser.roles.includes("ROLE_ADMIN")) {
      this.getProduct(this.props.router.params.id);
    } else {
      this.getUserProduct(this.props.router.params.id);
    }

    fetch(process.env.REACT_APP_API_URL + "/category")
        .then((response) => response.json())
        .then((data) => this.setState({ categories: data }));
  }

  // Handler-Methode für die Änderung des Titels
  changeTitleHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        title: event.target.value,
      },
    }));
  }

  // Handler-Methode für die Änderung der Beschreibung
  changeDescriptionHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        description: event.target.value,
      },
    }));
  }

  // Handler-Methode für die Änderung des Inhalts
  changeContentHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        content: event.target.value,
      },
    }));
  }

  // Handler-Methode für die Änderung des Preises
  changePriceHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        price: event.target.value,
      },
    }));
  }

  // Handler-Methode für die Änderung des Lagerbestands
  changeStockHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        stock: event.target.value,
      },
    }));
  }

  // Handler-Methode für die Änderung des Bildquellenpfads
  changeSrcHandler(event) {
    this.setState((prevState) => ({
      currentProduct: {
        ...prevState.currentProduct,
        src: event.target.value,
      },
    }));
  }

  // Handler-Methode für die Änderung der Kategorie
  changeCategoryHandler(event) {
    this.setState({ category: event.target.value });
  }

  // Produkt abrufen mit Axios-Fetch und JWT-Token
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

  // Produkt des Benutzers abrufen mit Axios-Fetch und JWT-Token
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

  // Veröffentlichen des Produkts mit Axios-Fetch und JWT-Token
  updatePublished(status) {
    const { currentProduct } = this.state;
    const currentUser = Auth.getCurrentUser();

    if (!currentProduct) {
      console.log("Kein Produkt ausgewählt");
      return;
    }

    const updatedProduct = {
      ...currentProduct,
      published: status,
    };

    const config = {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader(),
      },
    };
    if (currentUser.roles.includes("ROLE_ADMIN")) {
      axios
          .put(
              `${process.env.REACT_APP_API_URL}/products/admin/${currentProduct.id}`,
              updatedProduct,
              config
          )
          .then((response) => {
            console.log(response.data);
            this.setState((prevState) => ({
              currentProduct: {
                ...prevState.currentProduct,
                published: status,
              },
            }));
          })
          .catch((error) => {
            console.log("Fehler beim Aktualisieren des Produkts:", error);
          });
    } else {
      axios
          .put(
              `${process.env.REACT_APP_API_URL}/products/seller/${currentUser.id}/${currentProduct.id}`,
              updatedProduct,
              config
          )
          .then((response) => {
            console.log(response.data);
            this.setState((prevState) => ({
              currentProduct: {
                ...prevState.currentProduct,
                published: status,
              },
            }));
          })
          .catch((error) => {
            console.log("Fehler beim Aktualisieren des Produkts:", error);
          });
    }
  }

  // Produkt aktualisieren mit Axios-Fetch und JWT-Token
  updateProduct() {
    const { currentProduct } = this.state;
    const currentUser = Auth.getCurrentUser();
    if (!currentProduct) {
      console.log("Kein Produkt ausgewählt");
      return;
    }

    const config = {
      headers: authHeader(),
    };
    if (currentUser.roles.includes("ROLE_ADMIN")) {
      axios
          .put(
              `${process.env.REACT_APP_API_URL}/products/admin/${currentProduct.id}`,
              currentProduct,
              config
          )
          .then((response) => {
            console.log(response.data);
            this.setState({
              message: "Das Produkt wurde erfolgreich aktualisiert!",
            });
          })
          .catch((error) => {
            console.log("Fehler beim Aktualisieren des Produkts:", error);
          });
    } else {
      axios
          .put(
              `${process.env.REACT_APP_API_URL}/products/seller/${currentUser.id}/${currentProduct.id}`,
              currentProduct,
              config
          )
          .then((response) => {
            console.log(response.data);
            this.setState({
              message: "Das Produkt wurde erfolgreich aktualisiert!",
            });
          })
          .catch((error) => {
            console.log("Fehler beim Aktualisieren des Produkts:", error);
          });
    }
  }

  // Produkt löschen mit Axios-Fetch und JWT-Token
  deleteProduct() {
    const { currentProduct } = this.state;
    const currentUser = Auth.getCurrentUser();
    const config = {
      headers: authHeader(),
    };
    if (currentUser.roles.includes("ROLE_ADMIN")) {
      axios
          .delete(`${process.env.REACT_APP_API_URL}/products/admin/${currentProduct.id}`,config)
          .then((response) => response.data)
          .then((data) => {
            this.setState({ products: data });
            this.props.router.navigate("/admin"); // Zurück zur Adminseite navigieren
            window.location.reload(); // Seite neu laden
          })
          .catch((err) => {
            console.log(err);
          });
    } else {
      axios
          .delete(`${process.env.REACT_APP_API_URL}/products/seller/${currentUser.id}/${currentProduct.id}`,config)
          .then((response) => response.data)
          .then((data) => {
            this.setState({ products: data });
            this.props.router.navigate("/products"); // Zurück zur Adminseite navigieren
            window.location.reload(); // Seite neu laden
          })
          .catch((err) => {
            console.log(err);
          });
    }
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }
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