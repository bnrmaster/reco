package com.dz.cloud.geolib.parser;

import com.dz.cloud.geolib.Shape;
import com.dz.cloud.model.VectorDrawing;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class SVGParser {
    private static List<NodeParser> parsers;

    static {
        parsers = new ArrayList<>();
        parsers.add(new RectNodeParser());
        parsers.add(new LineNodeParser());
        parsers.add(new CircleNodeParser());
        parsers.add( new EllipseNodeParser());
    }


    public static VectorDrawing parse(Document doc) {
        List<List<Shape>> packer = new ArrayList<>();
        packer.add(parseNodes(doc,"rect"));
        packer.add(parseNodes(doc,"line"));
        packer.add(parseNodes(doc,"circle"));
        packer.add(parseNodes(doc,"ellipse"));
        VectorDrawing vtDraw = new VectorDrawing(packer);
        vtDraw.setFilePath(doc.getDocumentURI());
        return vtDraw;
    }

    private static List<Shape> parseNodes(Document doc, String nodeName) {
        NodeParser parser = parsers.stream().filter(e->e.nodeName().equals(nodeName)).findFirst().get();
        if (parser == null) {
            throw new RuntimeException("node parser '"+nodeName+"' not found!");
        }
        List<Shape> results = new ArrayList<>();
        NodeList nodList = doc.getElementsByTagName(nodeName);
        for (Integer i = 0; i < nodList.getLength(); i++) {
            try {
                Node node = nodList.item(i);
                results.add(parser.parseNode(node));
            } catch(Exception ex){
                continue;
            }
        }
        return results;
    }
}