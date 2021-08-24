package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 分页查询短评
     * @param page 页号
     * @param rows 每页记录数
     * @return     分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     * 根据评论编号查询评论
     * @param evaluationId
     * @return
     */
    public Evaluation selectByEvaluationId(Long evaluationId);

    /**
     * 更新评论禁用
     * @param evaluation
     * @return
     */
    public Evaluation updateEvaluation(Evaluation evaluation);
}
