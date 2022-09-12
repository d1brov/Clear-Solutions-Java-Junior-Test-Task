<details>
<summary>technical assignment</summary>

[requirements pdf](https://drive.google.com/file/d/1SOam3lWLcKLiBaWZ75I-6zZUrQvGUA0t/view)

### Java practical test assignment
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
3. Code is covered by unit tests using Spring
4. Code has exception handlers
5. Use of database is not necessary
6. Latest version of Spring Boot. Java version of your choice

</details>