package com.example.mxptools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：
 *
 * @author firefly by 2024/3/26
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/goodsCla")
    public String goodsCla(){
        return "goodsCla";
    }

}
