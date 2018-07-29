package com.dubyniak.bohdan.db;

import com.dubyniak.bohdan.api.GeneralEntity;
import com.dubyniak.bohdan.db.definition.DatabaseTableDefinition;
import com.dubyniak.bohdan.exception.NoDatabaseColumnsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DatabaseQueryBuilder<Entity extends GeneralEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseQueryBuilder.class);
    private static final String NO_COLUMNS_TO_UPDATE_MESSAGE = "There are no columns to update in the %s table.";
    private static final String NO_COLUMNS_TO_INSERT_MESSAGE = "There are no columns to insert in the %s table.";
    private String tableName;
    private String selectAllQuery;
    private String selectByIdQuery;
    private String countByIdQuery;
    private String insertQuery;
    private String updateQuery;
    private String deleteQuery;

    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<Entity> rowMapper;
    private String idColumnName;

    protected abstract DatabaseTableDefinition getTableDefinition();

    protected void initialize() {
        tableName = getTableDefinition().getTableName();
        idColumnName = getTableDefinition().getIdColumnName();
        String parameters = getParameters();
        String values = getValues();
        String pairs = getParameterValuePairs();
        selectAllQuery = String.format("select * from %s", tableName);
        selectByIdQuery = String.format("select * from %s where %s = ?", tableName, idColumnName);
        countByIdQuery = String.format("select count(*) from %s where %s = ?", tableName, idColumnName);
        insertQuery = String.format("insert into %s(%s) values(%s)", tableName, parameters, values);
        updateQuery = String.format("update %s set %s where %s = ?", tableName, pairs, idColumnName);
        deleteQuery = String.format("delete from %s where %s = ?", tableName, idColumnName);
    }

    private String getParameters() {
        if (getTableDefinition().getColumns().count() == 0) {
            LOGGER.error(String.format(NO_COLUMNS_TO_INSERT_MESSAGE, tableName));
            throw new NoDatabaseColumnsException(String.format(NO_COLUMNS_TO_INSERT_MESSAGE, tableName));
        }
        return getTableDefinition().getColumns().collect(Collectors.joining(", "));
    }

    private String getValues() {
        if (getTableDefinition().getColumns().count() == 0) {
            LOGGER.error(String.format(NO_COLUMNS_TO_INSERT_MESSAGE, tableName));
            throw new NoDatabaseColumnsException(String.format(NO_COLUMNS_TO_INSERT_MESSAGE, tableName));
        }
        return Stream.generate(() -> "?").limit(getTableDefinition().getColumns().count()).collect(Collectors.joining(", "));
    }

    private String getParameterValuePairs() {
        if (getTableDefinition().getColumns().count() == 0) {
            LOGGER.error(String.format(NO_COLUMNS_TO_UPDATE_MESSAGE, tableName));
            throw new NoDatabaseColumnsException(String.format(NO_COLUMNS_TO_UPDATE_MESSAGE, tableName));
        }
        return getTableDefinition().getColumns().map(column -> column + " = ?").collect(Collectors.joining(", "));
    }

    public List<Entity> getAll() {
        return jdbcTemplate.query(selectAllQuery, rowMapper);
    }

    public Entity get(Long id) {
        if ((jdbcTemplate.queryForObject(countByIdQuery, Integer.class, id) == 0)) {
            return null;
        }
        return jdbcTemplate.queryForObject(selectByIdQuery, rowMapper, id);
    }

    public Long create(Entity entity) {
        if (getTableDefinition().getColumns().count() == 0) {
            LOGGER.error(String.format(NO_COLUMNS_TO_INSERT_MESSAGE, tableName));
            throw new NoDatabaseColumnsException(String.format(NO_COLUMNS_TO_INSERT_MESSAGE, tableName));
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{idColumnName});
                    return prepareInsertStatementParameters(entity, ps);
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void update(Long id, Entity entity) {
        jdbcTemplate.update(updateQuery, getUpdateParameters(entity, id));
    }

    public void delete(Long id) {
        jdbcTemplate.update(deleteQuery, id);
    }

    protected abstract PreparedStatement prepareInsertStatementParameters(Entity entity, PreparedStatement ps)
            throws SQLException;

    protected abstract Object[] getUpdateParameters(Entity entity, Long id);
}
