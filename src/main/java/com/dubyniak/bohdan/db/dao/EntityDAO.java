package com.dubyniak.bohdan.db.dao;

import java.util.List;

public interface EntityDAO<Entity> {

    List<Entity> getAll();

    Entity get(Long id);

    Long create(Entity entity);

    void update(Long id, Entity entity);

    void delete(Long id);


}
