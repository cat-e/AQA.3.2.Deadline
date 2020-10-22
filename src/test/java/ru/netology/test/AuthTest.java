package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.mode.LoginPage;

public class AuthTest {

    @AfterAll
    static void cleanDataBase() {
        DataHelper.cleanDataBase();
    }

    @Test
    void shouldLoginWithAuthCode() {
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        val verificationCode = DataHelper.getVerificationInfo();
        val dashboardPage = verificationPage.validVerify(DataHelper.getVerificationInfo());
        dashboardPage.dashboardPage();
    }

    @Test
    void shouldLoginWithRandomFakerUser() {
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
        val authInfo = DataHelper.getRandomAuthInfo("active");
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        val verificationCode = DataHelper.getVerificationInfo();
        val dashboardPage = verificationPage.validVerify(DataHelper.getVerificationInfo());
        dashboardPage.dashboardPage();
    }

    @Test
    void loginFourTimesWithWrongPassword() {
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
        val authInfo = DataHelper.getAuthInfoWithWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.clearFields();
        loginPage.validLogin(authInfo);
        loginPage.clearFields();
        loginPage.validLogin(authInfo);
        loginPage.clearFields();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
    }
}
