/**
 * checks Local Storage for user item. 
 * @returns If there is a logged in user with accessToken (JWT) return HTTP Authorization header. Otherwise, return an empty object.
 */

export default function authHeader() {
    const user = JSON.parse(localStorage.getItem('user'));
  
    if (user && user.accessToken) {
      return { Authorization: 'Bearer ' + user.accessToken };
    } else {
      return {};
    }
}
  