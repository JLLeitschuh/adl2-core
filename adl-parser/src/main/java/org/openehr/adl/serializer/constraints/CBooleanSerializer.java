/*
 * ADL2-core
 * Copyright (c) 2013-2014 Marand d.o.o. (www.marand.com)
 *
 * This file is part of ADL2-core.
 *
 * ADL2-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openehr.adl.serializer.constraints;

import com.google.common.base.Joiner;
import org.openehr.adl.am.mixin.AmMixins;
import org.openehr.adl.serializer.ArchetypeSerializer;
import org.openehr.jaxb.am.CBoolean;
import org.openehr.jaxb.am.CDuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko Pipan
 */
public class CBooleanSerializer extends ConstraintSerializer<CBoolean> {
    public CBooleanSerializer(ArchetypeSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(CBoolean cobj) {
        boolean constrained=false;

        List<String> valids = new ArrayList<>();
        if (cobj.isTrueValid()) {
            valids.add("True");
        }
        if (cobj.isFalseValid()) {
            valids.add("False");
        }


        if (!valids.isEmpty()) {
            builder.append(Joiner.on(", ").join(valids));
            constrained=true;
        }
        if (!constrained) {
            builder.append("*");
        }


    }
}
