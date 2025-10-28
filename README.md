# 🛒 E-Commerce RESTful API Platform

<div align="center">
  
  [![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)](https://spring.io/projects/spring-boot)
  [![MongoDB](https://img.shields.io/badge/MongoDB-6.0+-green.svg)](https://www.mongodb.com/)
  [![REST API](https://img.shields.io/badge/API-RESTful-blue.svg)](#)
  
</div>


## 🚀 Overview
A comprehensive full-stack e-commerce platform with **RESTful API architecture** featuring product catalog management, **order processing system**, secure payment handling, and **role-based authentication** for buyers and sellers. Includes purchase history tracking and **analytics dashboard** with real-time seller insights.

## ✨ Key Features
- **🔐 Role-Based Access Control** - Separate buyer and seller functionalities
- **💳 Payment Card Management** - Secure card storage and transaction processing
- **📦 Order Processing** - Complete order lifecycle management
- **📊 Analytics Dashboard** - Sales statistics and performance metrics
- **🛍️ Product Catalog** - Full CRUD operations for inventory management
- **📈 Purchase History** - Comprehensive transaction tracking

## 🛠️ Technology Stack

### Backend (76.5%)
- **Java 17+** - Core programming language
- **Spring Boot** - Application framework
- **Maven** - Dependency management
- **MongoDB** - NoSQL database
- **Spring Data** - Data persistence layer
- **RESTful APIs** - HTTP-based web services

### Frontend (23.5%)
- **JavaScript (17.5%)** - Client-side scripting
- **HTML5 (2.0%)** - Markup structure
- **CSS3 (4.0%)** - Styling and responsive design

## 🏗️ Architecture Patterns
- **MVC Pattern** - Separation of concerns
- **Dependency Injection** - Loose coupling
- **DTO Pattern** - Secure data transfer
- **Service Layer** - Business logic encapsulation

## Setup
Go to src/main/resources/application.properties and replace the URI and database with your own, the one over there is disabled.

## API Reference

#### Get all products

```http
  GET /v1/products
```
Response is list of
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | Name of item |
`imgs`      | `string` | URL of item's image |
`seller`      | `string` |  Name of item's seller |
`code`      | `string` |  Internal code given by seller|
`price`      | `numeric` |  Item's price|
`message`      | `string` |  Additional information|
`status`      | `boolean` |  Success status|

#### Add product

```http
  POST /v1/products
```
Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Name of item |
`imgs`      | `string` | **Required**. URL of item's image |
`seller`      | `string` | **Required**. Name of item's seller |
`code`      | `string` | **Required**. Internal code given by seller|
`price`      | `numeric` | **Required**. Item's price|

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | Name of item |
`imgs`      | `string` | URL of item's image |
`seller`      | `string` |  Name of item's seller |
`code`      | `string` |  Internal code given by seller|
`price`      | `numeric` |  Item's price|
`message`      | `string` |  Additional information|
|`status`      | `boolean` |  Success status |



#### Modify product

```http
  PUT /v1/modify
```

All fields except code and seller will be modfied to match body\
Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Name of item |
`imgs`      | `string` | **Required**. URL of item's image |
`seller`      | `string` | **Required**. Name of item's seller |
`code`      | `string` | **Required**. Code internal code given by seller|
`price`      | `numeric` | **Required**. Item's price|

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | Name of item |
`imgs`      | `string` | URL of item's image |
`seller`      | `string` |  Name of item's seller |
`code`      | `string` |  Internal code given by seller|
`price`      | `numeric` |  Item's price|
`message`      | `string` |  Additional information|
|`status`      | `boolean` |  Success status |

#### Delete product

```http
  DELETE /v1/products/delete?code=String&seller=String
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`seller`      | `string` | **Required**. Name of item's seller |
`code`      | `string` | **Required**. Code internal code given by 

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | Name of item |
`imgs`      | `string` | URL of item's image |
`seller`      | `string` |  Name of item's seller |
`code`      | `string` |  Internal code given by seller|
`price`      | `numeric` |  Item's price|
`message`      | `string` |  Additional information|
|`status`      | `boolean` |  Success status |



#### Add card

```http
  POST /v1/cards
```
Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`number`      | `string` | **Required**. Card's number |
`expDate`      | `string` | **Required**. Format: YYYY-MM 
`cvv`      | `string` | **Required**. Card's cvv 
`amount`      | `numeric` | **Required**. Funds on card 
`owner`      | `string` | **Required**. Card's owner, to whom the card will be added  

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |




#### Add funds to card

```http
  PUT /v1/cards/addAmount?amount=amount
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `amount`      | `numeric` | Amount to be added/substracted|


Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`number`      | `string` | **Required**. Card's number |
`expDate`      | `string` | **Required**. Format: YYYY-MM 
`cvv`      | `string` | **Required**. Card's cvv 
`amount`      | `numeric` | **Required**. Funds on card 
`owner`      | `string` | **Required**. Card's owner

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |




#### Delete card

```http
  DELETE /v1/cards/delete
```


Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`number`      | `string` | **Required**. Card's number |
`expDate`      | `string` | **Required**. Format: YYYY-MM 
`cvv`      | `string` | **Required**. Card's cvv 
`amount`      | `numeric` | **Required**. Funds on card 
`owner`      | `string` | **Required**. Card's owner

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |




#### Place order

```http
   POST /v1/orders/place
```


Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`products`      | `List<ProductsBoughtDTO>` | **Required**. Each product is {code:String, seller:String, message:String, status:Boolean}, code and seller are mandatory |
`buyer`      | `string` | **Required**. Buyer 


Response

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`products`      | `List<ProductsBoughtDTO>` |  Each product is {code:String, seller:String, message:String, status:Boolean} status of false means it hasn't been added in the order  |
`finished`      | `boolean` |  Tells if order is finished, irrelevant here
`message`      | `string` | Additional information
 Buyer 


#### Get buyer's orders

```http
   GET /v1/orders/buyer/{name}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Buyer's name |




Response\
A list of:

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`products`      | `List<ProductsBoughtDTO>` |  Each product is {code:String, seller:String, message:String, status:Boolean} status of false means it hasn't been added in the order  |
`finished`      | `boolean` |  Tells if order is finished
`message`      | `string` | Additional information
 Buyer 




#### Get seller's orders

```http
   GET /v1/orders/seller/{name}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Seller's name |




Response\
A list of:

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`code`      | `string` |  Product's code
`buyer`      | `string` |  Product's buyer|







#### Add user

```http
  POST /v1/users
```


Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |
`password`      | `string` | **Required**. Password, minimum 6 characters 
`type`      | `numeric` | **Required**. 0 = buyer, 1 = seller 


Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`username`      | `string` | Username |
`type`      | `numeric` | Type |

#### Get user info

```http
  GET /v1/users/{name}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |


Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`username`      | `string` | Username |
`type`      | `numeric` | Type |


#### Add to user's history

```http
  POST /v1/users/addHistory/{name}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |

Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`code`      | `string` | Product's code |
`seller`      | `string` | Product's seller |

Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`code`      | `string` | Product's code |
`seller`      | `string` | Product's seller |





#### Get user's history

```http
  GET /v1/users/getHistory/{name}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |


Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`history`      | `List<ProductsBoughtDTO` | Each product being {code:String, seller:String, message:String, status:boolean} |



#### Get product's sell count and buyer count, seller's perspective

```http
  GET /v1/users/{name}/getProductStats?code=String
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |
`code`      | `string` | **Required**. Product's code |


Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`buyers`      | `integer` | Sold to how many |
`count`      | `integer` | How many sold |


#### Get how many products a seller sold and to how many

```http
  GET /v1/users/{name}/getStats
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |



Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`buyers`      | `integer` | Sold to how many |
`count`      | `integer` | How many sold |





#### Delete user

```http
  DELETE /v1/users/delete
```


Body
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
`name`      | `string` | **Required**. Username |
`password`      | `string` | **Required**. Password 
`type`      | `numeric` | **Required**. 0 = buyer, 1 = seller 


Response
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `message`      | `string` | Additional information |
`status`      | `boolean` | Success status |
`username`      | `string` | Username |
`type`      | `numeric` | Type |

