package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sf = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

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

    @Override
    public void dropUsersTable() {

        try (Session session = sf.openSession()) {
            // Начинаем транзакцию
            Transaction transaction = session.beginTransaction();

            try {
                // Выполняем SQL-запрос
                session.createSQLQuery("""
                DROP TABLE IF EXISTS users
                """).executeUpdate();

                // Фиксируем транзакцию
                transaction.commit();
                System.out.println("Пользовательская таблица удалена");
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

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
