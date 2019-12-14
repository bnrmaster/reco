package com.dz.cloud.model;


import com.dz.cloud.comon.Common;
import com.dz.cloud.geolib.Circle;
import com.dz.cloud.geolib.Ellipse;
import com.dz.cloud.geolib.Line;
import com.dz.cloud.geolib.Rect;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SVG {
    private List<Line> lines = new ArrayList<>();
    private List<Rect> rects = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();
    private List<Ellipse> ellipses = new ArrayList<>();


    public SVG(List<List> packer) {
        for (Integer i = 0; i < packer.size(); i++) {
            List shapeList = packer.get(i);
            if (shapeList.size() == 0) {
                continue;
            }
            Class clazz = Common.getInnerClass(shapeList);
            if (clazz.equals(Line.class)) {
                this.lines = shapeList;
            }
            if (clazz.equals(Rect.class)) {
                this.rects = shapeList;
            }
            if (clazz.equals(Circle.class)) {
                this.circles = shapeList;
            }
            if (clazz.equals(Ellipse.class)) {
                this.ellipses = shapeList;
            }
        }
    }
}

