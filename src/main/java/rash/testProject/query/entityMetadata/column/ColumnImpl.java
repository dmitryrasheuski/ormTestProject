package rash.testProject.query.entityMetadata.column;

import rash.testProject.query.entityMetadata.EntityColumn;

import java.util.Formatter;

public class ColumnImpl implements EntityColumn {
    private String tableName;
    private String columnName;

    public ColumnImpl(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public String getFullName() {
        return new Formatter()
                .format("%s.%s", tableName, columnName)
                .toString();
    }
}
