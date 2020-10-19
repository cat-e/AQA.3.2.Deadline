package ru.netology.mode;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
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
        $("[data-test-id='error-notification']").shouldBe(visible);
    }

    public void clearFields() {
        loginForm.clear();
        passwordForm.clear();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginForm.setValue(info.getLogin());
        passwordForm.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
