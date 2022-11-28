package com.wyl.controller;

import com.wyl.pojo.PClass;
import com.wyl.pojo.Users;
import com.wyl.service.PClassService;
import com.wyl.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class StuUserController {
    //UsersService自动注入
    @Autowired
    UsersService usersService;
    @Autowired
    PClassService pClassService;
    @GetMapping("/register")
    public String toRegister(Model model) {
        //调用service层获取全部班级信息
        List<PClass> allPClass=pClassService.getAllPClass();
        //将班级列表传入前台
        model.addAttribute("list",allPClass);
        return "student/register";
    }


//学生账号注册
    @PostMapping("/CanRegister")
    public String register(Users users, String username, String userpwd, String truename, Integer classid,HttpServletRequest request) {
        //查询用户信息的代码一定要放在上面
        Users byName = usersService.getByName(username);
        users.setUsername(username);
        users.setClassid(classid);
        users.setUserpwd(userpwd);
        users.setTruename(truename);
        usersService.addUsers(users);
        //判断一下是否拥有该用户，UsersDao设置一个查询用户的接口
        if(byName.equals("")){
            if (userpwd==null) {
                request.getSession().setAttribute("msg1","密码为空");
                return "redirect:/register";
                //在前端进行显示
            }
            if (truename.equals("")){
                request.getSession().setAttribute("msg2","真实姓名为空");
                return "redirect:/register";
            }
            if (username.equals("")){
                request.getSession().setAttribute("msg3","用户名为空");
                return "redirect:/register";
            }
            System.out.println("用户可以注册");
            return "redirect:/toStuLogin";
        }else{
            System.out.println("用户已经存在");
            return "redirect:/register";
        }
    }

//学生修改密码的功能
    @PostMapping("/exitStu")
    public String updateUserPwd(String userpwd,Integer userid){
        Integer j = usersService.updateUserPwd(userpwd, userid);
        if(j==0){
            return "";//修改失败
        }else{
            return "redirect:/toStuLogin";//修改成功
        }
    }

//退出登录
    @GetMapping("/logout")
    public String logOut(HttpServletRequest request){
        request.getSession().removeAttribute("truename");
        request.getSession().removeAttribute("stuid");
        request.getSession().removeAttribute("passwd");
        return "redirect:/toStuLogin";
    }
}
