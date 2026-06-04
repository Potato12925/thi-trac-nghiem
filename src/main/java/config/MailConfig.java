package config;

import io.github.cdimascio.dotenv.Dotenv;

public class MailConfig {

    private static final Dotenv dotenv = Dotenv.load();

    public static final String USERNAME = dotenv.get("MAIL_USERNAME");
    public static final String APP_PASSWORD = dotenv.get("MAIL_APP_PASSWORD");
}