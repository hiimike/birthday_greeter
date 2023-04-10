# Birthday Greeter

Birthday Greeter is a Java Spring Boot application that automates the process of sending birthday greetings and reminders to your friends. The application reads friend data from either an SQLite database or a flat text file and sends birthday greetings via email and SMS.

## Prerequisites

- JDK 17: Ensure you have JDK 17 installed on your system. You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/#java17).

- Maven: Ensure you have Maven installed on your system. You can download it from [Maven's official website](https://maven.apache.org/download.cgi).

## Running the Application

To run the application from the command line, follow these steps:

1. Navigate to the root directory of the project.  
    ```cd BirthdayGreeter```
2. Use Maven to compile and run the project.     
   ```mvn clean install```   
   ```mvn spring-boot:run```

## Using the Application
### Add/Modify data in the database (text file)
You can add new data to the friends.txt file by following these guidelines:

1. Open the friends.txt file in a text editor of your choice.

2. Each line in the file represents a friend's information, with the following fields separated by spaces:

- Last name
- First name
- Date of birth (in the format yyyy/MM/dd)
- Email address
- To add a new friend, append a new line to the end of the file with the friend's information, ensuring that each field is separated by a single space. For example:

- ```Smith Jane 1990/04/10 jane.smith@foobar.com```   

3. Save the changes to the friends.txt file.

4. Rerun the Birthday Greeter application to process the updated data.

5. Please note that the date of birth should be in the yyyy/MM/dd format, and the email address should be valid. Ensure there are no extra spaces or incorrect formatting to avoid errors when processing the file.

