package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class Main {
    public static void createUsersTable() {
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            // Начинаем транзакцию
            Transaction transaction = session.beginTransaction();

            try {
                // Выполняем SQL-запрос
                session.createSQLQuery("""
                    CREATE TABLE IF NOT EXISTS users
                    (
                        id        INT PRIMARY KEY AUTO_INCREMENT,
                        name      VARCHAR(45) NOT NULL,
                        lastname  VARCHAR(45) NOT NULL,
                        age       TINYINT     NOT NULL
                    )
                    """).executeUpdate();

                // Фиксируем транзакцию
                transaction.commit();
                System.out.println("Пользовательская таблица создана");
            } catch (Exception e) {
                // Откатываем транзакцию в случае ошибки
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace(); // Логируем ошибку
            }
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку при открытии сессии
        }
    }
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        createUsersTable();

    }
}
