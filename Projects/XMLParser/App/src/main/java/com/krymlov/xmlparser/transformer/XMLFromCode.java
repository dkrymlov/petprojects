package com.krymlov.xmlparser.transformer;

import com.krymlov.xmlparser.object.Inhabitant;

import java.io.*;
import java.util.List;

//Class Parse Java array of entities to XML database by template
public class XMLFromCode {

    //Constructor
    private XMLFromCode(){}

    //method to create xml file
    //resourcesPath - path to empty xml file, list - list of entities
    public static void create(String resourcesPath, List<Inhabitant> list){

        File file = new File(resourcesPath);

        StringBuilder stringBuilderXml = new StringBuilder();

        stringBuilderXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        stringBuilderXml.append("<?xml-stylesheet type=\"\" ?>");
        stringBuilderXml.append("<inhabitants type=\"root\">");

        for (int i = 0; i < list.size(); i++){
            stringBuilderXml.append("<inhabitant id=\"" + i + "\">");
            stringBuilderXml.append("<fullname>" + list.get(i).getFullname() + "</fullname>");
            stringBuilderXml.append("<age>" + list.get(i).getAge() + "</age>");
            stringBuilderXml.append("<faculty>" + list.get(i).getFaculty() + "</faculty>");
            stringBuilderXml.append("<cathedra>" + list.get(i).getCathedra() + "</cathedra>");
            stringBuilderXml.append("<grade>" + list.get(i).getGrade() + "</grade>");
            stringBuilderXml.append("<homeplace>" + list.get(i).getHomeplace() + "</homeplace>");
            stringBuilderXml.append("<payment>" + list.get(i).getPayment() + "</payment>");
            stringBuilderXml.append("</inhabitant>");
        }

        stringBuilderXml.append("</inhabitants>");
        System.out.println(stringBuilderXml.toString());

        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.toString()), "UTF-8"));
            writer.write(stringBuilderXml.toString());
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
