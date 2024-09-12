package ra.bt1.dao.student;

import ra.bt1.models.Student;
import ra.bt1.utils.ConnectionDB;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements IStudentDao{
    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        Connection connection = ConnectionDB.getConnection();
        CallableStatement callst = null;
        try{
            callst = connection.prepareCall("select * from student");
            ResultSet rs = callst.executeQuery();
            while(rs.next()){
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("status")
                );
                        students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return students;
    }

    @Override
    public Student getById(Integer id) {
        Connection connection = ConnectionDB.getConnection();
        CallableStatement callst = null;
        try{
            callst = connection.prepareCall("select * from student where id = ?");
            callst.setInt(1, id);
            ResultSet rs = callst.executeQuery();
            if(rs.next()){
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("status")
                );
                return student;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return null;
    }

    @Override
    public void save(Student student) {
        Connection connection = ConnectionDB.getConnection();
        CallableStatement callst = null;
        try{
            callst = connection.prepareCall("insert into student (fullName, email, address, phone, status) values (?, ?, ?, ?, ?)");
            callst.setString(1, student.getFullName());
            callst.setString(2, student.getEmail());
            callst.setString(3, student.getAddress());
            callst.setString(4, student.getPhone());
            callst.setBoolean(5, student.isStatus());
            callst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public void update(Student student) {
        Connection connection = ConnectionDB.getConnection();
        CallableStatement callst = null;
        try{
            callst = connection.prepareCall("update student set fullName=?, email=?, address=?, phone=?, status=? where id=?");
            callst.setString(1, student.getFullName());
            callst.setString(2, student.getEmail());
            callst.setString(3, student.getAddress());
            callst.setString(4, student.getPhone());
            callst.setBoolean(5, student.isStatus());
            callst.setInt(6, student.getId());
            callst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = ConnectionDB.getConnection();
        CallableStatement callst = null;
        try{
            callst = connection.prepareCall("delete from student where id=?");
            callst.setInt(1, id);
            callst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public List<Student> searchByName(String name) {
        List<Student> students = new ArrayList<>();
        Connection connection = ConnectionDB.getConnection();
        CallableStatement callst = null;
        try {

            callst = connection.prepareCall("select * from student where fullName like ?");
            callst.setString(1, "%" + name + "%");
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("status")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return students;
    }

}
