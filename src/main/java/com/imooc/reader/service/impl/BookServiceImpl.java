package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    /**
     * 分页查询图书
     * @param  categoryId 分类编号
     * @param order 排序方式
     * @param page 页号
     * @param rows 每页记录数
     * @return     分页对象
     */
    public IPage<Book> paging(Long categoryId,String order,Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page,rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        if(categoryId!=null&&categoryId!=-1){
            queryWrapper.eq("category_id",categoryId);
        }
        if(order!=null){
            if("quantity".equals(order)){
                queryWrapper.orderByDesc("evaluation_quantity");
            }else if("score".equals(order)){
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        Page<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    /**
     * 根据图书编号查询图书对象
     * @param bookId 图书编号
     * @return  图书对象
     */
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }
}
