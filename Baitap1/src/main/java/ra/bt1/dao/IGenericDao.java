package ra.bt1.dao;

import ra.bt1.models.Student;

import java.util.List;

public interface IGenericDao <T, E>{
    List<T> getAll();
    T getById(E id);
    void save(T t);
    void update(T t);
    void delete(E id);
    List<Student> searchByName(String name);
}
