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
Then add the @QueryParameter annotation in a controller you would like to use the query param.
Here you can see an example:

```java
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Parent>> findByPage(@QueryParameter(entity = Entity.class) Specification<Entity> queryCriteria) {
        return ResponseEntity.ok(parentService.findAll(queryCriteria));
    }
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)