package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sf;
    {
        sf = Util.getSessionFactory();

    }

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
        try (Session session = sf.openSession()) {
            // Начинаем транзакцию
            Transaction transaction = session.beginTransaction();

            try {
                session.save(new User(name, lastName, age));

                // Фиксируем транзакцию
                transaction.commit();
                System.out.println("Пользователь сохранен");
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
    public void removeUserById(long id) {
        try (Session session = sf.openSession()) {
            // Начинаем транзакцию
            Transaction transaction = session.beginTransaction();

            try {
                session.delete(session.get(User.class, id));

                // Фиксируем транзакцию
                transaction.commit();
                System.out.println("Пользователь удалён");
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
    public List<User> getAllUsers() {
       try (Session session = sf.openSession()){

        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery(criteriaQuery).getResultList();
        try {
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();

        }
        return userList;
    }}

    @Override
    public void cleanUsersTable() {

    }
}
