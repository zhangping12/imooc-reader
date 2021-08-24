package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("state","enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for(Evaluation eva:evaluationList){
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }

    /**
     * 分页查询短评
     * @param page 页号
     * @param rows 每页记录数
     * @return     分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<Evaluation>(page,rows);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        Page<Evaluation> pageObject = evaluationMapper.selectPage(p, queryWrapper);
        for(Evaluation eva:pageObject.getRecords()){
            Member member = memberMapper.selectById(eva.getMemberId());
            Book book = bookMapper.selectById((eva.getBookId()));
            eva.setMember(member);
            eva.setBook(book);
        }
        return pageObject;
    }

    /**
     * 根据评论编号查询评论
     * @param evaluationId
     * @return
     */
    public Evaluation selectByEvaluationId(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        return evaluation;
    }

    /**
     * 更新评论禁用
     * @param evaluation
     * @return
     */
    public Evaluation updateEvaluation(Evaluation evaluation) {
        evaluationMapper.updateById(evaluation);

        return evaluation;
    }
}
