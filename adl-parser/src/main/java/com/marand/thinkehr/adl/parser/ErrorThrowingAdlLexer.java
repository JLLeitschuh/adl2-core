/*
 * Copyright (C) 2014 Marand
 */

package com.marand.thinkehr.adl.parser;

import com.marand.thinkehr.adl.antlr.AdlLexer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

/**
 * @author markopi
 */
public class ErrorThrowingAdlLexer extends AdlLexer {
    public ErrorThrowingAdlLexer() {
    }

    public ErrorThrowingAdlLexer(CharStream input) {
        super(input);
    }

    public ErrorThrowingAdlLexer(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        throw new RuntimeRecognitionException(e);
    }
}
