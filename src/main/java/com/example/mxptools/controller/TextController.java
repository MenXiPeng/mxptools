package com.example.mxptools.controller;

import com.example.mxptools.util.goodsClas;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author firefly by 2024/3/26
 */
@RestController
@RequestMapping("/goods")
public class TextController {

    @PostMapping("/cla")
    public Map<String, List<String>> demo(@RequestBody Map<String,String> param){

        return goodsClas.cla(param.get("data"), param.get("address"));
    }

}
