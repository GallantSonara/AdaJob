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
**API Documentation: Get Job Listings**

This documentation provides information about the GET method for retrieving job listings from the server.

*Endpoint*
GET /joblisting

Request

**Method: GET**
Endpoint: http://localhost:5000/joblisting

**Response**

The response will be a JSON array containing objects representing job listings. Each object will have the following properties:

task_id (number): The ID of the task.
task_title (string): The title of the task.
task_type (string): The type of the task.
reward_type (string): The type of reward for the task.
reward (string): The description of the reward for the task.
deadline (string): The deadline for the task in ISO 8601 format.
task_link (string): The link to the task.
description (string): The description of the task.
enrollment (boolean): Indicates whether enrollment is available for the task.

```JSON
[
    {
        "task_id": 1,
        "task_title": "example task",
        "task_type": "example type",
        "reward_type": "example reward type",
        "reward": "example reward",
        "deadline": "2023-06-10T12:00:00.000Z",
        "task_link": "2023-06-10 00:00:00.000000",
        "description": "asdfaadfadfadf",
        "enrollment": true
    },
    {
        "task_id": 2,
        "task_title": "example title",
        "task_type": "exampel type",
        "reward_type": "example reward",
        "reward": "example reward",
        "deadline": "2023-06-24T00:00:00.000Z",
        "task_link": "https://www.binance.co/asdfsaf/adfadf",
        "description": "adfasdfasdfasdaddfsa \r\nadfasdf\r\nafsaf\r\nafsa\r\na\r\na\r\naF\r\na",
        "enrollment": false
    }
]
```
**Status Codes**

200 OK: The request was successful, and the response body contains the job listings in the expected format.
404 Not Found: The requested endpoint was not found or does not exist.

This API endpoint allows users to login by providing their email and password in the request body. If the login is successful, the server will respond with a JSON object containing an access token. This access token can be used to authenticate subsequent API requests.

Please note that this documentation assumes the server is running locally on http://localhost:5000. Make sure to replace this base URL with the actual URL of the server you are working with.
