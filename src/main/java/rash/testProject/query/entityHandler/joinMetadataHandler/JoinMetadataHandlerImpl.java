package rash.testProject.query.entityHandler.joinMetadataHandler;

import java.lang.reflect.AccessibleObject;

public class JoinMetadataHandlerImpl implements JoinMetadataHandler {
    private JoinMetadataHandlerItem chainOfHandlers;

    public JoinMetadataHandlerImpl(JoinMetadataHandlerItem... handlers) {
        for (int i = 1; i < handlers.length; i++) {
            handlers[i-1].setNext( handlers[i] );
        }
        chainOfHandlers = handlers[0];
    }

    @Override
    public String getMainEntityTableName(AccessibleObject property) {
        return chainOfHandlers.findMainEntityTableName(property);
    }

    @Override
    public String getMainEntityTableColumnName(AccessibleObject property) {
        return chainOfHandlers.findMainEntityTableColumnName(property);
    }

    @Override
    public String getSecondaryEntityTableName(AccessibleObject property) {
        return chainOfHandlers.findSecondaryEntityTableName(property);
    }

    @Override
    public String getSecondaryEntityTableColumnName(AccessibleObject property) {
        return chainOfHandlers.findSecondaryEntityTableColumnName(property);
    }
}
