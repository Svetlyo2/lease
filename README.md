# Project for SoftUni course Spring Advanced April 2021

This project was generated with Spring Framework and Thymeleaf template engine. 

## Functionality overview
The application is a site called "Dream Leasing" for handling of inquires about car leasing. An anonymous user should be able to access the landing, register and login page.
A registered user can add cars, make inquires, receive leasing offers and apply for lease financing. 

## The general page breakdown looks like this:
### Anonymous users

•	**Index page**
![image](https://user-images.githubusercontent.com/41020366/224549980-034c47d2-81c0-45ad-86e4-e6c51d273ef0.png)

•	**Register / Login pages** - Uses Spring Security

### Registered users

•   **Home page** - Contains short description of the process flow with links to add user profile or a car. 
![home](https://user-images.githubusercontent.com/41020366/224550065-f81b35ed-63fb-4eab-8187-d454abca2bd1.JPG)
•   **Add profile** - Form for input of user profile data

•   **Show profile** - Display profile details with links to edit profile or change email or password

•   **Add car** - Form for input of car data with option to add seller's offer as pdf or image file.
![Car_details](https://user-images.githubusercontent.com/41020366/224550163-7cfa5221-2052-45cc-963c-1c81217b17f7.JPG)
•   **My cars** - Show a list of all cars added by the user with link to the requested leasing offers for each car.

•   **Your offers** - Show a list of all inquiries about leasing offers with links to review the offers.
![image](https://user-images.githubusercontent.com/41020366/224550264-097b1160-87a2-4ac9-ac30-c89501044e93.png)
•   Leasing offer - Display the offer including all parameters and the calculated monthly payment with link to apply for leasing. After applying the user is asked to add seller details.

### Employees
•	  **Offer requests** - Display all requests waiting for answer with link for reply. When reply is selected the inquiry is displayed together with the seller's offer if attached to the request. The employee adds the price conditions, the leasing offer is generated and becomes available for the user.
![image](https://user-images.githubusercontent.com/41020366/224550454-16396948-36fb-422b-b63d-c58caec9b65d.png)
•  	**All offers** - Show a list of all inquiries and their status (requested, presented,revised or applied).

•   **New applications** - Show a list of all received leasing applications and their status.

•   **Statistics** - Display a chart with the daily number of requests for the last week.
![statistics](https://user-images.githubusercontent.com/41020366/224550554-8177efb2-da30-4867-b7d2-123b5913031c.JPG)
### Administrators

•   **Users** - Show a list of all registered users and their roles with options to change their roles.
![users](https://user-images.githubusercontent.com/41020366/224550584-1c6a40e2-cf60-44e1-9b2c-fcbb88bb7bb7.JPG)

•   **Employees** - Show a list of all employees with link to make one of the registered users an employee.   

## Features used:

•	Spring Security for authentication and access-control management

•	MySQL with MariaDB dialect as a database and Spring Data for database access.
 
•	Cloudinary Image API for image storage

•	AJAX used to asynchronously load and display data about received inquiries during last week.

• Error page with user-friendly error messages 

•	Interceptors for page title and notifications for employees with the number of new requests

•	Scheduled task to notify employees about overdue reply to inquiries.

• Responsive Web Page Design based on Bootstrap
