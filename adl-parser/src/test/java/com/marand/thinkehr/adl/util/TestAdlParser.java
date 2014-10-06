/*
 * Copyright (C) 2014 Marand
 */

package com.marand.thinkehr.adl.util;

import com.google.common.base.Charsets;
import com.marand.thinkehr.adl.parser.AdlDeserializer;
import com.marand.thinkehr.adl.parser.BomSupportingReader;
import com.marand.thinkehr.adl.rm.OpenEhrRmModel;
import org.apache.commons.io.IOUtils;
import org.openehr.jaxb.am.DifferentialArchetype;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author markopi
 */
public class TestAdlParser {

    private static String readClasspathResource(String classpathResource) {
        try {
            InputStream in = TestAdlParser.class.getClassLoader().getResourceAsStream(classpathResource);
            try (Reader reader = new BomSupportingReader(in, Charsets.UTF_8)) {
                return IOUtils.toString(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static DifferentialArchetype parseAdl(String classpathResource) {
        String adl = readClasspathResource(classpathResource);
        return new AdlDeserializer(new OpenEhrRmModel()).parse(adl);
    }

}
