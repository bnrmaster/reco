package com.dz.cloud.geolib.parser;

import com.dz.cloud.geolib.Circle;
import com.dz.cloud.geolib.Shape;
import org.w3c.dom.Node;

class CircleNodeParser extends NodeParser {

    @Override
    protected Shape parse(Node node) {
        Double cx = Double.parseDouble(node.getAttributes().getNamedItem("cx").getTextContent());
        Double cy = Double.parseDouble(node.getAttributes().getNamedItem("cy").getTextContent());
        Double r = Double.parseDouble(node.getAttributes().getNamedItem("rx").getTextContent());
        return new Circle(cx, cy, r);
    }

    @Override
    public String nodeName() {
        return "circle";
    }
}
