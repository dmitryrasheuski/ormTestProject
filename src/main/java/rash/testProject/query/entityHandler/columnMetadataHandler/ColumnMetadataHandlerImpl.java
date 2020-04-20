package rash.testProject.query.entityHandler.columnMetadataHandler;

import java.lang.reflect.AccessibleObject;

public class ColumnMetadataHandlerImpl implements ColumnMetadataHandler {
    private ColumnMetadataHandlerItem chainOfHandlers;

    public ColumnMetadataHandlerImpl(ColumnMetadataHandlerItem... handlers) {
        for (int i = 1; i < handlers.length; i++) {
            handlers[i-1].setNext( handlers[i] );
        }
        chainOfHandlers = handlers[0];
    }

    @Override
    public String getColumnName(AccessibleObject property) {
        return  chainOfHandlers.findColumnName(property);
    }

    @Override
    public String getTableName(AccessibleObject property) {
        return chainOfHandlers.findTableName(property);
    }
}
