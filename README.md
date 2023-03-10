# Inventory API

This is a simple API for recording inventory items using spring-boot.
actually this is clone from my other repo https://github.com/ilham-openbeta/spring-inventory-jdbc
the differences are :

- using Java 17
- Spring Boot 3
- Service separation

Some features is not working & removed because of upgrading java version.

Features :

- JWT Authentication

- SpringDoc OpenAPI

- Native Query (JDBC Template)

Tested on :

- OpenJDK 17

- MariaDB 10.4.16

## How to Install

1. Clone this repository.

```bash
git clone https://github.com/ilham-openbeta/spring-inventory-jdbc.git
```

2. Import database/inventory.sql file to the MySQL database server.

3. Configure application.yml file at src/main/resources directory or you can configure these environment variables :

| Name    | Description       | Default          |
|---------|-------------------|------------------|
| PORT    | Server Port       | 8080             |
| API_KEY | JWT Secret Key    | defaultSecretKey |
| DB_HOST | Database Host     | localhost        |
| DB_PORT | Database Port     | 3306             |
| DB_NAME | Database Name     | inventory        |
| DB_USER | Database User     | root             |
| DB_PASS | Database Password | -                |

4. Run the application (development).

```bash
mvnw spring-boot:run
```

Open the swagger docs at this URL for a list of available API endpoints. To get an authorization token, use signup then
signin.

```bash
http://localhost:8080/api/swagger-ui.html
```

3. Debugging : Configure your IDE to debug the main class (InventoryApiApplication.java).

## How to build

1. Build project.

```bash
mvnw clean install
```

2. Run the application after build finished.

```bash
java -jar target/inventory-api-0.0.3-SNAPSHOT.jar
```

## TODO

- Unit Testing
- Fix a lot of error

## License

MIT. See the LICENSE file.
