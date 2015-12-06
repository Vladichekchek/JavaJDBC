package sample;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


import java.sql.*;

    /**
     * Created by Kingv_000 on 16.11.2015.
     */
public class DataBase {
    private MysqlDataSource mysqlDataSource;
    public DataBase()
    {
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setDatabaseName("javajdbc");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setPort(3306);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setServerName("localhost");

    }

    public Connection getConnection() throws Exception{
        Connection connection = mysqlDataSource.getConnection();
        return connection;
    }

    public ResultSet select(int id)
    {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement("Select * from employee where id = ?");
            preparedStatement.setInt(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void addEmployee(String nameEmployee, String department)
    {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO employee (id, name, department) VALUES (NULL ,?,?)");
            preparedStatement.setString(1, nameEmployee);
            preparedStatement.setString(2, department);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void deleteEmployee(String name)
    {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE name = ?");
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
