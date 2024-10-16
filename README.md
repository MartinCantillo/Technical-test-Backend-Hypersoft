# 🛠️ Backend API - Spring Boot

Este proyecto es un **backend API** desarrollado en **Spring Boot**. Permite la gestión de usuarios y productos con autenticación JWT y roles específicos.

## 🚀 Características del Proyecto

- **Autenticación** con JWT.
- **Roles de usuario**: Admin, User.
- **CRUD de productos**: Agregar, Leer, Actualizar, Eliminar productos.
- **Base de datos**: MySQL.
- **Spring Security** para la protección de rutas.

## 📝 Endpoints del API

### 📦 Usuarios

#### Registrar un usuario
- `POST /user/register`
- Cuerpo de la solicitud:
    ```json
    {
      "username": "usuario",
      "password": "contraseña",
      "role": "ROLE_USER" // O ROLE_ADMIN
    }
    ```
  - **Respuesta**: `User registered successfully`

#### Login (Generar JWT)
- `POST /user/login`
- Cuerpo de la solicitud:
    ```json
    {
      "username": "usuario",
      "password": "contraseña"
    }
    ```
  - **Respuesta**: JWT token

### 🛒 Productos

#### Crear producto (Solo Admin)
- `POST /api/addProduct`
- Cuerpo de la solicitud:
    ```json
    {
      "name": "Producto A",
      "price": 100.0,
      "quantity": 10
    }
    ```
  - **Respuesta**: `Producto creado correctamente`

#### Obtener todos los productos (User y Admin)
- `GET /api/getAll`
  - **Respuesta**: Lista de productos en formato JSON

#### Obtener producto por ID (User y Admin)
- `GET /api/getById/{id}`
  - **Respuesta**: Producto con el ID especificado.

#### Actualizar producto (Solo Admin)
- `PUT /api/update/{id}`
- Cuerpo de la solicitud:
    ```json
    {
      "name": "Producto actualizado",
      "price": 200.0,
      "quantity": 50
    }
    ```
  - **Respuesta**: `Producto actualizado correctamente`

#### Eliminar producto (Solo Admin)
- `DELETE /api/delete/{id}`
  - **Respuesta**: `No content` si se elimina correctamente.

## ⚙️ Configuración

### Base de datos
El backend se conecta a una base de datos MySQL. Asegúrate de tener MySQL instalado y funcionando.

```properties
spring.application.name=Backend
spring.datasource.url=jdbc:mysql://localhost:3306/bdcrud
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
server.port=8020
## 🔑 Autenticación JWT
Este proyecto utiliza JWT para autenticar usuarios. El token JWT se debe incluir en las solicitudes protegidas, como la creación y edición de productos.

## 🛠️ Tecnologías utilizadas
- **Spring Boot** para la creación del API REST.
- **Spring Security** para autenticación y autorización.
- **JWT** para manejar tokens de acceso.
- **JPA / Hibernate** para la interacción con la base de datos.
- **MySQL** como base de datos.
- **JUnit / Mockito** para pruebas unitarias.

## 🚀 Pruebas

### Dependencias de pruebas:
- **JUnit 5**
- **Mockito**

