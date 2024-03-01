# Yacht-Charter-Api
Microservices app to charter yachts

## Getting Started
Yacht-Charter-Api is an example of microservices architecture. Microservices communicate to each other through OpenFeign.
It also uses api gateway and global security. The Application has been created for searching yachts, charter them and manage charters.
Data is collected to Mysql database through Jpa. 

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
| POST   | /user/auth/register | Sign up | N/A | [JSON](#register) |
| POST   | /user/auth/authenticate | Sign in | N/A | [JSON](#authenticate) |

### Authorization

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET   | /user/authorization/is-admin/{path}/{mappingMethod} | Check if is admin | USER | N/A |
| GET   | /user/authorization/is-correct-user/{id}/{resource} | Check if is correct user | USER | N/A |
| GET   | /user/authorization/id | Get logged in user id | USER | N/A |

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
| POST   | /search | Add yacht | ADMIN | [JSON](#createyacht) |
| PUT    | /search/{id} | Update specific yacht | ADMIN | [JSON](#updateyacht) |
| DELETE | /search/{id} | Delete specific yacht | ADMIN | N/A |

### Accessories

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /search/accessory | Get accessories | ADMIN | N/A |
| GET    | /search/accessory/{id} | Get specific accessory | ADMIN | N/A 
| POST   | /search/accessory | Add accessory | ADMIN | [JSON](#createaccessory) |
| PUT    | /search/accessory/{id} | Update specific accessory | ADMIN | [JSON](#updateaccessory) |
| DELETE   | /search/accessory/{id} | Delete specific accessory | ADMIN | N/A |


### Pricing

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /pricing/{priceFrom}/{from}/{to} | Get yacht price | USER | N/A |

### Orders

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /orders | Get logged in user orders | USER | N/A |
| GET    | /orders/{id} | Get logged in user specific order | USER | N/A |
| GET    | /orders/archival | Get logged in user archival orders | USER | N/A |
| GET    | /orders/archival/{id} |Get logged in user archival order by id | USER | N/A |
| POST   | /orders | Add order| USER | [JSON](#createorder) |
| PUT    | /orders/{id} | Update specific order | ADMIN | [JSON](#updateorder) |
| DELETE   | /orders/{id} | Delete specific order | ADMIN | N/A |

*(\*) Required USER who created it or ADMIN*

## Request body examples

#### <a id="register">Sign Up (/user/auth/register)</a>
```json
{
	"firstName": "Maciej",
	"lastName": "Jurczak",
	"email": "maciekjurczak123@smh.com",
	"password": "P@ssword123"
}
```

#### <a id="authenticate">Sign In (/user/auth/authenticate)</a>
```json
{
	"email": "maciekjurczak123@smh.com",
	"password": "P@ssword123"
}
```

#### <a id="updateuser">Update User (/users/{id})</a>
```json
{
	"firstName": "Maciej",
	"lastName": "Jurczak",
	"email": "maciekjurczak123@smh.com",
	"password": "P@ssword123",
	"role": "user"
}
```

#### <a id="createyacht">Create Yacht (/search)</a>
```json
{
	"model": "Sasanka",
	"propulsion": "sailing",
	"length": 6.60,
	"capacity": 5,
	"motorPower": 8.0,
	"priceFrom": 150.0,
	"accessories":[
		{"id":1,"name":"tent","yachts":null},
		{"id":2,"name":"sink","yachts":null}]
}
```

#### <a id="updateyacht">Update Yacht (/search/{id})</a>
```json
{
	"model": "Sasanka",
	"propulsion": "sailing",
	"length": 6.60,
	"capacity": 5,
	"motorPower": 14.0,
	"priceFrom": 165.0,
	"accessories":[
		{"id":1,"name":"tent","yachts":null},
		{"id":2,"name":"sink","yachts":null}]
}
```

#### <a id="createaccessory">Create Accessory (/search/accessory)</a>
```json
{
	"name": "tent"
}
```

#### <a id="updateaccessory">Update Accessory (/search/accessory/{id})</a>
```json
{
	"name": "sink"
}
```

#### <a id="createorder">Create Order (/orders)</a>
```json
{
	"userId": 1,
	"yachtId": 1,
	"days": 10,
	"dateFrom": "2024-04-15",
	"dateTo": "2024-04-25",
	"price": 1987.5
}
```

#### <a id="updateorder">Update Order (/orders/{id})</a>
```json
{
	"userId": 2,
	"yachtId": 2,
	"days": 10,
	"dateFrom": "2024-04-15",
	"dateTo": "2024-04-25",
	"price": 1722.5
}
```

## Author

* **Maciej Jurczak** 

See also other projects of [Maciek0475](https://github.com/Maciek0475).

