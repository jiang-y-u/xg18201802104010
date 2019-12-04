package dao;

import domain.Teacher;
import domain.User;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class UserDao {
    private static UserDao userDao=
            new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return userDao;
    }

    //返回结果集对象
    public Collection<User> findAll(){
        Collection<User> users = new TreeSet<User>();
        try{
            //获得数据库连接对象
            Connection connection = JdbcHelper.getConn();
            //在该连接上创建语句盒子对象
            Statement stmt = connection.createStatement();
            //执行SQL查询语句并获得结果集对象
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM user");
            //若结果存在下一条，执行循环体
            while (resultSet.next()) {
                //打印结果集中记录的id字段
                System.out.print(resultSet.getInt("id"));
                System.out.print(",");
                System.out.print(resultSet.getInt("username"));
                System.out.print(",");
                System.out.print(resultSet.getInt("password"));
                System.out.print(",");
                //打印结果集中记录的no字段
                System.out.print(resultSet.getString("teacher_id"));
                //根据数据库中的数据,创建User类型的对象
                User user = new User(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
                //添加到集合degrees中
                users.add(user);
            }
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
    public User find(Integer id) throws SQLException{
        //声明一个User类型的变量
        User user = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String User_sql = "SELECT * FROM User WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(User_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,username,password,teacher_id值为参数，创建user对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));;
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }
    public Set<User> findByUserName(String username )throws SQLException{
        //创建一个集合
        Set<User> users = new HashSet<User>();
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String findUser_sql = "SELECT * FROM User WHERE username=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,username);
        //执行预编译语句，并获得结果集对象
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果存在下一条语句，执行循环体
        while(resultSet.next()){
            //创建一个School类型的school对象
            Teacher teacher = TeacherDao.getInstance().find(resultSet.getInt("teacher_id"));
            User user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),teacher);
            users.add(user);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        //返回
        return users;
    }
    public boolean update(User user) throws  SQLException {
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateUser_sql = " update user set username=?,password=?,teacher_id=? where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setInt(3,user.getTeacher().getId());
        preparedStatement.setInt(4,user.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
    public User login(String username, String password)throws SQLException{
        //声明一个User类型的的user变量
        User user = null;
        Connection connection = JdbcHelper.getConn();
        String findByUsername_sql = "select * from user where username = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(findByUsername_sql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        //执行预编译语句，并把结果赋值给result这个结果集
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            user = new User(result.getInt("id"),
                    result.getString("username"),
                    result.getString("password"),
                    TeacherDao.getInstance().find(result.getInt("teacher_id")));
        }
        JdbcHelper.close(result,preparedStatement,connection);
        return user;
    }

}
