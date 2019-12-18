package com.dz.cloud.math;

import com.dz.cloud.geolib.Line;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

public class ShapeUtils {
    public static Geometry lineToPolygonGeo(Line line) {
        GeometryFactory factory = new GeometryFactory();
        Coordinate[] forward = line.getGeometry().getCoordinates();

        Coordinate[] poly = new Coordinate[2*forward.length];
        for (int i = 0; i < forward.length; i++) {
            poly[i] = forward[i];
        }

        for (int i = forward.length - 1; i >= 0; i--) {
            poly[poly.length-1 - i] = forward[i];
        }
        return factory.createLinearRing(poly);
    }
}
