package com.dz.cloud.utlis;

import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.VectorDrawing;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class VtDrawUtils {
    static public VectorDrawing getVectorDrawing(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document d = null;
        try {
            d = builder.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VectorDrawing vectorDrawing = SVGParser.parse(d);
        return vectorDrawing;
    }
}
