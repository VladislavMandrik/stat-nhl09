package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class Nhl09ServiceApplicationTests {

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testUpload() {
        driver.get("http://localhost:8080/login");

        WebElement searchInputLogin = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder='Username']")));
        searchInputLogin.sendKeys(adminUsername);

        WebElement searchInputPassword = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder='Password']")));
        searchInputPassword.sendKeys(adminPassword);

        WebElement searchButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-lg.btn-primary.btn-block")));
        searchButton.click();

        WebElement resultTitle = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Welcome to statistics']")));
        assertTrue(resultTitle.isDisplayed(), "Заголовок не отображается");

        WebElement searchUploadButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[value='Upload']")));
        searchUploadButton.click();

        Select firstTeamSelect = new Select(driver.findElement(By.name("firstTeam")));
        firstTeamSelect.selectByValue("HAM");

        Select firstGoalsSelect = new Select(driver.findElement(By.name("firstGoals")));
        firstGoalsSelect.selectByValue("1");

        Select totalSelect = new Select(driver.findElement(By.name("total")));
        totalSelect.selectByValue("SO");

        Select secondGoalsSelect = new Select(driver.findElement(By.name("secondGoals")));
        secondGoalsSelect.selectByValue("2");

        Select secondTeamSelect = new Select(driver.findElement(By.name("secondTeam")));
        secondTeamSelect.selectByValue("HFD");

        WebElement choosePlayerstatFileButton = driver.findElement(By.name("playerstat"));
        String filePlayerstatPath = System.getProperty("user.dir")
                + "/src/test/test_data/HAM-HFD_playerstat_16_04_23_15_35_03.csv";
        choosePlayerstatFileButton.sendKeys(filePlayerstatPath);

        WebElement chooseTeamstatFileButton = driver.findElement(By.name("teamstat"));
        String fileTeamstatPath = System.getProperty("user.dir")
                + "/src/test/test_data/HAM-HFD_teamstat_16_04_23_15_35_03.csv";
        chooseTeamstatFileButton.sendKeys(fileTeamstatPath);

        WebElement searchUploadStatButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-primary")));
        searchUploadStatButton.click();

        WebElement successesMessage = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-primary")));
        assertTrue(successesMessage.isDisplayed(), "Ошибка загрузки результатов");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}