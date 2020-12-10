package ru.netology.mode;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    public void verificationPage() {
        $("[data-test-id=code] input").shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        $("[data-test-id=code] input").setValue(verificationCode);
        $("[data-test-id='action-verify']").click();
        return new DashboardPage();
    }
}
