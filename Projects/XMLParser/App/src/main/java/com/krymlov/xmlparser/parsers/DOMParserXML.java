package com.krymlov.xmlparser.parsers;

import com.krymlov.xmlparser.object.Inhabitant;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//class DOM parser
public class DOMParserXML{

    //constructor
    private DOMParserXML(){}

    //static method to get List of Entities
    //file path - path to xml file, keyword - string to find in xml
    public static List<Inhabitant> parseXML(String filePath, String keyword){
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        NodeList nodeList;
        List<Inhabitant> inhabitantList = new ArrayList<>();
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File(filePath));
            nodeList = document.getElementsByTagName("inhabitant");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    if (findByKeyword(element, keyword) != null){
                        inhabitantList.add(findByKeyword(element, keyword));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return inhabitantList;
    }

    //find method
    private static Inhabitant findByKeyword(Element element, String keyword){
        if (element.getElementsByTagName("fullname").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else if (element.getElementsByTagName("age").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else if (element.getElementsByTagName("faculty").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else if (element.getElementsByTagName("cathedra").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else if (element.getElementsByTagName("grade").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else if (element.getElementsByTagName("homeplace").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else if (element.getElementsByTagName("payment").item(0).getTextContent().contains(keyword)){
            return setInhabitant(element);
        }else return null;
    }

    //method to set inhabitant values
    private static Inhabitant setInhabitant(Element element){
        return Inhabitant.getInstance(element.getElementsByTagName("fullname").item(0).getTextContent(),
                element.getElementsByTagName("age").item(0).getTextContent(),
                element.getElementsByTagName("faculty").item(0).getTextContent(),
                element.getElementsByTagName("cathedra").item(0).getTextContent(),
                element.getElementsByTagName("grade").item(0).getTextContent(),
                element.getElementsByTagName("homeplace").item(0).getTextContent(),
                element.getElementsByTagName("payment").item(0).getTextContent());
    }

}
