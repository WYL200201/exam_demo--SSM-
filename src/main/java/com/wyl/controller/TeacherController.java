package com.wyl.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.dao.PClassDao;
import com.wyl.pojo.*;
import com.wyl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class TeacherController {
    @Autowired
    UsersService usersService;
    @Autowired
    PClassService pClassService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExamService examService;
    @Autowired
    PaperService paperService;
    @Autowired
    StuScoreService stuScoreService;
//获取学生列表的方法
    //需要进行分页配置(在配置文件中)
    @GetMapping("/StudentList")
    public String findStuUsers(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1",value="pageNum")Integer pageNum){
        //在获取自己班级的学生列表的时候，教师也要在前端看到自己的班级信息（其实是看不到的，只是为了传递）
        //获取前端的班级信息
        Integer teaClassid=(Integer) request.getSession().getAttribute("TeaClassid");//是一个隐藏的，用于传递信息
        //查看教师所在班级
        PClass aclass=pClassService.getClassid(teaClassid);
        //使用model.addAttribute将班级名称传入前端
        model.addAttribute("pj",aclass);

        //通过班级id查询学生
        //设置分页, @RequestParam(defaultValue = "1",value="pageNum")Integer pageNum
        //每一页显示6个
        PageHelper.startPage(pageNum,5);
        List<Users> users=usersService.pageByClassid(teaClassid);
        //之后new一下PageInfo将学生列表传进去
        PageInfo<Users> usersPageInfo=new PageInfo<>(users);
        //根据前端的端口实现前端可以翻页
        model.addAttribute("pageInfo",usersPageInfo);
        model.addAttribute("liss",users);

        //获得所有班级信息
        List<PClass> allPClass=pClassService.getAllPClass();
        model.addAttribute("list",allPClass);
        return "teacher/StudentList";
    }

//添加学生
    @PostMapping("/addStu")
    public String addStuUsers(String username,String userpwd,String truename,Integer classid){
        //学生注册那里已经使用了添加学生的
        //查询学生是否存在
        Users byName=usersService.getByName(username);
        Users users=new Users();
            if(byName==null){
                users.setUsername(username);
                users.setTruename(truename);
                users.setClassid(classid);
                users.setUserpwd(userpwd);
                usersService.addUsers(users);
            }else{
                System.out.println("该学生已经存在！");
            }
            return "redirect:/StudentList";
    }

//修改学生信息
    //因为返回的是数据，所以要加上ResponseBody注解
    @ResponseBody
    //查询所有班级,用于下拉栏
    @PostMapping("/findAllClass")
    public List<PClass> pClassList(){
        List<PClass> allPClass = pClassService.getAllPClass();
        return allPClass;
    }
    //因为返回的是数据，所以要加上ResponseBody注解
    @ResponseBody
    @PostMapping("/StuEdit")
    //要先获取到学生信息
    public Users stuEdit(@RequestBody Users users){
        //通过学生的id进行查询
        Users byUserid = usersService.getByUserid(users.getUserid());
        if(byUserid!=null){
            return byUserid;
        }else{
            return null;
        }
    }
    //修改学生信息
    @PostMapping("/updateStu")
    public String UpdateStu(Users users){
        usersService.updateUsers(users);
        return "redirect:/StudentList";
    }


//删除单个学生
    @GetMapping("/DeleteStu")
    public String deleteUsers(HttpServletRequest request){
        //通过学生id获取
        Integer userid =Integer.valueOf(request.getParameter("userid"));
        usersService.delUserid(userid);
        return "redirect:/StudentList";
    }

//删除所有本班级的学生（不能删除其他班级，通过班级来删除学生）
    @GetMapping("/deleteAll")
    public String daleteUsersAll(HttpServletRequest request){
        Integer teaClassid=(Integer) request.getSession().getAttribute("TeaClassid");
        usersService.delClassid(teaClassid);
        return "redirect:/StudentList";
    }


//查询题目的方法
    @GetMapping("/finddanxuan")
    public String findSingle(@RequestParam(defaultValue = "1",value="pageNum") Integer pageNum,Model model){
        PageHelper.startPage(pageNum,5);
        List<Subject> subjects=subjectService.getAllSubject();
        //前端的接口还需要课程名称
        for(Subject subject:subjects){
            Course allById = courseService.getAllById(subject.getCno());
            subject.setCourse(allById);
        }
        //查看题目需要分页，所以写一下分页
        //设置每5条数据进行分页
        //new一下pageInfo，将题的列表传进去
        PageInfo<Subject> subjectPageInfo =new PageInfo<>(subjects);
        model.addAttribute("pageInfo",subjectPageInfo);
        model.addAttribute("subjectlist",subjects);
        return "teacher/Single";
        //不是redirect:Single
    }

//添加题目

    //获取所有课程的方法
    @ResponseBody
    @GetMapping("/findAllCourse")
    public List<Course> getAllCourse(){
        List<Course> allCourse = courseService.getAllCourse();
        return allCourse;
    }

    @PostMapping("/addSingle")
    public String addSingle(Integer cno,String scontent,String sa,String sb,String sc,String sd,String skey){
        Subject subject1 = new Subject();
        subject1.setCno(cno);
        subject1.setScontent(scontent);
        subject1.setSa(sa);
        subject1.setSb(sb);
        subject1.setSc(sc);
        subject1.setSd(sd);
        subject1.setSkey(skey);
        subjectService.addSubject(subject1);
        return "redirect:/finddanxuan";
    }
//题的修改
    //修改题的前提是必须要先查询一下要修改题的数据
    @ResponseBody
    @PostMapping("/findBySid")
    public Subject findById(@RequestBody Subject subject){
        //查看前端代码可知，点击按钮后自动把sid传到这个ajax方法里了,然后ajax再把这个数以json形式传到后端
        //后端通过查询数据库得到信息传给前端
        // 从前端拿的有sid属性subject还是要返回到前端去
        Integer sid = subject.getSid();
        Subject byId = subjectService.getById(sid);
        if(byId!=null){return byId;}else{return null;}
    }
    @PostMapping("/updateSingle")
    public String updateSingle(Subject subject){
        subjectService.updateSubject(subject);
        return "redirect:/finddanxuan";
    }

//题目的删除
    //单个题目的删除
    @PostMapping("/deleteSingle")
    public String deleteSingle(@RequestParam Integer sid) {
        subjectService.delSingle(sid);
        return "redirect:/finddanxuan";
    }


//查询考试信息列表
    @GetMapping("/selectexam")
    public String selectExam(@RequestParam(defaultValue = "1",value="pageNum") Integer pageNum,Model model){
        PageHelper.startPage(pageNum,5);
        List<Exam> exams = examService.allExam();
        //new一下pageInfo，将题的列表传进去
        PageInfo<Exam> examPageInfo = new PageInfo<>(exams);
        model.addAttribute("examlist",exams);
        model.addAttribute("pageInfo",examPageInfo);
        return "teacher/SelectExam";
    }



//添加考试
    //界面跳转
    //这里跳转过去就是一个静态的界面，和原来的不同，它不需要
    //立即从后端传递数据到前端
    @GetMapping("/addexam")
    public String toAddExam(){
        return "teacher/addexam";
    }
    @PostMapping("/addexames")
    public String addExam(String pname, Integer userid, Integer cno,Integer classid,Integer singlenumber, Integer singlecore, Date examdate,Date examtime,Integer testtime){

        Exam exam = new Exam();
        exam.setPname(pname);
        exam.setUserid(userid);
        exam.setClassid(classid);
        exam.setCno(cno);
        exam.setSinglenumber(singlenumber);
        exam.setSinglecore(singlecore);
        exam.setExamdate(examdate);
        exam.setExamtime(examtime);
        exam.setTesttime(testtime);
        examService.addExam(exam);
        //注意：!!!!命题人是由session（login界面）登录时候传入的

        //通过cno来查询题目
        List<Subject> subByCno = subjectService.getSubByCno(cno);
        //随机生成相应的题目
        ArrayList<Subject> subjects = new ArrayList<>();
        Random random=new Random();
        if(singlenumber>0){
            for(int i=1;i<=singlenumber;i++){
                //nextInt(int x)则会生成一个范围在0~x（不包含X）内的任意正整数
                int j=random.nextInt(subByCno.size());
                //获取链表中指定位置处的元素,随机拿到一个题目
                Subject ramSubject=subByCno.get(j);
                if(i==subByCno.size()){
                    break;
                }else{
                    //如果链表包含指定元素，返回true(防止题目重复)
                    if (subjects.contains(ramSubject)){
                        i--;
                    }else{
                        //增加指定元素到链表尾部
                        subjects.add(ramSubject);
                        //接着就是向试卷里添加
                        Paper paper=new Paper();
                        paper.setEid(exam.getEid());//！!!添加考试信息，自动生成相应的题目
                        paper.setCno(cno);
                        paper.setSid(ramSubject.getSid());
                        paper.setScontent(ramSubject.getScontent());
                        paper.setSa(ramSubject.getSa());
                        paper.setSb(ramSubject.getSb());
                        paper.setSc(ramSubject.getSc());
                        paper.setSd(ramSubject.getSd());
                        paper.setSkey(ramSubject.getSkey());
                        System.out.println(paper);
                        paperService.addPaper(paper);
                        //没写查询考试信息，先随便返回一个页面
                    }
                }
            }
        }
        //return "redirect:/selectexam";
        return "redirect:/addexam";
    }

//通过考试信息的id查询试卷信息
@GetMapping("/paperDetails")
    public String paperDetails(@RequestParam Integer eid,Model model){
    List<Paper> paperByEid = paperService.findPaperByEid(eid);
    Exam examByEid = examService.findExamByEid(eid);
    model.addAttribute("exam",examByEid);
    model.addAttribute("tm",paperByEid);
    return"teacher/paperDetails";
    }


//修改考试信息
    //查询一场考试信息的方法
    @ResponseBody
    @PostMapping("/findByOneExam")
    public  Exam findOneExam(@RequestBody Exam exam){
        Exam examByEid = examService.findExamByEid(exam.getEid());
        if(examByEid==null){
            return null;
        }else{
        return examByEid;
        }
    }
    //返回班级信息列表
    @ResponseBody
    @GetMapping("/findAllClasses")
    public List<PClass> findAllClasses(){
        List<PClass> allPClass = pClassService.getAllPClass();
        return allPClass;
    }
    @PostMapping("/updateExam")
    public String exitExam(Exam exam){
        examService.exitExam(exam);
        return "redirect:/selectexam";
    }

//删除考试信息
    //删除一个考试信息
    @GetMapping("/deleteExam")
    public String delExam(@RequestParam Integer eid){
        examService.deleExam(eid);
        return "redirect:/selectexam";
    }

//查询学生成绩
    @GetMapping("/findAllScore")
    public String findStuScores(HttpServletRequest request,Model model,@RequestParam(defaultValue = "1",value="pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,5);
        Integer teaClassid = (Integer) request.getSession().getAttribute("TeaClassid");
        PClass clas=pClassService.getClassid(teaClassid);
        model.addAttribute("cs",clas);
        List<StuScore> stuScoreByClassid = stuScoreService.getStuScoreByClassid(teaClassid);
        for(StuScore ss :stuScoreByClassid){
            Users byUserid = usersService.getByUserid(ss.getUserid());
            ss.setUsers(byUserid);
        }
        PageInfo<StuScore> stuScorePageInfo = new PageInfo<>(stuScoreByClassid);
        model.addAttribute("pageInfo",stuScorePageInfo);
        model.addAttribute("score",stuScoreByClassid);
        return "teacher/studentScore";
    }
}

