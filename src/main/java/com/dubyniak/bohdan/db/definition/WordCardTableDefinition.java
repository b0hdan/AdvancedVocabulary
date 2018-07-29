package com.dubyniak.bohdan.db.definition;

import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class WordCardTableDefinition implements DatabaseTableDefinition {
    private static final String TABLE_NAME = "word_card";
    public static final String ID = "id";
    public static final String FRONT_SIDE = "front_side";
    public static final String BACK_SIDE = "back_side";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID;
    }

    @Override
    public Stream<String> getColumns() {
        return Stream.of(FRONT_SIDE, BACK_SIDE);
    }
}
