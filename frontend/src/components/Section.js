import React from "react";
import { Routes, Route } from "react-router-dom";

import NotFound from "./Page/NotFound";
import AddProduct from "./Page/AddProduct";
import EditProduct from "./Page/EditProduct";
import SalesMonitoring from "./Page/SalesMonitoring";
import ProductDetails from "./Page/ProductDetails";
import AboutUs from "./Page/AboutUs";
import Cart from "./Page/Cart";
import Home from "./Page/Home";
import Payment from "./Page/Payment";
import Login from "./Page/Login";
import Register from "./Page/Register";
import Profile from "./Page/Profile";
import AdminDashboard from "./Page/AdminDashboard";
import Contact from "./Page/Contact";

class Section extends React.Component {
  render() {
    return (
      <section>
        <Routes>
          <Route path="/" >
            <Route path="" index element={<Home/>} />
            <Route path="products" element={<SalesMonitoring />} />
            <Route path="editProduct/:id" element={<EditProduct />} />
            <Route path="products/detail/:id" element={<ProductDetails />} />
            <Route path="cart" element={<Cart />} />
            <Route path="admin" element={<AdminDashboard />} />
            <Route path="payment" element={<Payment />} />
            <Route path="add" element={<AddProduct />} />
            <Route path="about" element={<AboutUs />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/contact" element={<Contact />} />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </section>
    );
  }
}

export default Section;
