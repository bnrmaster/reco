package com.dz.cloud.geolib.parser;

import com.dz.cloud.geolib.Rect;
import com.dz.cloud.geolib.Shape;
import org.w3c.dom.Node;

class RectNodeParser extends NodeParser {

    @Override
    protected Shape parse(Node node) {
        Double x = Double.parseDouble(node.getAttributes().getNamedItem("x").getTextContent());
        Double y = Double.parseDouble(node.getAttributes().getNamedItem("y").getTextContent());
        Double width = Double.parseDouble(node.getAttributes().getNamedItem("width").getTextContent());
        Double height = Double.parseDouble(node.getAttributes().getNamedItem("height").getTextContent());
        return new Rect(x, y, width, height);
    }

    @Override
    public String nodeName() {
        return "rect";
    }
}
