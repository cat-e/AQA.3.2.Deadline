package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.mode.DashboardPage;
import ru.netology.mode.LoginPage;

import java.sql.SQLException;

public class AuthTest {

    @AfterAll
    static void cleanDataBase() throws SQLException {
        val dashboardPage = new DashboardPage();
        dashboardPage.cleanDataBase();
    }

    @Test
    void shouldLoginWithAuthCode() throws SQLException{
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        val verificationCode = DataHelper.getVerificationInfo();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.dashboardPage();
    }

    @Test
    void shouldLoginWithRandomFakerUser() throws SQLException {
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
        val authInfo = DataHelper.getRandomAuthInfo("active");
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        val verificationCode = DataHelper.getVerificationInfo();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.dashboardPage();
    }

    @Test
    void loginFourTimesWithWrongPassword() {
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
        val authInfo = DataHelper.getAuthInfoWithWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
    }
}
