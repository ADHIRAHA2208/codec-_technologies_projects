package com.ems.util;

import java.sql.*;

public class DBUtil {
 public static Connection getConnection() {
  try {
   Class.forName("com.mysql.cj.jdbc.Driver");
   return DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/ems","root","chinu@123");
  } catch (Exception e) {
   e.printStackTrace();
  }
  return null;
 }
}
