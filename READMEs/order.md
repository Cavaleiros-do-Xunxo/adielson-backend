# Order

This is an example of Order body.


```
{
    "user": "418",
    "delivery": "LOCAL",
    "status": "PREPARING",
    "total": 155.12,
    "orderTime": 5467467465,
    "deliveryTime": 5467467473
}
```

return :

```
{
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
}
```