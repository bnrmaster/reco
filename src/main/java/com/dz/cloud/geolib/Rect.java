package com.dz.cloud.geolib;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

@Data
public class Rect extends Shape {

    public Rect(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    Double x;
    Double y;
    Double width;
    Double height;

    @Override
    public Polygon getPolygon() {
        GeometricShapeFactory factory = new GeometricShapeFactory();
        factory.setCentre(new Coordinate(x + width / 2, y + height / 2));
        factory.setWidth(width);
        factory.setHeight(height);
        Polygon polygon =factory.createRectangle();
        return polygon;
    }
}