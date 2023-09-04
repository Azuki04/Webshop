import React from "react";

const GlobalStorageContext = React.createContext();

class GlobalStorageProvider extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cartItems: [],
        };
    }

    setCartItems = (cartItems) => {
        this.setState({ cartItems });
    }

    render() {
        const { children } = this.props;
        const { cartItems } = this.state;

        return (
            <GlobalStorageContext.Provider value={{ cartItems, setCartItems: this.setCartItems }}>
                {children}
            </GlobalStorageContext.Provider>
        );
    }
}

export const GlobalStorageConsumer = GlobalStorageContext.Consumer;
export default GlobalStorageContext;
export { GlobalStorageProvider } ;
