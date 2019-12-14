package com.dz.cloud.geolib.parser;

import com.dz.cloud.geolib.Line;
import com.dz.cloud.geolib.Shape;
import org.w3c.dom.Node;

class LineNodeParser extends NodeParser {

    @Override
    protected Shape parse(Node node) {
        Double x1 = Double.parseDouble(node.getAttributes().getNamedItem("x1").getTextContent());
        Double y1 = Double.parseDouble(node.getAttributes().getNamedItem("y1").getTextContent());
        Double x2 = Double.parseDouble(node.getAttributes().getNamedItem("x2").getTextContent());
        Double y2 = Double.parseDouble(node.getAttributes().getNamedItem("y2").getTextContent());
        return new Line(x1, y1, x2, y2);
    }

    @Override
    public String nodeName() {
        return "line";
    }
}
