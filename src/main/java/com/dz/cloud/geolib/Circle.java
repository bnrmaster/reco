package com.dz.cloud.geolib;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

@Data
public class Circle extends Shape {

    public Circle(Double cx, Double cy, Double r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    Double cx;
    Double cy;
    Double r;

    @Override
    public Geometry getGeometry() {
        GeometryFactory f = null;
        GeometricShapeFactory factory = new GeometricShapeFactory();
        factory.setCentre(new Coordinate(cx, cy));
        factory.setSize(r*2);
        Polygon polygon =factory.createCircle();
        return polygon;
    }
}
