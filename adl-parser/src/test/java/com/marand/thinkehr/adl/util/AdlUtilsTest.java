/*
 * Copyright (C) 2014 Marand
 */

package com.marand.thinkehr.adl.util;

import com.marand.thinkehr.adl.ParserTestBase;
import org.openehr.jaxb.am.DifferentialArchetype;
import org.openehr.jaxb.am.FlatArchetype;
import org.testng.annotations.Test;

/**
 * @author markopi
 */
public class AdlUtilsTest extends ParserTestBase {
    @Test
    public void testTargetCloneEquals() {
        DifferentialArchetype differential = parseArchetype("adl15/openEHR-EHR-EVALUATION.alert.v1.adls");

        FlatArchetype targetClone = AdlUtils.createFlatArchetypeClone(differential);
        assertEquals(json(differential), json(targetClone));
    }

}
