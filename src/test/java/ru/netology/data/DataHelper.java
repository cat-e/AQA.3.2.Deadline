package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoWithWrongPassword() {
        return new AuthInfo("vasya", "123qwerty");
    }

    public static AuthInfo getRandomAuthInfo(String status) throws SQLException {
        val faker = new Faker();
        String login = faker.name().firstName();
        val runner = new QueryRunner();
        val countSQL = "SELECT COUNT(*) FROM users;";
        val dataSQL = "INSERT INTO users (id, login, password, status) VALUES (?, ?, ?, ?);";
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app",
                "app", "pass")) {
            long count = runner.query(conn, countSQL, new ScalarHandler<>());
            runner.update(conn, dataSQL, count,
                    login, "$2a$10$Pml3uwcimo7D/XZVwf2OaOWML5yYu5c.ziYKTwG36kAhzdmJRCYN2", status);
        }
        return new AuthInfo(login, "qwerty123");
    }

    @Value
    public static class VerificationInfo {
        private String code;
    }

    public static VerificationInfo getVerificationInfo() throws SQLException {
        String verificationCode = "";
        val codesSQL = "SELECT * FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://192.168.5.108:3306/app",
                "app", "pass")) {
            val usersCode = runner.query(conn, codesSQL, new BeanHandler<>(User.class));
            verificationCode = usersCode.getCode();
        }
        return new VerificationInfo(verificationCode);
    }
}
