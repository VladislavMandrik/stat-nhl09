package com.example.demo.e2e;

import com.example.demo.e2e.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginE2ETest {

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${test.base-url:http://localhost:8080}")
    private String baseUrl;

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;

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
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    @Order(1)
    @DisplayName("E2E: Успешный логин с правильными credentials")
    public void testSuccessfulLogin() {
        String title = driver.getTitle();
        assertTrue(title.contains("Please sign in"));

        loginPage.login(adminUsername, adminPassword);
        assertTrue(driver.getCurrentUrl().contains("/"));
        //добавить проверку заголовка
    }

    @Test
    @Order(2)
    @DisplayName("E2E: Попытка логина с неверным паролем")
    public void testLoginWithWrongPassword() {
        String title = driver.getTitle();
        assertTrue(title.contains("Please sign in"));

        loginPage.wrongLogin(adminUsername, "wrong_password");
        //вынести в отдельный файл с константами(вынести весь открытй текст)
        assertEquals("Неверные учетные данные пользователя", loginPage.getErrorMessage());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}