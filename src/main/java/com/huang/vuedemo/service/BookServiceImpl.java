package com.huang.vuedemo.service;

import com.huang.vuedemo.model.Book;
import com.huang.vuedemo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    public static final int PAGE_SIZE = 10;

    @Autowired
    BookRepository bookRepository;

    @Override
    public Page<Book> searchBooksByName(String name, int page) {
        return bookRepository.findByNameIgnoreCaseContainingOrderByIdDesc(name, PageRequest.of(page, PAGE_SIZE));
    }

    @Override
    public Page<Book> searchBooksByNameOrAuthorOrPublisher(String name, String author, String publisher, int page) {
        return bookRepository.findByNameContainingOrAuthorContainingOrPublisherContainingAllIgnoreCaseOrderByIdDesc(name, author, publisher, PageRequest.of(page, PAGE_SIZE));
    }

    /**
     * 根据搜索条件以及是否排序查询记录
     *
     * @param name      书名
     * @param author    作者
     * @param publisher 出版社
     * @param order     排序规则
     * @param prop      排序字段
     * @param page      当前页码
     * @return
     */
    @Override
    public Page<Book> searchBooks(String name, String author, String publisher, String order, String prop, int page) {
        if (!"".equals(order)) {  //根据prop进行排序
            Sort sort = "descending".equals(order) ? (new Sort(Sort.Direction.DESC, prop)) : (new Sort(Sort.Direction.ASC, prop));
            return bookRepository.findByNameContainingOrAuthorContainingOrPublisherContainingAllIgnoreCase(name, author, publisher, PageRequest.of(page, PAGE_SIZE, sort));
        }
        //默认按id升序
        return bookRepository.findByNameContainingOrAuthorContainingOrPublisherContainingAllIgnoreCaseOrderByIdDesc(name, author, publisher, PageRequest.of(page, PAGE_SIZE));
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void saveOrUpdateBook(Book book) {
        book.setUpdateTime(LocalDateTime.now());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Integer deleteByIdIn(List<Long> list) {
        return bookRepository.deleteByIdIn(list);
    }


}
