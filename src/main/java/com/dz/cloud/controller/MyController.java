package com.dz.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.VectorDrawing;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@RestController
@RefreshScope
public class MyController {
    @GetMapping("hello")
    public String hello(){
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("/Users/wangdz/Downloads/method-draw-image.vectorDrawing");
            VectorDrawing vectorDrawing = new SVGParser().parse(d);
            System.out.println(JSON.toJSON(vectorDrawing.getCircles()));
            System.out.println(JSON.toJSON(vectorDrawing.getEllipses()));
            System.out.println(JSON.toJSON(vectorDrawing.getLines()));
            System.out.println(JSON.toJSON(vectorDrawing.getRects()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello1";
    }
}
