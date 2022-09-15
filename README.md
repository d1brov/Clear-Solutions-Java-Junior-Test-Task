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

- `GET /users/birthdate?from=yyyy-MM-dd&to=yyyy-MM-dd` finds users with query parameters (it converts to `DateRangeDto` internally). Returns `200 OK` and list of `UserDataDto`.

- `PUT /users/{userId}/update` updates user by `userId` with `DateDataDto` in request body. Returns `200 OK` and updated `UserDataDto`.

- `PUT /users/{userId}/update/parameters?email=xxx&firstName=yyy&...` partly updates user by `userId` with querry parameters. Returns `200 OK` and updated `UserDataDto`.

- `DELETE /users/{userId}` deletes user by ID. Returns `200 OK` and deleted `UserDataDto`.

## DTOs

### Input Entities
```
UserDataDto {
    "id": 28,
    "email": "itsmaild@mail.com",       --required, validated email
    "firstName": "Vasyl",               - required
    "lastName": "Petrovych",            - required
    "birthDate": "2000-09-14",          - required, can't be in future or less than `user.age.minimum.years`(validation.properties) value
    "phone": "0000000000",              - nullable, not validated
    "address": "Adress ave, 46"         - nullable, not validated
}
```
---

It is internal validated input DTO
```
DateRangeDto {
    "from":"yyyy-MM-dd";        --required, from can't be after to and vice versa
    "to":"from":"yyyy-MM-dd";   --required
}
```

### Output entities
```
UserDataDto {
   *** repeats UserDataDto on this point 
}
```

## Validation
Validation happens in Service layer by programmatical validator. For field `birthDate` and `DateRangeDto` entity created custom annotations. Validation properties and messages are externalized in `validation.properties` file in `resource` dir.

## Exception handling
All controller exceptions are handled by `@ControllerAdvice` exception handler. It translates internal exceptions in ResponseEntities with response codes

## Testing
I couldn't achieve 100% coverage due to some `MockMvc` flaws of `MockMvc`. Details in `findUsers_byValidRange_foundUsers()` test comments.
Also had some problems with creating pure Unit tests without context-loaded properties. Need more experience in Spring.
<details><summary>Testing stats</summary>

![](/readme_images/testing_status.png)
</details>