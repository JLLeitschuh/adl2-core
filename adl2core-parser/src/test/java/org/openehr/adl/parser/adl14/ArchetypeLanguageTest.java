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

package org.openehr.adl.parser.adl14;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.openehr.adl.ParserTestBase;
import org.openehr.jaxb.am.Archetype;
import org.openehr.jaxb.rm.TranslationDetails;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Testcase for Archetype language section parsing
 *
 * @author Rong Chen
 * @version 1.0
 */
public class ArchetypeLanguageTest extends ParserTestBase {

    @Test
    public void testParseLanguageSection() throws Exception {
        Archetype archetype = parseArchetype("adl14/adl-test-entry.archetype_language.v1.adl");

        assertThat(archetype).isNotNull();

        List<TranslationDetails> translations = archetype.getTranslations();
        assertThat(translations).isNotEmpty();


        TranslationDetails td = getTranslation(translations, "de");
        Map<String, String> map = dictToMap(td.getAuthor());
        assertNotNull("author null", map);
        assertEquals("author.name wrong", "Harry Potter", map.get("name"));
        assertEquals("author.email wrong", "harry@something.somewhere.co.uk",
                map.get("email"));

        assertEquals("acrreditation wrong",
                "British Medical Translator id 00400595", td.getAccreditation());

        map = dictToMap(td.getOtherDetails());
        assertEquals("review 1 wrong", "Ron Weasley", map.get("review 1"));
        assertEquals("review 2 wrong", "Rubeus Hagrid", map.get("review 2"));
    }


    @Test
    public void testParseLanguageWithoutAccreditation() throws Exception {
        Archetype archetype = parseArchetype("adl14/adl-test-entry.archetype_language_no_accreditation.v1.adl");
        assertThat(archetype).isNotNull();

        Map<String, TranslationDetails> translations = transToMap(archetype.getTranslations());
        assertThat(translations).isNotEmpty();

        TranslationDetails td = translations.get("de");
        assertNotNull("translation de missing", td);
        Map<String, String> map = dictToMap(td.getAuthor());
        assertNotNull("author null", map);
        assertEquals("author.name wrong", "Harry Potter", map.get("name"));
        assertEquals("author.email wrong", "harry@something.somewhere.co.uk",
                map.get("email"));

        assertEquals("accreditation wrong", null, td.getAccreditation());

        map = dictToMap(td.getOtherDetails());
        assertEquals("review 1 wrong", "Ron Weasley", map.get("review 1"));
        assertEquals("review 2 wrong", "Rubeus Hagrid", map.get("review 2"));
    }

    @Test
    public void testParseLanguageWithAccreditationBeforeLanguage() throws Exception {
        Archetype archetype = parseArchetype("adl14/adl-test-entry.archetype_language_order_of_translation_details.test.adl");

        Map<String, TranslationDetails> translations = transToMap(archetype
                .getTranslations());
        assertThat(translations).isNotEmpty();

        TranslationDetails td = translations.get("de");
        assertNotNull("translation de missing", td);
        Map<String, String> map = dictToMap(td.getAuthor());
        assertNotNull("author null", map);
        assertEquals("author.name wrong", "Harry Potter", map.get("name"));
        assertEquals("author.email wrong", "harry@something.somewhere.co.uk",
                map.get("email"));

        assertEquals("accreditation wrong", "Seven OWLs at Hogwards", td.getAccreditation());
        assertEquals("language wrong", "de", td.getLanguage().getCodeString());
        map = dictToMap(td.getOtherDetails());
        assertEquals("review 1 wrong", "Ron Weasley", map.get("review 1"));
        assertEquals("review 2 wrong", "Rubeus Hagrid", map.get("review 2"));
    }

    @Test
    public void testParseTranslationsLanguageAuthor() throws Exception {
        Archetype archetype = parseArchetype("adl14/adl-test-entry.translations_language_author.test.adl");

        Map<String, TranslationDetails> translations = transToMap(archetype.getTranslations());

        TranslationDetails td = translations.get("de");
        assertNotNull("translation de missing", td);
        Map<String, String> map = dictToMap(td.getAuthor());
        assertNotNull("author null", map);
    }

    @Test
    // test the reverse order of language, author in translations
    public void testParseTranslationsAuthorLanguage() throws Exception {
        Archetype archetype = parseArchetype("adl14/adl-test-entry.translations_author_language.test.adl");

        Map<String, TranslationDetails> translations = transToMap(archetype.getTranslations());

        TranslationDetails td = translations.get("de");
        assertNotNull("translation de missing", td);
        Map<String, String> map = dictToMap(td.getAuthor());
        assertNotNull("author null", map);
    }


    private TranslationDetails getTranslation(List<TranslationDetails> details, final String language) {
        return FluentIterable.from(details).firstMatch(new Predicate<TranslationDetails>() {
            @Override
            public boolean apply(TranslationDetails input) {
                return language.equals(input.getLanguage().getCodeString());
            }
        }).get();
    }
}
