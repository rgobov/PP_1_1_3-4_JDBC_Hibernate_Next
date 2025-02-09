package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    public static final UserService userService = new UserServiceImpl();

    public static String generateNames() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            User user = new User();
            user.setName(generateNames());
            user.setLastName(generateNames());
            user.setAge((byte) (Math.random() * 100));
            users.add(user);
        }
        return users;
    }

    public static void main(String[] args) {
        userService.createUsersTable();
        for (User user : generateUsers()) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
