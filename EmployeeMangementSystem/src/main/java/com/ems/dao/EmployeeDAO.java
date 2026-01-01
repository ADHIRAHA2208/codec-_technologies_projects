package com.ems.dao;

import java.sql.*;
import java.util.*;
import com.ems.*;
import com.ems.model.Employee;
import com.ems.util.DBUtil;

public class EmployeeDAO {

 public void save(Employee e) throws Exception {
  Connection con = DBUtil.getConnection();
  PreparedStatement ps =
   con.prepareStatement("insert into employee values(0,?,?,?,?)");
  ps.setString(1, e.getName());
  ps.setString(2, e.getEmail());
  ps.setString(3, e.getRole());
  ps.setDouble(4, e.getSalary());
  ps.executeUpdate();
 }

 public List<Employee> getAll() throws Exception {
  List<Employee> list = new ArrayList<>();
  Connection con = DBUtil.getConnection();
  ResultSet rs = con.createStatement()
    .executeQuery("select * from employee");
  while(rs.next()) {
   Employee e = new Employee();
   e.setId(rs.getInt(1));
   e.setName(rs.getString(2));
   e.setEmail(rs.getString(3));
   e.setRole(rs.getString(4));
   e.setSalary(rs.getDouble(5));
   list.add(e);
  }
  return list;
 }

 public void delete(int id) throws Exception {
  Connection con = DBUtil.getConnection();
  PreparedStatement ps =
   con.prepareStatement("delete from employee where id=?");
  ps.setInt(1, id);
  ps.executeUpdate();
 }

 public Employee getById(int id) throws Exception {
  Connection con = DBUtil.getConnection();
  PreparedStatement ps =
   con.prepareStatement("select * from employee where id=?");
  ps.setInt(1, id);
  ResultSet rs = ps.executeQuery();
  if(rs.next()) {
   Employee e = new Employee();
   e.setId(rs.getInt(1));
   e.setName(rs.getString(2));
   e.setEmail(rs.getString(3));
   e.setRole(rs.getString(4));
   e.setSalary(rs.getDouble(5));
   return e;
  }
  return null;
 }
}
