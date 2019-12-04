package service;

import dao.UserDao;
import domain.User;

import java.sql.SQLException;
import java.util.Collection;

public class UserService {
    private static UserDao userDao
            = UserDao.getInstance();
    private static UserService userService
            =new UserService();
    private UserService(){}
    public static UserService getInstance(){
        return userService;
    }
    public Collection<User> findAll(){
        return userDao.findAll();
    }
    public User find(Integer id)throws SQLException {
        return userDao.find(id);
    }
    public boolean update(User user)throws SQLException,ClassNotFoundException{
        return userDao.update(user);
    }
    public Collection<User> findByUserName(String username) throws SQLException {
        return userDao.findByUserName(username);
    }
    public User login(String username, String password) throws SQLException {
        return userDao.login(username,password);
    }
}
