# OrderItem

This is an example of OrderItem body.


```
{
    "order": "598",
    "menuItem": "25",
    "price": 155.12
}
```

return :

```
{
    "id": 90,
    "order": {
        "id": 598,
        "user": {
            "id": 418,
            "name": "Schneider",
            "address": "Bobos Street, nº0",
            "email": "schneider@fakemail.com",
            "cpf": 1.234567891E10,
            "password": "abc123",
            "phone": 2345678.0,
            "role": "CUSTOMER"
        },
        "delivery": "LOCAL",
        "status": "PREPARING",
        "total": 155.12,
        "orderTime": 5467467465,
        "deliveryTime": 5467467473
    },
    "item": {
        "id": 25,
        "name": "potato bread",
        "price": 3.98,
        "image": "www.fakerurl.com/image.png",
        "description": "Potato bread is a form of bread in which potato flour",
        "inMenu": false,
        "category": {
            "id": 876,
            "name": "food",
            "description": "only food"
        }
    },
    "price": 155.12
}
```