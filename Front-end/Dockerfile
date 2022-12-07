#Base image
FROM node:18.8-alpine

#Make the 'app' folder the current working directory
WORKDIR /app

#copy 'package.json' and 'package-lock.json'
COPY package*.json ./

#install dependencies
RUN npm install

#copy project files and folders to the current working directory
COPY . .

#open the port
EXPOSE 3000

#run the frontend server
CMD [ "npm", "start" ]