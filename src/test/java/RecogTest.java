import com.alibaba.fastjson.JSON;
import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.VectorDrawing;
import com.dz.cloud.utlis.VtDrawUtils;
import lombok.Data;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RecogTest {
    @Data
    class VtDrawAndDist{
        public VtDrawAndDist(VectorDrawing vtDraw, BigDecimal distance) {
            this.vtDraw = vtDraw;
            this.distance = distance;
        }

        VectorDrawing vtDraw;
        BigDecimal distance;
    }
    @Test
    public void Classification() {
        // 初始化模板
        List<VectorDrawing> templates = new ArrayList<>();

        templates.add(VtDrawUtils.getVectorDrawing("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image5.svg"));
        templates.add(VtDrawUtils.getVectorDrawing("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image11.svg"));
        templates.add(VtDrawUtils.getVectorDrawing("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image30.svg"));
        templates.add(VtDrawUtils.getVectorDrawing("file:///Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image36.svg"));
        templates.add(VtDrawUtils.getVectorDrawing("file:///Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image22.svg"));
        templates.add(VtDrawUtils.getVectorDrawing("file:///Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image13.svg"));

        List<VectorDrawing> vtDraws = this.getFolderVtDraw("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1");
        HashMap<String, List<VtDrawAndDist>> bucket = new HashMap<>();
        for (VectorDrawing vtDraw: vtDraws) {
            BigDecimal distance = null;
            VectorDrawing electedVtDraw = null;
            for(VectorDrawing template: templates) {
                BigDecimal resultDistance = vtDraw.distance(template);
                if (distance == null) {
                    distance = resultDistance;
                    electedVtDraw = template;
                } else {
                   if (distance.compareTo(resultDistance) > 0){
                       distance = resultDistance;
                       electedVtDraw = template;
                   }
                }
            }
            List<VtDrawAndDist> vtList = bucket.computeIfAbsent(electedVtDraw.getFilePath(), e->new ArrayList<>());
            vtList.add(new VtDrawAndDist(vtDraw, distance));
        }
        for (String k : bucket.keySet()) {
            List<VtDrawAndDist> list = bucket.get(k);
            list.sort(Comparator.comparing(VtDrawAndDist::getDistance));
        }

        HashMap<String, List<String>> result = new HashMap<>();
        for (String k: bucket.keySet()){
            result.put(k, bucket.get(k).stream().map(e->e.getVtDraw().getFilePath()).collect(Collectors.toList()));
        }
        System.out.println(JSON.toJSON(result));
    }

    private List<VectorDrawing> getFolderVtDraw(String path) {
        List<VectorDrawing> result = new ArrayList<>();
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            String fileDirectory = path;//scanner("请输入图片所在文件夹");
            File file = new File(fileDirectory);		//获取其file对象
            File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(File f:fs){					//遍历File[]数组
                if(!f.isDirectory()) {
                    //log.info("正在计算图片"+f.getAbsolutePath()+"特征");
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document d = builder.parse(f.getAbsolutePath());
                    VectorDrawing vectorDrawing = SVGParser.parse(d);
                    //List<BigDecimal> feature = vectorDrawing.makeFeature();
                    //log.info(""+feature.stream().map(e->e.doubleValue()).collect(Collectors.toList()));
                    result.add(vectorDrawing);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
