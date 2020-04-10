# JPA Query Param

JPA Query Param is a library that allows you to add a query parameter to your endpoints.

## Prerequisites

- Spring Data JPA
- The repositories you want to query needs to implement JpaSpecificationExecutor<T>

## Installation

Add the following dependency in your project to start using JPA Query Param.

```bash
implementation 'org.manuel.spring:query-parameter-jpa:0.0.1-SNAPSHOT'
```

## Usage

Add the @EnableQueryParameter annotation in your spring boot application, to import the configuration.

```java
@SpringBootApplication
@EnableQueryParameter
public class SpringBootAppApplication {
```

Then add the @QueryParameter annotation in a controller you would like to use the query param.
Here you can see an example:

```java
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Entity>> findByQuerying(@QueryParameter(entity = Entity.class) Specification<Entity> query) {
        return ResponseEntity.ok(entityService.findAll(query));
    }
```

Then call that endpoint and use the available query operations:

```bash
GET /entities?q=firstName::Manuel;age:<18
```

## Query Operations supported

# Equals (::)

Below an example to match firstName == Manuel
```bash
GET /entities?q=firstName::Manuel
```

# Lower than (:<)

Below an example to match age < 18
```bash
GET /entities?q=age:<18
```

# Lower than or equal (:<=)

Below an example to match age <= 18
```bash
GET /entities?q=age:<=18
```

# Greater than (:>)

Below an example to match age > 18
```bash
GET /entities?q=age:>18
```

# Greater than or equal (:>=)

Below an example to match age >= 18
```bash
GET /entities?q=age:>=18
```

## Concatenate params

It's possible to concatenate params AND (;), OR (|).

As an example:
If we want entities whose firstname is Manuel *and* age less than 18
```bash
GET /entities?q=firstName::Manuel;age:<18
```
If we want entities whose firstname is Manuel *or* age less than 18
```bash
GET /entities?q=firstName::Manuel|age:<18
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)