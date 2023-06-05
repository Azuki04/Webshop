import React from 'react';
import { Link } from 'react-router-dom';

const NotFound = () => {
    return (<div>
        <h4>404 - Not Found</h4>
        <p>The page "{ window.location.pathname }" could not be found.</p>
        <p><Link to="/">Return to Home</Link></p>
    </div>)
}

export default NotFound;