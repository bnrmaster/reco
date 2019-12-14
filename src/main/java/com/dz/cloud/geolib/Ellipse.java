package com.dz.cloud.geolib;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

@Data
public class Ellipse extends Shape {

    public Ellipse(Double cx, Double cy, Double rx, Double ry) {
        this.cx = cx;
        this.cy = cy;
        this.rx = rx;
        this.ry = ry;
    }

    Double cx;
    Double cy;
    Double rx;
    Double ry;

    @Override
    public Polygon getPolygon() {
        GeometricShapeFactory factory = new GeometricShapeFactory();
        factory.setCentre(new Coordinate(cx, cy));
        factory.setWidth(rx * 2);
        factory.setHeight(ry * 2);
        Polygon polygon =factory.createCircle();
        return polygon;
    }
}
