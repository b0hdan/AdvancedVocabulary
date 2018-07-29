package com.dubyniak.bohdan.db.mapper;

import com.dubyniak.bohdan.api.WordCard;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.dubyniak.bohdan.db.definition.WordCardTableDefinition.*;

@Component
public class WordCardRowMapper implements RowMapper<WordCard> {

    public WordCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WordCard(rs.getLong(ID), rs.getString(FRONT_SIDE), rs.getString(BACK_SIDE));
    }
}
