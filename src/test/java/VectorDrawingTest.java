import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.VectorDrawing;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class VectorDrawingTest {
    @Test
    public void SVGTest() {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("/Users/wangdz/Downloads/method-draw-image4.svg");
            VectorDrawing vectorDrawing = SVGParser.parse(d);
            vectorDrawing.setWidth(500d);
            vectorDrawing.setHeight(400d);
            System.out.println("Circle:" + vectorDrawing.getCircles().size());
            System.out.println("Ecllipse:" + vectorDrawing.getEllipses().size());
            System.out.println("Line:"+ vectorDrawing.getLines().size());
            System.out.println("Rect:"+ vectorDrawing.getRects().size());
            List<BigDecimal> feature = vectorDrawing.makeFeature();
            System.out.println("Feature Vector:"+feature.stream().map(e->e.doubleValue()).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!ipt.isEmpty()) {
                return ipt;
            }
        }
        return "";
    }

    @Test
    public void SVGCalc() {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            String fileDirectory = "/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1";//scanner("请输入图片所在文件夹");
            File file = new File(fileDirectory);		//获取其file对象
            File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
            for(File f:fs){					//遍历File[]数组
                if(!f.isDirectory()) {
                    //log.info("正在计算图片"+f.getAbsolutePath()+"特征");
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document d = builder.parse(f.getAbsolutePath());
                    VectorDrawing vectorDrawing = SVGParser.parse(d);
                    List<BigDecimal> feature = vectorDrawing.makeFeature();
                    log.info(""+feature.stream().map(e->e.doubleValue()).collect(Collectors.toList()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
