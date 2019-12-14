package com.dz.cloud.geolib;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.util.GeometricShapeFactory;

@Data
public class Line extends Shape {
    public Line(Double x1, Double y1, Double x2, Double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    Double x1;
    Double y1;
    Double x2;
    Double y2;

    @Override
    public Geometry getGeometry() {
        GeometryFactory factory = new GeometryFactory();
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(x1, y1),
                new Coordinate(x2, y2)
        };
        Geometry line = factory.createLineString(coordinates);
        return line;
    }
}
