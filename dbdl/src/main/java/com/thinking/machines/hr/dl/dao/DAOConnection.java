package com.thinking.machines.hr.dl.dao;
import java.sql.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DAOConnection
{
private DAOConnection()
{
}
public static Connection getConnection() throws DAOException
{
Connection c=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return c;
}
}