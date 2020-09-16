package com.dell.hangman.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dell.hangman.domain.repository.WordsRepository;

@Service
public class XMLFileWordsRepository implements WordsRepository {

    private static final String WORDS_TAG = "word";
    public static final String CANNOT_LOAD_LIST_OF_WORDS = "Cannot load list of words";
    public static final String WORDS_XML_PATH = "/words.xml";

    @Override
    public List<String> getWords() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(CANNOT_LOAD_LIST_OF_WORDS);
        }
        Document doc;
        try {
            doc = builder.parse(
                    XMLFileWordsRepository.class.getResourceAsStream(WORDS_XML_PATH)
            );
        } catch (Exception e) {
            throw new RuntimeException(CANNOT_LOAD_LIST_OF_WORDS);
        }

        NodeList nodeList = doc.getElementsByTagName(WORDS_TAG);
        List<String> wordList = new ArrayList<>();
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String word = node.getTextContent();
                wordList.add(word);
            }
        }
        return wordList;
    }
}
