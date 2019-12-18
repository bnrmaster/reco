package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.geolib.Line;
import com.dz.cloud.model.VectorDrawing;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.math.Vector2D;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
    Sum of right angle
 */
public class RightAnglePercentFeature extends VtFeature {

    public RightAnglePercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "直角数量比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        BigDecimal totalAngleNum = getVtDrawRightAngleSum(orginVtDraw);
        BigDecimal curAngleNum = getVtDrawRightAngleSum(vectorDrawing);
        if (totalAngleNum.compareTo(BigDecimal.valueOf(0)) == 0) {
            return BigDecimal.valueOf(0);
        } else {
            return curAngleNum.divide(totalAngleNum, 5);
        }
    }

    public BigDecimal getVtDrawRightAngleSum(VectorDrawing vectorDrawing) {
        BigDecimal totalRightAngleCount = BigDecimal.valueOf(0);
        // each rectangle have four right angle
        totalRightAngleCount = totalRightAngleCount.add(BigDecimal.valueOf(vectorDrawing.getLines().size()).multiply(BigDecimal.valueOf(4)));
        // line interect line right angle
        Set<String> preRevertCompare = new HashSet<>();
        for (int i = 0; i < vectorDrawing.getLines().size(); i++) {
            for (int j = 0; j < vectorDrawing.getLines().size(); j++) {
                // prevent one element take in place twice
                if (preRevertCompare.contains(j + "|" + i)) {
                    continue;
                }
                preRevertCompare.add(i + "|" + j);
                Line l1 = vectorDrawing.getLines().get(i);
                Line l2 = vectorDrawing.getLines().get(j);
                if (!l1.getVisible() || !l2.getVisible()) {
                    continue;
                }
                Vector2D directOne = Vector2D.create(
                        new Coordinate(l1.getX1(), l1.getY1()),
                        new Coordinate(l1.getX2(), l1.getY2())
                );
                Vector2D directTwo = Vector2D.create(
                        new Coordinate(l2.getX1(), l2.getY1()),
                        new Coordinate(l2.getX2(), l2.getY2())
                );

                double angle = directOne.angleTo(directTwo);
                angle = Math.abs(angle);
                if (angle == Math.PI/2){
                    totalRightAngleCount = totalRightAngleCount.add(BigDecimal.valueOf(1));
                }
            }
        }
        // rectangle interect line right angle even the line and rectangle not intersected
        for (int i = 0; i < vectorDrawing.getRects().size(); i ++) {
            for (int j = 0; j < vectorDrawing.getLines().size(); j++) {
                // prevent one element take in place twice
                GeometryGraph rectGrap = new GeometryGraph(0, vectorDrawing.getRects().get(i).getGeometry());
                Iterator<Edge> it = rectGrap.getEdgeIterator();
                while (it.hasNext()) {
                    Edge edge = it.next();
                    Vector2D rectEdge = Vector2D.create(
                            edge.getCoordinate(0),
                            edge.getCoordinate(1)
                    );
                    Line line = vectorDrawing.getLines().get(j);
                    Vector2D directLine = Vector2D.create(
                            new Coordinate(line.getX1(), line.getY1()),
                            new Coordinate(line.getX2(), line.getY2())
                    );
                    double angle = rectEdge.angleTo(directLine);
                    angle = Math.abs(angle);
                    if (angle == Math.PI/2){
                        totalRightAngleCount = totalRightAngleCount.add(BigDecimal.valueOf(1));
                    }
                }
            }
        }

        // rectangle interect rectangle right anle
        preRevertCompare = new HashSet<>();
        for (int i = 0; i < vectorDrawing.getRects().size(); i ++) {
            for (int j = 0; j < vectorDrawing.getRects().size(); j++) {
                // prevent one element take in place twice
                if (preRevertCompare.contains(j + "|" + i)) {
                    continue;
                }
                preRevertCompare.add(i + "|" + j);
                GeometryGraph rectGrap1 = new GeometryGraph(0, vectorDrawing.getRects().get(i).getGeometry());
                GeometryGraph rectGrap2 = new GeometryGraph(0, vectorDrawing.getRects().get(j).getGeometry());
                Iterator<Edge> it1 = rectGrap1.getEdgeIterator();
                while (it1.hasNext()) {
                    Edge edge1 = it1.next();
                    Iterator<Edge> it2 = rectGrap2.getEdgeIterator();
                    while (it2.hasNext()) {
                        Vector2D rectEdge1 = Vector2D.create(
                                edge1.getCoordinate(0),
                                edge1.getCoordinate(1)
                        );
                        Edge edge2 = it2.next();
                        Vector2D rectEdge2 = Vector2D.create(
                                edge2.getCoordinate(0),
                                edge2.getCoordinate(1)
                        );
                        double angle = rectEdge1.angleTo(rectEdge2);
                        angle = Math.abs(angle);
                        if (angle == Math.PI/2){
                            totalRightAngleCount = totalRightAngleCount.add(BigDecimal.valueOf(1));
                        }
                    }
                }
            }
        }
        return totalRightAngleCount;
    }
}
