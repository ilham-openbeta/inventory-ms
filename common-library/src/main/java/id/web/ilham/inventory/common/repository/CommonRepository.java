package id.web.ilham.inventory.common.repository;

import java.util.List;

public interface CommonRepository<T, ID> {
    void save(T entity);

    Boolean removeById(ID id);

    T findById(ID id);

    List<T> findAll();
}
