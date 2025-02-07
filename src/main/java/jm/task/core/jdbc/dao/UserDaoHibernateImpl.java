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
                transaction.commit();
                System.out.println("Пользовательская таблица создана");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            // Начинаем транзакцию
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("""
                        DROP TABLE IF EXISTS users
                        """).executeUpdate();
                transaction.commit();
                System.out.println("Пользовательская таблица удалена");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                transaction.commit();
                System.out.println("Пользователь сохранен");
            } catch (Exception e) {
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
                transaction.commit();
                System.out.println("Пользователь удалён");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sf.openSession()) {

            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            Transaction transaction = session.beginTransaction();
            List<User> userList = session.createQuery(criteriaQuery).getResultList();
            try {
                transaction.commit();
                System.out.println("Список возвращен");
                return userList;
            } catch (HibernateException e) {
                transaction.rollback();
                e.printStackTrace();

            }
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery("TRUNCATE TABLE mydb.users;").executeUpdate();
                transaction.commit();
                System.out.println("Таблица очищена");
            } catch (HibernateException e) {
                transaction.rollback();
                e.printStackTrace();

            }
        }
    }
}
