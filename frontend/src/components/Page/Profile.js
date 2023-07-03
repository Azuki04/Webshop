import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import Auth from "../services/Auth";
import "../css/UserProfil.css"
export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            redirect: null,
            userReady: false,
            currentUser: { username: "" }
        };
    }

    componentDidMount() {
        const currentUser = Auth.getCurrentUser();

        if (!currentUser) this.setState({ redirect: "/home" });
        this.setState({ currentUser: currentUser, userReady: true })
    }

    render() {

        if (this.state.redirect) {
            return <Navigate to={this.state.redirect} />
        }
        const { currentUser } = this.state;

        return (
            <div>
                {this.state.userReady ? (
                    <div>
                            <h4>{currentUser.username} Profile</h4>

                        <div className="container">
                    <div className="profile-info">
                        <div className="profile-content">
                            <div className="profile-image-container">
                                <img
                                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                                    alt="profile-img"
                                    className="profile-img"
                                />
                                <div className="edit-button-container">
                                    <button className="edit-button">Edit</button>
                                </div>
                            </div>
                            <table className="profile-table">
                                <tbody>
                                <tr>
                                    <td><strong>Token:</strong></td>
                                    <td>{currentUser.accessToken.substring(0, 20)} ... {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}</td>
                                </tr>
                                <tr>
                                    <td><strong>Id:</strong></td>
                                    <td>{currentUser.id}</td>
                                </tr>
                                <tr>
                                    <td><strong>Email:</strong></td>
                                    <td>{currentUser.email}</td>
                                </tr>
                                <tr>
                                    <td colSpan="2"><strong>Authorities:</strong></td>
                                </tr>
                                {currentUser.roles && currentUser.roles.map((role, index) => (
                                    <tr key={index}>
                                        <td colSpan="2">{role}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                        </div>
                    </div>
                ) : null}
            </div>
        );
    }
}
