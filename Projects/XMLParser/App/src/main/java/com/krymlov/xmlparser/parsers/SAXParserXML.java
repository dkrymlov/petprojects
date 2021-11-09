package com.krymlov.xmlparser.parsers;

import com.krymlov.xmlparser.object.Inhabitant;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//Class Sax Parser
public class SAXParserXML{

    private static List<Inhabitant> inhabitantList;

    //Constructor
    private SAXParserXML(){}

    //parse method
    //file path - path to xml, keyword - string to find
    public static List<Inhabitant> parseXML(String filePath, String keyword){
        inhabitantList = null;

        inhabitantList = new ArrayList<>();
        List<Inhabitant> listToReturn = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new XMLHandler();

            File file = new File(filePath);
            saxParser.parse(file, handler);

            for (int i = 0; i < inhabitantList.size(); i++) {
                if (inhabitantList.get(i) != null && containsKeyword(inhabitantList.get(i), keyword)){
                    listToReturn.add(inhabitantList.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listToReturn;
    }

    //Sax handler with override methods
    private static class XMLHandler extends DefaultHandler {
        boolean foundFullname = false;
        boolean foundAge = false;
        boolean foundFaculty = false;
        boolean foundCathedra = false;
        boolean foundGrade = false;
        boolean foundHomeplace = false;
        boolean foundPayment = false;
        Inhabitant inhabitant;

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {

            if (qName.equals("inhabitant")) {
                inhabitant = Inhabitant.getInstance();
            }

            if (qName.equals("fullname")) {
                foundFullname = true;
            }

            if (qName.equals("age")) {
                foundAge = true;
            }

            if (qName.equals("faculty")) {
                foundFaculty = true;
            }

            if (qName.equals("cathedra")) {
                foundCathedra = true;
            }

            if (qName.equals("grade")) {
                foundGrade = true;
            }

            if (qName.equals("homeplace")) {
                foundHomeplace = true;
            }

            if (qName.equalsIgnoreCase("payment")) {
                foundPayment = true;
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("inhabitant")) {
                inhabitantList.add(inhabitant);
                inhabitant = null;
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {

            if (foundFullname) {
                inhabitant.setFullname(new String(ch, start, length));
                foundFullname = false;
            }

            if (foundAge) {
                inhabitant.setAge(new String(ch, start, length));
                foundAge = false;
            }

            if (foundFaculty) {
                inhabitant.setFaculty(new String(ch, start, length));
                foundFaculty = false;
            }

            if (foundCathedra) {
                inhabitant.setCathedra(new String(ch, start, length));
                foundCathedra = false;
            }

            if (foundGrade) {
                inhabitant.setGrade(new String(ch, start, length));
                foundGrade = false;
            }

            if (foundHomeplace) {
                inhabitant.setHomeplace(new String(ch, start, length));
                foundHomeplace = false;
            }

            if (foundPayment) {
                inhabitant.setPayment(new String(ch, start, length));
                foundPayment = false;

            }

        }
    }

    //find keyword method
    private static boolean containsKeyword(Inhabitant inhabitant, String keyword){
        if (inhabitant.toString().contains(keyword)){
            return true;
        }else return false;
    }
}
