package com.example.demo.e2e;

import com.example.demo.e2e.pages.LoginPage;
import com.example.demo.e2e.pages.UploadPage;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadE2ETest {

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${test.base-url:http://localhost:8080}")
    private String baseUrl;

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private UploadPage uploadPage;

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
        uploadPage = new UploadPage(driver, wait);
    }

    @Test
    @DisplayName("E2E: Успешная загрузка файлов статистики")
    public void testUploadMatchStatistics() {
        String filePlayerstatPath = System.getProperty("user.dir") + "/src/test/test_data/HAM-HFD_playerstat_16_04_23_15_35_03.csv";
        String fileTeamstatPath = System.getProperty("user.dir") + "/src/test/test_data/HAM-HFD_teamstat_16_04_23_15_35_03.csv";

        String title = driver.getTitle();
        assertTrue(title.contains("Please sign in"));

        loginPage.login(adminUsername, adminPassword);
        uploadPage.uploadMatchStatistics("HAM", "1", "SO", "2", "HFD",
                filePlayerstatPath, fileTeamstatPath);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}