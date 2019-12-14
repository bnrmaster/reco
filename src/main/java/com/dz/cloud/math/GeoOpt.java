package com.dz.cloud.math;

import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.index.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
    Use JST to calculate intersect point count.
    But the count is not so exactly, because the
    swipe segment algorithm may cause different segements's
    same end points take in place twice, I use the class PrecisionIntersecotr
    to solve the problem.
 */
public class GeoOpt{
    private GeometryGraph a;
    private GeometryGraph b;
    public GeoOpt(Geometry g0, Geometry g1) {
        this.a =  new GeometryGraph(0, g0, BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE);
        this.b = new GeometryGraph(0, g1, BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE);
    }

    public int intersectPointCount(){
        int result = 0;
        PrecisionIntersecotr si = new PrecisionIntersecotr(new RobustLineIntersector(), false, true);
        si.setBoundaryNodes(a.getBoundaryNodes(), b.getBoundaryNodes());
        EdgeSetIntersector esi = new SimpleMCSweepLineIntersector();
        Iterator it = this.a.getEdgeIterator();
        List edges1 = new ArrayList();
        while(it.hasNext()) {
            edges1.add(it.next());
        }
        List edges2 = new ArrayList();
        it = this.b.getEdgeIterator();
        while(it.hasNext()) {
            edges2.add(it.next());
        }
        esi.computeIntersections(edges1, edges2, si);
        return si.intersectPoint.size();
    }

    /**
        For calculate the intersect points
        and remove the same points
     */
    class PrecisionIntersecotr extends SegmentIntersector {

        private final LineIntersector li;
        private List<Coordinate> intersectPoint;

        public PrecisionIntersecotr(LineIntersector li, boolean includeProper, boolean recordIsolated) {
            super(li, includeProper, recordIsolated);
            this.li = new RobustLineIntersector();
            this.intersectPoint = new ArrayList<>();
        }

        @Override
        public void addIntersections(Edge e0, int segIndex0, Edge e1, int segIndex1){
            if (e0 != e1 || segIndex0 != segIndex1) {
                ++this.numTests;
                Coordinate p00 = e0.getCoordinates()[segIndex0];
                Coordinate p01 = e0.getCoordinates()[segIndex0 + 1];
                Coordinate p10 = e1.getCoordinates()[segIndex1];
                Coordinate p11 = e1.getCoordinates()[segIndex1 + 1];
                this.li.computeIntersection(p00, p01, p10, p11);
                if (this.li.hasIntersection()) {
                    for (int i = 0; i < this.li.getIntersectionNum(); i++) {
                        // do not insert while the intersect point already exist
                        int index = i;
                        if (this.intersectPoint.stream().filter(e->li.getIntersection(index).equals2D(e)).count() == 0) {
                            this.intersectPoint.add(li.getIntersection(i));
                        }
                    }
                }
            }
        }
    }
}
