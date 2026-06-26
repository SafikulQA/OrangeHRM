package piston;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Orangehrm {

    @Test
    public void LoginandAddUserOrangheHrm() {
        WebDriver driver = null;
        String newUsername = "admin_user_" + System.currentTimeMillis();

        try {
            // Step 1: Setup ChromeDriver automatically.
            WebDriverManager.chromedriver().setup();

            // Step 2: Open Chrome browser.
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 3: Create explicit wait.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Step 4: Open OrangeHRM login page.
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Step 5: Login with valid username and password.
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
            driver.findElement(By.name("password")).sendKeys("admin123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            // Step 6: Verify Dashboard page.
            WebElement dashboard = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']"))
            );
            Assert.assertTrue(dashboard.isDisplayed(), "Dashboard should be visible after login.");

            // Step 7: Click Admin menu.
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='Admin']")
            )).click();

            // Step 8: Click Add button.
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Add']")
            )).click();

            // Step 9: Select User Role as Admin.
            selectDropdownValue(wait, "User Role", "Admin");

            // Step 10: Select Status as Enabled.
            selectDropdownValue(wait, "Status", "Enabled");

            // Step 11: Enter username, password, and confirm password.
            typeIntoField(wait, "Username", newUsername);
            typeIntoField(wait, "Password", "Admin@12345");
            typeIntoField(wait, "Confirm Password", "Admin@12345");

            // Step 12: Enter employee name and select first suggestion.
            selectEmployee(wait, "a");

            // Step 13: Click Save button. If the selected employee already has a user account,
            // try another employee from the demo data.
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Save']")
            )).click();

            boolean userAdded = isSuccessMessageVisible(driver);

            if (!userAdded) {
                for (String searchText : List.of("e", "i", "o", "u", "m", "r", "s", "t")) {
                    selectEmployee(wait, searchText);
                    wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[normalize-space()='Save']")
                    )).click();

                    if (isSuccessMessageVisible(driver)) {
                        userAdded = true;
                        break;
                    }
                }
            }

            // Step 14: Verify success message.
            Assert.assertTrue(userAdded, "Success message should be displayed after adding the user.");

            // Step 15: Print success message in terminal.
            System.out.println("SUCCESS: Admin added new user successfully. Username: " + newUsername);
        } finally {
            // Step 16: Close browser.
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void selectDropdownValue(WebDriverWait wait, String label, String value) {
        By dropdown = By.xpath("//label[text()='" + label + "']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
        By option = By.xpath("//div[@role='option']//span[text()='" + value + "']");

        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private void typeIntoField(WebDriverWait wait, String label, String value) {
        By input = By.xpath("//label[text()='" + label + "']/ancestor::div[contains(@class,'oxd-input-group')]//input");
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(input));
        field.clear();
        field.sendKeys(value);
    }

    private void selectEmployee(WebDriverWait wait, String searchText) {
        By employeeInput = By.xpath("//input[@placeholder='Type for hints...']");
        By firstEmployeeOption = By.xpath("//div[@role='option'][not(contains(.,'Searching')) and not(contains(.,'No Records Found'))][1]");

        WebElement employee = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeInput));
        employee.clear();
        employee.sendKeys(searchText);
        wait.until(ExpectedConditions.elementToBeClickable(firstEmployeeOption)).click();
    }

    private boolean isSuccessMessageVisible(WebDriver driver) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".oxd-toast--success")
            )).isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }
}
