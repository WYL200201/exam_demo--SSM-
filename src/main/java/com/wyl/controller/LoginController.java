package com.wyl.controller;

import com.wyl.pojo.TeaUser;
import com.wyl.pojo.Users;
import com.wyl.service.TeaUserService;
import com.wyl.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    //usersService自动注入
    @Autowired
    UsersService usersService;
    @Autowired
    TeaUserService teaUserService;

    //根据前端页面，写一下教师和学生界面的互相跳转
    @GetMapping("toTeacher")
    public String toTeaLogin(){
        return "teacher/login";
    }
    @GetMapping("toStuLogin")
    public String toStuLogin(){
        return "student/login";
    }


//学生的登录
    //在前端我们可以拿到username和userpwd的值
    //根据前端的请求方式写一下注解
    @PostMapping("/stulogin")
    public String stulogin(String username, String userpwd, HttpServletRequest request){
        //调用一下UsersService的login方法
        Users login=usersService.login(username,userpwd);
        if(login==null){
            request.getSession().setAttribute("mag","密码错误");
            System.out.println("登陆失败");
            //返回login页面
            return "redirect:/toStuLogin";
        }else{
            System.out.println("登陆成功");
            Integer userIdByName = usersService.findUserIdByName(username);
            String trueNameByName = usersService.findTrueNameByName(username);
            Integer clssIdByName = usersService.findClssIdByName(username);
            request.getSession().setAttribute("truename",trueNameByName);
            request.getSession().setAttribute("stuid",userIdByName);
            request.getSession().setAttribute("passwd",userpwd);
            request.getSession().setAttribute("clssIdByName",clssIdByName);

            //返回学生首页
            return "student/StuMan";
        }
    }


//教师登录
    @PostMapping("/tealogin")
    public String teaLogin(String username,String userpwd,HttpServletRequest request){
        //为了查询班级中学生的信息，查一下老师的班级id
        Integer classId= teaUserService.getClassId(username);
        String teaTruename=teaUserService.getTeaTruename(username);
        Integer teaIdByName = teaUserService.getTeaIdByName(username);
        //接收前端username，userpwd的值
        TeaUser teaUser=teaUserService.teaLogin(username,userpwd);
        if(teaUser==null){
            request.getSession().setAttribute("msg","登录密码错误");
            System.out.println("登陆失败");
            //返回login页面
            return "redirect:/toTeacher";
        }else{
            System.out.println("登录成功");
            //将教师真实姓名传入前端
            teaUserService.getTeaTruename(username);
            request.getSession().setAttribute("Teatruename",teaTruename);
            //将教师的班级id传入前端
            request.getSession().setAttribute("TeaClassid",classId);//是隐藏的
            //将教师的id值传入前端
            request.getSession().setAttribute("Teauserid",teaIdByName);
            return "teacher/manage";
        }
    }
}
