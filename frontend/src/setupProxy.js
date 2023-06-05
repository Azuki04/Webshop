const { createProxyMiddleware } = require("http-proxy-middleware");

/**
 * HttpOnly Cookie approach works if the React app and the back-end server hosted in same domain. 
 * So we need to use http-proxy-middleware for local development.
 * @param {*} app 
 */
module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: "http://localhost:8080",
      changeOrigin: true,
    })
  );
};
