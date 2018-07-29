package com.dubyniak.bohdan.db.dao.impl;

import com.dubyniak.bohdan.api.WordCard;
import com.dubyniak.bohdan.db.DatabaseQueryBuilder;
import com.dubyniak.bohdan.db.dao.WordCardDAO;
import com.dubyniak.bohdan.db.definition.DatabaseTableDefinition;
import com.dubyniak.bohdan.db.definition.WordCardTableDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class WordCardDAOImpl extends DatabaseQueryBuilder<WordCard> implements WordCardDAO {
    private final WordCardTableDefinition wordCardTableDefinition;

    @Autowired
    public WordCardDAOImpl(WordCardTableDefinition wordCardTableDefinition, RowMapper<WordCard> rowMapper,
                           DataSource dataSource) {
        this.wordCardTableDefinition = wordCardTableDefinition;
        this.rowMapper = rowMapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
        super.initialize();
    }

    @Override
    protected DatabaseTableDefinition getTableDefinition() {
        return wordCardTableDefinition;
    }

    @Override
    protected PreparedStatement prepareInsertStatementParameters(WordCard wordCard, PreparedStatement ps)
            throws SQLException {
        ps.setString(1, wordCard.getFrontSide());
        ps.setString(2, wordCard.getBackSide());
        return ps;
    }

    protected Object[] getUpdateParameters(WordCard wordCard, Long id) {
        return new Object[]{wordCard.getFrontSide(), wordCard.getBackSide(), id};
    }
}
