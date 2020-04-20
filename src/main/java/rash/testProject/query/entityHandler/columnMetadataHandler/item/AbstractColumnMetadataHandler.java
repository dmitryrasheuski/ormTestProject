package rash.testProject.query.entityHandler.columnMetadataHandler.item;

import rash.testProject.query.entityHandler.columnMetadataHandler.ColumnMetadataHandlerItem;


import java.lang.reflect.AccessibleObject;

abstract class AbstractColumnMetadataHandler implements ColumnMetadataHandlerItem {
    private ColumnMetadataHandlerItem successor;

    @Override
    public String findColumnName(AccessibleObject property) {

        String columnName = null;
        if (isCorrect(property)) {
            columnName = getColumnName(property);
        } else if (successor != null){
            columnName = successor.findColumnName(property);
        }
        return columnName;
    }
    @Override
    public String findTableName(AccessibleObject property) {

        String tableName = null;
        if (isCorrect(property)) {
            tableName = getTableName(property);
        } else if (successor != null){
            tableName = successor.findTableName(property);
        }
        return tableName;
    }
    @Override
    public void setNext(ColumnMetadataHandlerItem successor) {
        this.successor = successor;
    }

    protected abstract boolean isCorrect(AccessibleObject property);
    protected abstract String getColumnName(AccessibleObject property);
    protected abstract String getTableName(AccessibleObject property);

}
