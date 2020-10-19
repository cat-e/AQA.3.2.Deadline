package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "pass";
    Connection conn = null;

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
        try (val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
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

    public static String getVerificationInfo() throws SQLException {
        String verificationCode = "";
        val codesSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            val usersCode = runner.query(conn, codesSQL, new ScalarHandler<>());
            verificationCode = (String) usersCode;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verificationCode;
    }

    public static void cleanDataBase() throws SQLException {
        val cleanCards = "DELETE FROM cards";
        val cleanAuthCodes = "DELETE FROM auth_codes";
        val cleanUser = "DELETE FROM users";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            val cleanCardsUser = runner.execute(conn, cleanCards, new ScalarHandler<>());
            val cleanAuthCodesUser = runner.execute(conn, cleanAuthCodes, new ScalarHandler<>());
            val cleanUserUser = runner.execute(conn, cleanUser, new ScalarHandler<>());
        }
    }
}
