package com.dubyniak.bohdan.db.definition;

import java.util.stream.Stream;

public interface DatabaseTableDefinition {

    String getTableName();

    String getIdColumnName();

    Stream<String> getColumns();
}
