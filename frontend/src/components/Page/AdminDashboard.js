import React from "react";
import axios from 'axios';
import { Link } from "react-router-dom";
import { Button } from "../Button";
import "../css/Products.css";
import authHeader from "../services/Auth-header";


class AdminDashboard extends React.Component {
    constructor(props) {
        super(props);
        this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);

        this.removeAllProducts = this.removeAllProducts.bind(this);
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

    // fetch get all products endpoint only for admin
    componentDidMount() {
        // fetch the jwt token from local storage
        const config = {
            headers:
                authHeader()
        };
        axios.get(process.env.REACT_APP_API_URL+"/products/admin", config)
            .then(response => response.data)
            .then(data => this.setState({products: data}))
            .catch(err => {console.log(err)})
    }

    // fetch delete all products only for admin with jwt token
    removeAllProducts() {
        // fetch the jwt token from local storage
        const config = {
            headers:
                authHeader()
        };
        axios.delete(process.env.REACT_APP_API_URL+"/products/admin", config)
            .then(response => response.data)
            .then(data => this.setState({products: data}))
            .catch(err => {console.log(err)})
        alert("you have successfully deleted if not reload your page");
    }

    //search for title only for admin with jwt token
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
        axios.get(process.env.REACT_APP_API_URL+"/products/admin?title=" + title, config)
            .then(response => response.data)
            .then(data => this.setState({products: data}))
            .catch(err => {console.log(err)})
    }


    render() {
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
                    <h4>Admin Dashboard</h4>
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
                                    <img src=".\img\blob.jpg" alt="Product_Picture" />
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
                    <div style={{ margin: "35px" }}>
                        <Button buttonStyle="btn--delete" onClick={this.removeAllProducts}>
                            Remove All
                        </Button>
                    </div>
                </div>
            );
        }
    }
}
export default AdminDashboard;
