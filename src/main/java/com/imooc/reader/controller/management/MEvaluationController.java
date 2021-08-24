package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @GetMapping("/index.html")
    public ModelAndView showEvaluation(){
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page,Integer limit){
        if (page == null){
            page = 1;
        }

        if(limit == null){
            limit = 20;
        }
        IPage<Evaluation> pageObject = evaluationService.paging(page,limit);
        Map result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",pageObject.getRecords());//当前页面数据
        result.put("count",pageObject.getTotal());//未分页时记录总数
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disable(Long evaluationId,String reason){
        Map result = new HashMap();
        try {
            Evaluation rawevaluation = evaluationService.selectByEvaluationId(evaluationId);
            rawevaluation.setDisableReason(reason);
            rawevaluation.setDisableTime(new Date());
            rawevaluation.setState("disable");
            evaluationService.updateEvaluation(rawevaluation);
            result.put("code", "0");
            result.put("msg", "success");
        }catch(BussinessException ex){
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

}
