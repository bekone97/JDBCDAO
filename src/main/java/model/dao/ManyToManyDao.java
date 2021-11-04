package model.dao;

public interface ManyToManyDao<T> {
    void create(T item);
    void delete (T item);
}
