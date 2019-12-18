package com.dz.cloud.algorithm;

import com.dz.cloud.algorithm.impl.*;
import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.geolib.Ring;
import com.dz.cloud.model.VectorDrawing;
import org.locationtech.jts.geom.Coordinate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VtFeatureAnalysis {
    private static int DIM_COUNT = 15;
    public static List<VtFeature> VtFeatures = new ArrayList<>();
    private static List<Double> dimWeight = new ArrayList<>();
    static {
        // 图形交点数量
        VtFeatures.add(new InterPointPercentFeature(100d));
        // 圆形占总（有面积线条的）面积比
        VtFeatures.add(new CircleAreaPercentFeature(100d));
        // 椭圆形占总（有面积线条的）面积比
        VtFeatures.add(new EllipseAreaPercentFeature(100d));
        // 当前环与整体圆形数量占比
        VtFeatures.add(new CircleCountPercentFeature(100d));
        // 当前环元素数量占比
        VtFeatures.add(new ElemCountPercentFeature(100d));
        // 单签环椭圆形数量占比
        VtFeatures.add(new EllipseCountPercentFeature(100d));
        // 当前环直线数占比
        VtFeatures.add(new LineCountPercentFeature(100d));
        // 当前环矩形数量占比
        VtFeatures.add(new RectCountPercentFeature(100d));
        // 当前环直角数量在占比
        VtFeatures.add(new RightAnglePercentFeature(100d));
        // 当前环线交角与总体角和的比
        VtFeatures.add(new AngleSumLLPercentFeature(100d));
        // 平行线有多少对/有多少对可以比较的线
        VtFeatures.add(new ParalleLinePercentFeature(100d));
        // 直线延长线与矩形交点数量/2*矩形数量

        // 圈扫盒计算-圈扫盒能描绘出物体的轮廓特征，统计圈扫盒特征有助于统计图形特征
        // 圈扫盒子内角和
        // 圈扫盒的定点个数
        //
    }

    /**
     单环特征计算
     @param interVtDraw
     @return
     */
    private static List<BigDecimal> analyse(VectorDrawing orginVtDraw, VectorDrawing interVtDraw) {
        List<BigDecimal> result = new ArrayList<>();
        VtFeatures.stream().forEach(
                feture-> result.add(feture.calc(orginVtDraw, interVtDraw))
        );
        return result;
    }

    /**
     分环特征提取
     @param vectorDrawing
     @return
     */
    private static List<List<BigDecimal>> sliceAnalyse(VectorDrawing vectorDrawing) {
        List<List<BigDecimal>> result = new ArrayList<>();
        Coordinate center = vectorDrawing.getCentroid();
        Double raduis = vectorDrawing.getRaduis();
        Double ringWidth = raduis / DIM_COUNT;
        for (int i = 0; i < DIM_COUNT; i++) {
            Ring ring = new Ring(center.getX(), center.getY(), i*ringWidth, (i+1) * ringWidth);
            VectorDrawing intersectedVtDraw = vectorDrawing.intersect(ring.getGeometry());
            List<BigDecimal> analyseResult = analyse(vectorDrawing, intersectedVtDraw);
            result.add(analyseResult);
        }
        return result;
    }

    /**
     特征权值合并
     */
    public static List<BigDecimal> getFeature(VectorDrawing vectorDrawing) {
        List<List<BigDecimal>> analyseResult = sliceAnalyse(vectorDrawing);
        List<BigDecimal> feature = new ArrayList<>();
        for (List<BigDecimal> dimResult : analyseResult) {
            BigDecimal oneDimFeatureSum = BigDecimal.valueOf(0);
            for (int i = 0; i < dimResult.size(); i++) {
                oneDimFeatureSum = oneDimFeatureSum.add(dimResult.get(i).multiply(BigDecimal.valueOf(VtFeatures.get(i).getWeight())));
            }
            feature.add(oneDimFeatureSum);
        }
        return feature;
    }

    public static void setVtFeatures(List<Double> vtFeatures) {
        for (int i = 0; i <VtFeatures.size(); i++) {
            VtFeatures.get(i).setWeight(vtFeatures.get(i));
        }
    }
}
