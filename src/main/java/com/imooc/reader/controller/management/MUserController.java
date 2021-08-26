package com.imooc.reader.controller.management;

import com.imooc.reader.entity.User;
import com.imooc.reader.service.UserService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management")
public class MUserController {
    @Resource
    private UserService userService;

    @GetMapping("/login.html")
    public ModelAndView showIndex(){
        return new ModelAndView("/management/login");
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map login(String username, String password, HttpSession session){
        Map result = new HashMap();
        try{
            User user = userService.checkLogin(username, password);
            session.setAttribute("loginUser", user);
            result.put("code","0");
            result.put("msg","success");
            result.put("redirect_url","/management/index.html");
        }catch(BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }

    /**
     * 注销
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.removeAttribute("loginUser");
        return new ModelAndView("management/login");
    }
}
