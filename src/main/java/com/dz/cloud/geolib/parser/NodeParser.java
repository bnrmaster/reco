package com.dz.cloud.geolib.parser;

import com.dz.cloud.geolib.Shape;
import org.w3c.dom.Node;

abstract class NodeParser {
    public Shape parseNode(Node node) {
        // 个性化建造
        Shape shape = this.parse(node);
        // 提取一些共有属性
        Node displayNode = node.getAttributes().getNamedItem("display");
        if (displayNode != null) {
            if (displayNode.getTextContent().equals("none")) {
                shape.setVisible(false);
            }
        }
        return shape;
    }
    abstract protected Shape parse(Node node);
    abstract public String nodeName();
}

