package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.PaperDTO;
import com.example.springboot.entity.Paper;
import com.example.springboot.entity.PaperQuestion;
import com.example.springboot.entity.Question;
import com.example.springboot.mapper.PaperMapper;
import com.example.springboot.mapper.QuestionMapper;
import com.example.springboot.mapper.PaperQuestionMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.ServiceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    PaperMapper PaperMapper;

    @Resource
    QuestionMapper QuestionMapper;

    @Resource
    PaperQuestionMapper PaperQuestionMapper;


    @PostMapping
    public Result<?> save(@RequestBody Paper Paper){
        PaperMapper.insert(Paper);
        return Result.success();
    }
    @PostMapping("/composeTest")
    public Result composeTest(@RequestBody PaperDTO PaperDTO){
        QueryWrapper<Question> queryWrapper=new QueryWrapper<>();
        List<Question> questionList=QuestionMapper.selectList(queryWrapper);
        List<PaperQuestion> paperQuestions1=getPaperQuestion(questionList.size(),PaperDTO.getType1(),questionList,PaperDTO.getPaperId());
        for (PaperQuestion paperQuestions : paperQuestions1) {
            PaperQuestionMapper.insert(paperQuestions);
        }

        return Result.success();
    }


    private List<PaperQuestion> getPaperQuestion(int questionSize,int paperQuestionSize,List<Question> source,Integer paperId){
        List<Integer> typeRandomList=getEleList(questionSize,paperQuestionSize);
        List<PaperQuestion> list=new ArrayList<>();
        for(Integer index:typeRandomList){
            Question question=source.get(index);
            PaperQuestion paperQuestion=new PaperQuestion();
            paperQuestion.setPaperId(paperId);
            paperQuestion.setQuestionId(question.getId());
            list.add(paperQuestion);
        }
        return list;
    }
    private List<Integer> getEleList(int sourceSize,int resultSize){
        List<Integer> list= CollUtil.newArrayList();
        for(int i=0;i<sourceSize;i++){
            list.add(i);
        }
        return RandomUtil.randomEleList(list,resultSize);
    }
    @PutMapping
    public Result<?> update(@RequestBody Paper Paper){
        PaperMapper.updateById(Paper);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        PaperMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //最后一个参数是输入的关键字
        Page<Paper> PaperPage=PaperMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Paper>lambdaQuery().like(Paper::getName,search));
        return Result.success(PaperPage);
    }
}