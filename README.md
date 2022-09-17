## Clear Solutions Java Junior Test Task

## Task
<details>
<summary>Details</summary>

[requirements pdf](https://drive.google.com/file/d/1SOam3lWLcKLiBaWZ75I-6zZUrQvGUA0t/view)

## Java practical test assignment
You need to create a RESTful API based on the web Spring Boot application:
controller, responsible for the resource named Users.
1. It has the following fields:
   - Email (required). Add validation against email pattern
   - First name (required)
   - Last name (required)
   - Birth date (required). Value must be earlier than current date
   - Address (optional)
   - Phone number (optional)
2. It has the following functionality:
   - Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
   - Update one/some user fields
   - Update all user fields
   - Delete user
   - Search for users by birth date range. Add the validation which checks that “From” is less than “To”. Should return a list of objects
## 3. Code is covered by unit tests using Spring
4. Code has exception handlers
5. Use of database is not necessary
6. Latest version of Spring Boot. Java version of your choice

</details>

## Summary
All subtasks of the assignment are completed.
## Endpoints
- `POST /users` creates user with `DateRangeDto` request body. Returns `201 CREATED` and `UserDataDto` of created user.

- `GET /users` finds users with query parameters (it converts to `DateRangeDto` internally). Returns `200 OK` and list of `UserDataDto`.

- `PUT /users/{userId}` updates user by `userId` with `DateDataDto` in request body. Returns `200 OK` and updated `UserDataDto`.

- `PATCH /users/{userId}` partly updates user by `userId` with JSON parameters. Returns `200 OK` and updated `UserDataDto`.

- `DELETE /users/{userId}` deletes user by `userId`. Returns `200 OK` and deleted `UserDataDto`.

## DTOs

### Input Entities
```
UserDataDto {
    "id": 28,
    "email": "itsmaild@mail.com",       - required, validated email
    "firstName": "Vasyl",               - required
    "lastName": "Petrovych",            - required
    "birthDate": "2000-09-14",          - required, can't be in future or less than `user.age.minimum.years`(validation.properties) value
    "phone": "0000000000",              - nullable, not validated
    "address": "Adress ave, 46"         - nullable, not validated
}
```
---


### Output entities
```
UserDataDto {
   *** repeats UserDataDto on this point 
}
```

## Validation
Validation happens in Service layer programmatically. For field `birthDate` and birthdate range entity created custom annotations. Validation properties and messages are externalized in `validation.properties` file in `resource` dir.

## Exception handling
All controller exceptions are handled by `@ControllerAdvice` exception handler. It translates internal exceptions in ResponseEntities with response codes