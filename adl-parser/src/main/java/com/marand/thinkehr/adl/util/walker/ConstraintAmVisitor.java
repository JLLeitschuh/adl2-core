/*
 * Copyright (C) 2014 Marand
 */

package com.marand.thinkehr.adl.util.walker;

import org.openehr.am.AmObject;
import com.marand.thinkehr.adl.rm.RmPath;
import org.openehr.jaxb.am.CAttribute;
import org.openehr.jaxb.am.CObject;

/**
 * @author markopi
 */

public class ConstraintAmVisitor<C extends AmConstraintContext> extends DispatchingAmVisitor<C> {

    public ConstraintAmVisitor() {
        add(AmObject.class, new CObjectAmVisitor());
    }


    private class CObjectAmVisitor extends AbstractAmVisitor<AmObject, C> {
        @Override
        public ArchetypeWalker.Action<? extends AmObject> preorderVisit(AmObject item, C context) {
            if (!context.getAmParents().isEmpty()) {
                if (item instanceof CObject) {
                    CObject cobj = (CObject) item;
                    final RmPath path = context.getRmPath().constrain(cobj.getNodeId());
                    context.getParentRmPaths().addLast(context.getRmPath());
                    context.setRmPath(path);
                } else if (item instanceof CAttribute) {
                    CAttribute attribute = (CAttribute) item;
                    final RmPath path;
                    if (attribute.getDifferentialPath() != null) {
                        path = context.getRmPath().resolve(attribute.getDifferentialPath());
                    } else {
                        path = context.getRmPath().resolve(attribute.getRmAttributeName(), null);
                    }
                    context.getParentRmPaths().addLast(context.getRmPath());
                    context.setRmPath(path);
                }
            }
            return super.preorderVisit(item, context);
        }

        @Override
        public ArchetypeWalker.Action<? extends AmObject> postorderVisit(AmObject item, C context,
                ArchetypeWalker.Action<? extends AmObject> action) {
            if (!context.getAmParents().isEmpty()) {
                if (item instanceof CObject || item instanceof CAttribute) {
                    context.setRmPath(context.getParentRmPaths().pollLast());
                }
            }
            return super.postorderVisit(item, context, action);
        }
    }
}
