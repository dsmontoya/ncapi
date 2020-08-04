# Usage
## Start the server
```bash
./mvnw spring-boot:run
```
## Write an agreement
```bash
curl -X POST localhost:8080/agreements -H "Content-Type: application/json" -d '{"name": "myAgreement", "signedBy": "Jhon Doe", "products": [e": "Product 1", "price": 100.50, "products": [{"name": "Product 2", "price": 50.30, "products": []}]}]}'
```
## Get the agreement
```bash
curl localhost:8080/agreements/myAgreement -H "Content-Type: application/json"
```

You can find a generated file called myAgreement.