import React from "react";
import "../css/Contact.css"; // Importiere das CSS-Styling

class Contact extends React.Component {
    render() {
        return (
            <div>
            <h4>Contact</h4>
            <div className="contact-container">

                <p className="welcome-text">Welcome to our shop! If you have any questions or concerns, you can reach us using the following information:</p>
                <div className="info-container">
                    <p className="heading">Main Headquarters:</p>
                    <p>Address: Winterthur, Switzerland</p>
                    <p>Phone: +41 123 456 789</p>
                    <p>Email: info@myshop.com</p>
                </div>
            </div>
            </div>
        );
    }
}

export default Contact;
