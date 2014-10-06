/*
 * Copyright (C) 2014 Marand
 */

package com.marand.thinkehr.adl.rm;

import com.marand.thinkehr.adl.ParserTestBase;
import org.openehr.jaxb.rm.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.marand.thinkehr.adl.rm.RmObjectFactory.newIntervalOfInteger;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author markopi
 */
public class RmBeanReflectorTest extends ParserTestBase {
    @Test
    public void testListProperties() throws Exception {
        Iterable<RmBeanReflector.RmAttribute> properties = RmBeanReflector.listProperties(DvQuantity.class);
        List<String> names = new ArrayList<>();
        for (RmBeanReflector.RmAttribute property : properties) {
            names.add(property.getAttribute());
        }


        assertThat(names).containsOnly("accuracy", "accuracy_is_percent", "magnitude", "magnitude_status",
                "normal_range", "normal_status", "other_reference_ranges", "precision", "units");
    }

    @Test
    public void testRmAttributeMandatory() throws Exception {
        RmBeanReflector.RmAttribute numerator = RmBeanReflector.getRmAttribute(DvProportion.class, "numerator");

        assertRmAttribute(numerator, "numerator", "numerator", Float.TYPE, newIntervalOfInteger(1, 1));
    }

    @Test
    public void testRmAttributeOptional() throws Exception {
        RmBeanReflector.RmAttribute formatting = RmBeanReflector.getRmAttribute(DvText.class, "formatting");

        assertRmAttribute(formatting, "formatting", "formatting", String.class, newIntervalOfInteger(0, 1));
    }

    @Test
    public void testRmAttributeMulti() throws Exception {
        RmBeanReflector.RmAttribute items = RmBeanReflector.getRmAttribute(ItemList.class, "items");

        assertRmAttribute(items, "items", "items", Element.class, newIntervalOfInteger(0, null));
    }


    private void assertRmAttribute(RmBeanReflector.RmAttribute attr, String attributeName, String propertyName, Class<?> type,
            IntervalOfInteger occurrences) {
        assertThat(attr.getAttribute()).isEqualTo(attributeName);
        assertThat(attr.getProperty().getName()).isEqualTo(propertyName);
        assertThat(attr.getTargetType()).isEqualTo(type);

        assertThat(json(attr.getOccurrences())).isEqualTo(json(occurrences));
    }
}
