Screencast Link - https://drive.google.com/file/d/1gILeig9OkpblrS-25jtiQWhoWn_aJMvK/view?usp=sharing



# OrangeHRM Automation Project

This project is a simple automation test for the OrangeHRM demo website.
It uses Java with Selenium WebDriver and TestNG to open the browser, log in to OrangeHRM, go to the Admin page, and add a new user.

## What We Used

- **Java 21: Used as the programming language for writing the automation code.
- **Maven**: Used to manage project dependencies and run the test.
- **Selenium WebDriver**: Used to control the Chrome browser and interact with web elements.
- **ChromeDriver**: Used to run the test in the Google Chrome browser.
- **WebDriverManager**: Used to automatically download and set up the correct ChromeDriver version.
- **TestNG**: Used as the testing framework to run the test and check the result.
- **OrangeHRM Demo Site**: Used as the application for testing.

## Test Scenario

The test performs these steps:

1. Opens the Chrome browser.
2. Goes to the OrangeHRM login page.
3. Logs in with the demo admin user.
4. Verifies that the Dashboard page is displayed.
5. Opens the Admin menu.
6. Clicks the Add button.
7. Selects User Role as Admin.
8. Selects Status as Enabled.
9. Enters username, password, and confirm password.
10. Selects an employee from the suggestion list.
11. Saves the new user.
12. Verifies that the success message is displayed.
13. Closes the browser.

## Project Files

- `pom.xml`: Contains Maven dependencies and build configuration.
- `testng.xml`: Contains the TestNG suite configuration.
- `src/test/java/piston/Orangehrm.java`: Contains the Selenium automation test code.
- `target/surefire-reports`: Contains the test execution reports after running the test.

## How to Run

Open the terminal in the `admin` folder and run:

```bash
mvn test
```

After the test runs, the report will be generated in:

```text
target/surefire-reports
```

## Notes

- Make sure Google Chrome is installed on your system.
- Internet connection is required because the test opens the live OrangeHRM demo website.
- The test uses demo login details:

```text
Username: Admin
Password: admin123
```
