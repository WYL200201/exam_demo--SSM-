package com.wyl.controller;

import com.sun.jmx.snmp.Timestamp;

import com.wyl.pojo.Course;
import com.wyl.pojo.Exam;
import com.wyl.pojo.Paper;
import com.wyl.pojo.StuScore;
import com.wyl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ExamController {
    @Autowired
    UsersService usersService;
    @Autowired
    ExamService examService;
    @Autowired
    CourseService courseService;
    @Autowired
    PaperService paperService;
    @Autowired
    StuScoreService stuScoreService;
//考试信息列表
    @GetMapping("/examList")
    public String examList(HttpServletRequest request, Model model){
        //通过班级id查询考试所有信息
        Integer classid = (Integer) request.getSession().getAttribute("clssIdByName");
        List<Exam> examList = examService.getExamList(classid);
        for(Exam exam:examList){
            //数据库里是没有exam对应的course信息的，这里需要遍历去拿
            Course allById = courseService.getAllById(exam.getCno());
            exam.setCourse(allById);
        }
        model.addAttribute("examslenth",examList.size());
        model.addAttribute("exams",examList);
        return "student/examList";
    }

//答卷
    //首先要把这个试卷信息的对象传递到前端
    @ResponseBody
    @PostMapping("/findExamByEid")
    public Exam findOneExam(@RequestBody Exam exam){
        Exam examByEid = examService.findExamByEid(exam.getEid());
        if(examByEid!=null) {
            return examByEid;
        }else{
            return null;
        }
    }
    //查询试卷
    @GetMapping("/paper")
    public String findPaper(@RequestParam Integer eid,Model model,HttpServletRequest request){
        List<Paper> paperByEid = paperService.findPaperByEid(eid);
        Exam examByEid = examService.findExamByEid(eid);
        request.getSession().setAttribute("single",paperByEid);
        model.addAttribute("exam",examByEid);
        model.addAttribute("count",paperByEid.size());
        return "student/papers";

    }


//计算学生考试成绩
    @PostMapping("/PaperScore")
    public String paperScore(HttpServletRequest request,Model model){
        //存放学生选择的答案
        String stuAnswer[]=null;
        Integer score=0;
        //获取每道题分数值
        Integer eid = Integer.valueOf(request.getParameter("eid"));
        Exam examByEid = examService.findExamByEid(eid);
        Integer singlecore = examByEid.getSinglecore();
        //获取试卷的问题列表
        List<Paper> single = (List<Paper>) request.getSession().getAttribute("single");
        for(int i=0;i< single.size();i++){
            Paper paper= single.get(i);
            //阵列!妙
            stuAnswer=request.getParameterValues(String.valueOf(paper.getSid()));//(1,3,5)
            if(stuAnswer!=null){
                String stuKey="";
                //封装答案
                for(int j=0;j< stuAnswer.length;j++){
                    stuKey +=stuAnswer[j];
                }
                //equalsIgnoreCase() 方法用于将字符串与指定的对象比较，不考虑大小写
                if(stuKey.equalsIgnoreCase(paper.getSkey())){
                    score=score+singlecore;
                }
            }else{
                return "redirect:/examList";
            }
        }

        //将成绩存储到学生成绩存储表
        //计算总成绩
        int sum = single.size() * singlecore;
        model.addAttribute("score",score);
        String pname = request.getParameter("pname");
        Integer classid = (Integer) request.getSession().getAttribute("clssIdByName");
        Integer stuid = (Integer) request.getSession().getAttribute("stuid");
        //学生考试时长
        String titime = request.getParameter("tjtime");
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //java.sql.Timestamp.valueOf(titime);
        StuScore stuScore=new StuScore();
        stuScore.setEid(eid);
        stuScore.setPname(pname);
        stuScore.setScore(score);
        stuScore.setClassid(classid);
        stuScore.setUserid(stuid);
        stuScore.setTitime(titime);
        stuScore.setZscore(sum);
        stuScoreService.addStuScore(stuScore);
        return"student/paperScore";
    }

//查询学生成绩
    @GetMapping("/findAllStuPaper")
    public String findStuScore(HttpServletRequest request,Model model){
        Integer stuid = (Integer) request.getSession().getAttribute("stuid");
        List<StuScore> stuScore = stuScoreService.findStuScore(stuid);
        //将数据传入前端stuPaperList.html
        model.addAttribute("stuexamlist",stuScore);
        return "student/stuPaperList";
    }

//判断学生是否做过该试卷
    @ResponseBody
    @PostMapping("/findOneStuExam")
    public List<StuScore> findIfDone(HttpServletRequest request,@RequestBody Exam exam){
        Integer stuid = (Integer) request.getSession().getAttribute("stuid");
        Integer eid=exam.getEid();
        List<StuScore> ifDone = stuScoreService.getIfDone(stuid, eid);
        return ifDone;
    }

//跳转在线考试
    @GetMapping("/stuMan")
    public String getStuMain(){
        return "student/StuMan";
    }
}
