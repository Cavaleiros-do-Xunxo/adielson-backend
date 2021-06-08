# Pastelaria Adielson !

This is the back-end service that controls the database and also provides for the frontend.

Paths:
```
Users:
    GET /users			Returns all registered Users
    GET /users?var=value	Find User by Filter
    GET /users/{id}		Find User by ID
    DELETE /users/{id}		Delete a user by ID
    PUT /users			Edit a User

Categories:
    GET /category		Returns all registered Categories
    GET /category/{id}		Find Category by ID
    POST /category		Create a new Category
    DELETE /category/{id}       Delete a Category by ID
    PUT /category		Edit a Category

MenuItems:
    GET /items			Returns all registered MenuItems
    GET /items/{id}             Find MenuItem by ID
    POST /items			Create a new MenuItem
    DELETE /items/{id}	        Delete a MenuItem by ID
    PUT /items			Edit a MenuItem
    PATCH /items                Edit MenuItem "inMenu"

Orders:
    GET /order			Returns all registered Orders
    GET /order/{id}             Find Order by ID
    POST /order			Create a new Order
    DELETE /order/{id}          Delete a Order by ID


OrderItems:
    GET /orderItem              Returns all registered OrderItems
    GET /orderItem/{order}      Find OrderItems by Order
    POST /orderItem             create a new OrderItem
    DELETE /orderItem/{id}      Delete a OrderItem by ID
    
Authentication:
    POST /auth/register
```
<br>
Body examples:

[Users](./READMEs/user.md) - [MenuItem](./READMEs/menuitem.md) - [Category](./READMEs/category.md) - [Order](./READMEs/order.md) - [OrderItem](./READMEs/orderitem.md) 