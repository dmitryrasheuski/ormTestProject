package rash.testProject.query.entityHandler.joinMetadataHandler.item;

import rash.testProject.query.entityHandler.joinMetadataHandler.JoinMetadataHandlerItem;

import java.lang.reflect.AccessibleObject;

abstract class AbstractJoinMetadataHandlerItem implements JoinMetadataHandlerItem {
    private JoinMetadataHandlerItem successor;

    @Override
    public void setNext(JoinMetadataHandlerItem successor) {
        this.successor = successor;
    }

    @Override
    public String findMainEntityTableName(AccessibleObject property) {
        String mainEntityTableName = null;
        if ( isCorrect(property) ) {
            mainEntityTableName = getMainEntityTableName(property);
        } else if (successor != null) {
            mainEntityTableName = successor.findMainEntityTableName(property);
        }
        return mainEntityTableName;
    }

    @Override
    public String findMainEntityTableColumnName(AccessibleObject property) {
        String mainEntityTableColumnName = null;
        if ( isCorrect(property) ) {
            mainEntityTableColumnName = getMainEntityTableColumnName(property);
        } else if (successor != null) {
            mainEntityTableColumnName = successor.findMainEntityTableColumnName(property);
        }
        return mainEntityTableColumnName;
    }

    @Override
    public String findSecondaryEntityTableName(AccessibleObject property) {
        String secondaryEntityTableName = null;
        if ( isCorrect(property) ) {
            secondaryEntityTableName = getSecondaryEntityTableName(property);
        } else if (successor != null) {
            secondaryEntityTableName = successor.findSecondaryEntityTableName(property);
        }
        return secondaryEntityTableName;
    }

    @Override
    public String findSecondaryEntityTableColumnName(AccessibleObject property) {
        String secondaryEntityTableColumnName = null;
        if ( isCorrect(property) ) {
            secondaryEntityTableColumnName = getSecondaryEntityTableColumnName(property);
        } else if (successor != null) {
            secondaryEntityTableColumnName = successor.findSecondaryEntityTableColumnName(property);
        }
        return secondaryEntityTableColumnName;
    }

    protected abstract boolean isCorrect(AccessibleObject property);
    protected abstract String getMainEntityTableName(AccessibleObject properties);
    protected abstract String getMainEntityTableColumnName(AccessibleObject properties);
    protected abstract String getSecondaryEntityTableName(AccessibleObject properties);
    protected abstract String getSecondaryEntityTableColumnName(AccessibleObject properties);


}
