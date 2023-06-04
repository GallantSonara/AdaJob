# AdaJob
this for capstone project product-based
REST API Documentation
This repository contains the documentation for the REST API provided by the application. The API allows users to register by sending a POST request to the /users endpoint.

# Register User
Registers a new user in the system.

URL: http://localhost:5000/users

**Method: POST**

Request Body:
```JSON
{
  "name": "Balya Aulia Assiddiqi",
  "email": "email@gmail.com",
  "password": "123456",
  "confPassword": "123456"
}
```

Response JSON:
```JSON
{
  "msg": "Register Berhasil"
}
```

# User Login
Authenticates a user and generates an access token.

URL: http://localhost:5000/login

**Method: POST**

Request Body:
```JSON
{
  "email": "email@gmail.com",
  "password": "123456"
}
```

Response JSON:
```JSON
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiJCYWx5YSBBdWxpYSBBc3NpZGRpcWkiLCJlbWFpbCI6ImVtYWlsQGdtYWlsLmNvbSIsImlhdCI6MTY4NTYwMjU5NiwiZXhwIjoxNjg1NjAyNjE2fQ.DPXVQ53yx81YwiHaoXQgpqZiqqj3zA3l5rUjJIgDbzw"
}
```

This API endpoint allows users to login by providing their email and password in the request body. If the login is successful, the server will respond with a JSON object containing an access token. This access token can be used to authenticate subsequent API requests.

Please note that this documentation assumes the server is running locally on http://localhost:5000. Make sure to replace this base URL with the actual URL of the server you are working with.
