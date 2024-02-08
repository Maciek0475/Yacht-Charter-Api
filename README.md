# Yacht-Charter-Api
Microservices app to charter yachts

## Getting Started



### Technology Stack
Mostly used:
* [Spring Boot](https://spring.io/)
* [Mysql](https://www.mysql.com/)
* [JWT](https://jwt.io/)

The project is an example of using several technologies, mainly including:
* Spring Security
* Spring Data Jpa
* Spring Cloud
* OpenFeign
* REST
* JWT
* Mysql

### Preparing Database

You have to create database and import `rest-api.sql`. In `application.properties`
you can manipulate connection data as you like.

### Running Localy

## Rest endpoints
Check out list of application endpoints.

### Authentication

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| POST   | user/auth/register | Sign up | N/A | [JSON](#signup) |
| POST   | user/auth/authenticate | Sign in | N/A | [JSON](#signin) |

### Authorization

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET   | user/authorization/is-admin/{path}/{mappingMethod} | Check if is admin | USER | [JSON](#signup) |
| GET   | user/authorization/is-correct-user/{id}/{resource} | Check if is correct user | USER | [JSON](#signin) |
| GET   | user/authorization/id | Get logged in user id | USER | [JSON](#signin) |

### Users

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /users | Get all users | ADMIN | N/A |
| GET    | /users/{id} | Get specific user | USER(*) | N/A |
| PUT    | /users/{id} | Update specific user | ADMIN | [JSON](#updateuser) |
| DELETE | /users/{id} | Delete specific user | ADMIN | N/A |

### Search

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /search/{id}/model | Get yacht model | USER | N/A |
| GET    | /search | Get all yachts | USER | N/A |
| GET    | /search/motor | Get motor yachts | USER | N/A |
| GET    | /search/sailing | Get sailing yachts | USER | N/A |
| GET    | /search/{id} | Get specific yacht | USER | N/A |
| GET    | /search/{id}/price/{from}/{to} | Get yacht price | USER | N/A |
| POST   | /search | Add yacht | ADMIN | [JSON](#createbook) |
| PUT    | /search/{id} | Update specific yacht | ADMIN | [JSON](#updatebook) |
| DELETE | /search/{id} | Delete specific yacht | ADMIN | N/A |

### Accessories

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /search/accessory | Get accessories | ADMIN | N/A |
| GET    | /search/accessory/{id} | Get specific accessory | ADMIN | N/A 
| POST   | /search/accessory/{id} | Add accessory | ADMIN | [JSON](#createthread) |
| PUT    | /search/accessory/{id} | Update specific accessory | ADMIN | [JSON](#updatethread) |
| DELETE   | /search/accessory/{id} | Delete specific accessory | ADMIN | N/A |


### Pricing

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /pricing/{priceFrom}/{from}/{to} | Get specific thread messages | USER | N/A |

### Orders

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /orders | Get logged in user orders | USER | N/A |
| GET    | /orders/{id} | Get logged in user specific order | USER | N/A |
| GET    | /orders/archival | Get logged in user archival orders | USER | N/A |
| GET    | /orders/archival/{id} |Get logged in user archival order by id | USER | N/A |
| POST   | /orders | Add order| USER | [JSON](#createmessage) |
| PUT    | /orders/{id} | Update specific order | ADMIN | [JSON](#updatemessage) |
| DELETE   | /orders/{id} | Delete specific order | ADMIN | N/A |

*(\*) Required USER who created it or ADMIN*

## Request body examples

#### <a id="signup">Sign Up (/auth/authorization)</a>
```json
{
	"firstName": "Jan",
	"lastName": "Kowalski",
	"email": "jan.kowalski@smh.com",
	"password": "password"
}
```

#### <a id="signin">Sign In (/auth/authentication)</a>
```json
{
	"email": "jan.kowalski@smh.com",
	"password": "password"
}
```

#### <a id="updateuser">Update User (/users/{id})</a>
```json
{
	"firstName": "Jan",
	"lastName": "Kowalski",
	"email": "jan.kowalski@smh.com",
	"password": "password"
}
```

#### <a id="createbook">Create Book (/books)</a>
```json
{
	"name": "Book",
	"publicationYear": "2023",
	"description": "An example of book"
}
```

#### <a id="updatebook">Update Book (/books/{id})</a>
```json
{
	"name": "Updated Book",
	"publicationYear": "2023",
	"description": "An updated example of book"
}
```

#### <a id="createthread">Create Thread (/threads)</a>
```json
{
	"name": "Thread",
	"bookId": "1",
	"content": "What do you think about this book?"
}
```

#### <a id="updatethread">Update Thread (/threads/{id})</a>
```json
{
	"name": "Updated Thread",
	"bookId": "1",
	"content": "What do you think about this book?"
}
```

#### <a id="createmessage">Create Message (/threads/{id}/messages)</a>
```json
{
	"content": "I think this book is good."
}
```

#### <a id="updatemessage">Update Message (/messages/{id})</a>
```json
{
	"content": "I think this book is great!"
}
```

## Author

* **Maciej Jurczak** 

See also other projects of [Maciek0475](https://github.com/Maciek0475).

