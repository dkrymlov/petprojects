package com.krymlov.xmlparser.transformer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.nio.file.Path;
import java.nio.file.Paths;

//Class to transform XML to HTML
public class XSLTransformer {

    //Constructor
    private XSLTransformer(){}

    //Transform method
    //pathToXslFile, pathToXmlFile, pathToHtmlFile
    public static void transform(String pathToXslFile, String pathToXmlFile, String pathToHtmlFile){
        StreamSource xslStreamSourse = new StreamSource(
                Paths.get(pathToXslFile).toAbsolutePath().toFile());
        StreamSource xmlStreamSourse = new StreamSource(
                Paths.get(pathToXmlFile).toAbsolutePath().toFile());
        Path pathToHtml = Paths.get(pathToHtmlFile);

        StreamResult result = new StreamResult(pathToHtml.toFile());

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance("org.apache.xalan.processor.TransformerFactoryImpl", null);
        try {
            Transformer transformer = transformerFactory.newTransformer(xslStreamSourse);
            transformer.transform(xmlStreamSourse, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
