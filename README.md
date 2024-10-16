# 🛠️ Backend API - Spring Boot

This project is a **backend API** developed in **Spring Boot**. It allows for user and product management with JWT authentication and specific roles.

## 🚀 Project Features

- **Authentication** with JWT.
- **User roles**: Admin, User.
- **Product CRUD**: Add, Read, Update, Delete products.
- **Database**: MySQL.
- **Spring Security** for route protection.

## 📝 API Endpoints

### 📦 Users

#### Register a user
- `POST /user/register`
- Request body:
    ```json
    {
      "username": "user",
      "password": "password",
      "role": "ROLE_USER" // Or ROLE_ADMIN
    }
    ```
  - **Response**: `User registered successfully`

#### Login (Generate JWT)
- `POST /user/login`
- Request body:
    ```json
    {
      "username": "user",
      "password": "password"
    }
    ```
  - **Response**: JWT token

### 🛒 Products

#### Create product (Admin only)
- `POST /api/addProduct`
- Request body:
    ```json
    {
      "name": "Product A",
      "price": 100.0,
      "quantity": 10
    }
    ```
  - **Response**: `Product created successfully`

#### Get all products (User and Admin)
- `GET /api/getAll`
  - **Response**: List of products in JSON format

#### Get product by ID (User and Admin)
- `GET /api/getById/{id}`
  - **Response**: Product with the specified ID.

#### Update product (Admin only)
- `PUT /api/update/{id}`
- Request body:
    ```json
    {
      "name": "Updated Product",
      "price": 200.0,
      "quantity": 50
    }
    ```
  - **Response**: `Product updated successfully`

#### Delete product (Admin only)
- `DELETE /api/delete/{id}`
  - **Response**: `No content` if deleted successfully.

## ⚙️ Configuration

### Database
The backend connects to a MySQL database. Make sure MySQL is installed and running.

```properties
spring.application.name=Backend
spring.datasource.url=jdbc:mysql://localhost:3306/bdcrud
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
server.port=8020
