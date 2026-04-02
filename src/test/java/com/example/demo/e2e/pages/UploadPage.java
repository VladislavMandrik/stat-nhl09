package com.example.demo.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UploadPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "[value='Upload']")
    private WebElement uploadButton;

    @FindBy(name = "firstTeam")
    private WebElement firstTeamSelect;

    @FindBy(name = "secondTeam")
    private WebElement secondTeamSelect;

    @FindBy(name = "firstGoals")
    private WebElement firstGoalsSelect;

    @FindBy(name = "secondGoals")
    private WebElement secondGoalsSelect;

    @FindBy(name = "total")
    private WebElement totalSelect;

    @FindBy(name = "playerstat")
    private WebElement choosePlayerstatFileButton;

    @FindBy(name = "teamstat")
    private WebElement chooseTeamstatFileButton;

    @FindBy(css = ".btn.btn-primary")
    private WebElement uploadStatButton;

    @FindBy(css = ".alert.alert-primary")
    private WebElement successesMessage;

    public UploadPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickUploadButton() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
        uploadButton.click();
    }

    public void selectFirstTeam(String teamValue) {
        Select firstTeam = new Select(firstTeamSelect);
        firstTeam.selectByValue(teamValue);
    }

    public void selectFirstGoals(String goalsValue) {
        Select firstGoals = new Select(firstGoalsSelect);
        firstGoals.selectByValue(goalsValue);
    }

    public void selectTotal(String totalValue) {
        Select total = new Select(totalSelect);
        total.selectByValue(totalValue);
    }

    public void selectSecondGoals(String goalsValue) {
        Select secondGoals = new Select(secondGoalsSelect);
        secondGoals.selectByValue(goalsValue);
    }

    public void selectSecondTeam(String teamValue) {
        Select secondTeam = new Select(secondTeamSelect);
        secondTeam.selectByValue(teamValue);
    }

    public void uploadPlayerstatFile(String playerstatFilePath) {
        choosePlayerstatFileButton.sendKeys(playerstatFilePath);
    }

    public void uploadTeamstatFile(String teamstatFilePath) {
        chooseTeamstatFileButton.sendKeys(teamstatFilePath);
    }

    public void clickUploadStatButton() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadStatButton));
        uploadStatButton.click();
    }

    public void verifySuccessMessage() {
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(successesMessage));
        assertNotNull(successMsg, "Элемент с сообщением об успехе не найден");
    }

    public void uploadMatchStatistics(String firstTeam, String firstGoals, String total, String secondGoals, String secondTeam,
                                      String playerstatFileName, String teamstatFileName) {
        clickUploadButton();
        selectFirstTeam(firstTeam);
        selectFirstGoals(firstGoals);
        selectTotal(total);
        selectSecondGoals(secondGoals);
        selectSecondTeam(secondTeam);
        uploadPlayerstatFile(playerstatFileName);
        uploadTeamstatFile(teamstatFileName);
        clickUploadStatButton();
        verifySuccessMessage();
    }
}
