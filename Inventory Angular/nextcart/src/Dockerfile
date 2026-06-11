# Build Angular application
FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

# Serve with Nginx
FROM nginx:alpine

COPY --from=build /app/dist/nextcart/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]