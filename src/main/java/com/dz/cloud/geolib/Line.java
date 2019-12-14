package com.dz.cloud.geolib;

import lombok.Data;
import org.locationtech.jts.geom.Polygon;

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
    public Polygon getPolygon() {
        return null;
    }
}
