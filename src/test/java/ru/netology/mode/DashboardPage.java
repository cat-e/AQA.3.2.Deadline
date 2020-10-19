package ru.netology.mode;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public void dashboardPage() {
        $("[data-test-id=dashboard]").shouldBe(Condition.visible);
    }

}