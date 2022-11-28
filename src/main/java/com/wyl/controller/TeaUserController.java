package com.wyl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TeaUserController {
    @GetMapping("/Tlogout")
    public String TOut(HttpServletRequest request){
        request.getSession().removeAttribute("Teauserid");
        request.getSession().removeAttribute("Teatruename");
        request.getSession().removeAttribute("TeaClassid");
        return"redirect:/toTeacher";
    }
}
