# Intro

Dies ist ein Step-by-Step Repo für ein ReactJA-basiertes Frontend eines Online-Quizzes.

Die Multiuser-Funktionalität wurde von [https://www.bezkoder.com/react-jwt-auth/](https://www.bezkoder.com/react-jwt-auth/) übernommen. 

Wichtig: Das dazugehörige Backend muss "unsichere" JWT-Tokens unterstützen, siehe [https://www.bezkoder.com/spring-boot-jwt-authentication/](https://www.bezkoder.com/spring-boot-jwt-authentication/)

## Bugs fixed: 

1. *login* und *register* Komponenten nutzen neue Form validation API: "form" statt "Form" und "input" statt "Input"
2. API_URL in AuthService


## Using Environment Variables

* for configuration things like API-URLs, image paths etc.
* place variables in *.env* file in root directory.
* `.env` should be listed in .gitignore file...
* begin variable names with `REACT_APP`, otherwise they are ignored
* access environment variables like `process.env.REACT_APP_API_URL`

### Available Scripts

In the project directory, you can run:

### `docker build`

Create a docker image with NPM and the Frontend

#### `npm install`

Downloads & installs all the packages mentioned in package.json. \
Do this the first before attempting to build or run your app.

#### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

#### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

#### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.
