package com.dz.cloud.controller;

import com.dz.cloud.algorithm.VtFeatureAnalysis;
import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.service.Recognizator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class RecogController {
    @GetMapping("getRecogResult")
    public HashMap<String, List<String>> hello(){
        Recognizator recognizator = new Recognizator();
        HashMap<String, List<String>> result = recognizator.Classification();
        result.forEach((k,v )->{
            for (int i = 0; i < v.size(); i++) {
                v.set(i, v.get(i).replace("file:///Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/", "/material/"));
            }
        });
        HashMap<String, List<String>> newResult = new HashMap<>();
        result.forEach((k,v)->{
            newResult.put(k.replace("file:///Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/", "/material/"), v);
        });
        return newResult;
    }

    @GetMapping("getWeight")
    public List weight(){
        return VtFeatureAnalysis.VtFeatures;
    }

    @PostMapping(value = "setWeight", produces = "application/json;charset=UTF-8")
    public void setWeight(@RequestBody List<Double> weights) {
        VtFeatureAnalysis.setVtFeatures(weights);
    }
}
