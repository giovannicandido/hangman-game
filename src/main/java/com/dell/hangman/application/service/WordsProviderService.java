package com.dell.hangman.application.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface WordsProviderService {
    List<String> getWords();
}
