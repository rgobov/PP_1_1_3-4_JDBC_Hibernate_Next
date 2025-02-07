package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static SessionFactory sessionFactory;

    static {
        try {
            // Создание конфигурации Hibernate
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydb");
            configuration.setProperty("hibernate.connection.username", "admin");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("show_sql", "true");

            // Добавьте ваши сущности
            configuration.addAnnotatedClass(User.class);

            // Создание SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Закрытие кэшированных соединений
        getSessionFactory().close();
    }
    // реализуйте настройку соеденения с БД
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/mydb?user=admin&password=root";

    private Util() {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING);
    }



}

