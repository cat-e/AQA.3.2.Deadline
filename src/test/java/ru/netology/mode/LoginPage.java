package ru.netology.mode;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    private SelenideElement loginForm = $("[data-test-id=login] input");
    private SelenideElement passwordForm = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public void openLoginPage() {
        open("http://localhost:9999");
    }

    public void errorNotificationCreate() {
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    public void clearFields() {
        loginForm.doubleClick().sendKeys(Keys.BACK_SPACE);
        passwordForm.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginForm.setValue(info.getLogin());
        passwordForm.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
