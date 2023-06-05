# containerize a react app
# see https://dzone.com/articles/how-to-dockerize-reactjs-app

# Step 1
FROM node:12-alpine as build-step

RUN mkdir /app
WORKDIR /app
COPY package.json /app

RUN npm install
COPY . /app

RUN npm run build

# Stage 2
FROM nginx:1.17.1-alpine
COPY --from=build-step /app/build /usr/share/nginx/html

