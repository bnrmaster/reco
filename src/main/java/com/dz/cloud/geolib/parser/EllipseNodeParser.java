package com.dz.cloud.geolib.parser;

import com.dz.cloud.geolib.Ellipse;
import com.dz.cloud.geolib.Shape;
import org.w3c.dom.Node;

class EllipseNodeParser extends NodeParser {

    @Override
    protected Shape parse(Node node) {
        Double cx = Double.parseDouble(node.getAttributes().getNamedItem("cx").getTextContent());
        Double cy = Double.parseDouble(node.getAttributes().getNamedItem("cy").getTextContent());
        Double rx = Double.parseDouble(node.getAttributes().getNamedItem("rx").getTextContent());
        Double ry = Double.parseDouble(node.getAttributes().getNamedItem("ry").getTextContent());
        return new Ellipse(cx, cy, rx, ry);
    }

    @Override
    public String nodeName() {
        return "ellipse";
    }
}
