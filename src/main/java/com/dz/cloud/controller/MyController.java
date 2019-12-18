package com.dz.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.VectorDrawing;
import com.dz.cloud.service.Recognizator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.List;

@Controller
@RefreshScope
public class MyController {
    @GetMapping("index")
    public String getIndex() {
        return "/bucket";
    }
}
