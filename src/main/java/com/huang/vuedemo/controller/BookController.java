package com.huang.vuedemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.vuedemo.exception.JsonParseException;
import com.huang.vuedemo.model.Book;
import com.huang.vuedemo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
public class BookController {

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    BookService bookService;

    /**
     * 查询
     *
     * @param param 搜索条件
     * @param order 排序规则，""代表默认按id升序
     * @param prop  排序字段，""代表默认按id升序
     * @param page  当前页码
     * @return
     */
    @GetMapping(value = "/books")
    public ResponseEntity<Page<Book>> searchBooks(@RequestParam("param") String param,
                                                  @RequestParam("order") String order,
                                                  @RequestParam("prop") String prop,
                                                  @RequestParam("page") int page) {
        return ResponseEntity.ok(bookService.searchBooks(param, param, param, order, prop, page - 1));
    }

    @GetMapping(value = "/books/findById")
    public ResponseEntity<Book> getBookById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PostMapping(value = "/books/deleteList")
    public ResponseEntity<String> deleteList(String[] ids) {
        List<Long> list = Arrays.stream(ids).map(Long::parseLong).collect(toList());
        Integer a = bookService.deleteByIdIn(list);
        if (a > 0) {
            return ResponseEntity.ok("删除成功！");
        }
        return ResponseEntity.ok("删除失败！");
    }

    @PostMapping(value = "/books/saveOrUpdate")
    public ResponseEntity<String> addOrUpdateBook(@RequestBody @Valid Book book) {
        String result;
        if (book.getId() == null) {
            result = "添加成功";
        } else {
            result = "更新成功";
        }
        bookService.saveOrUpdateBook(book);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/books/delete")
    public ResponseEntity<String> deleteBook(@RequestBody String param) {
        Long id;
        try {
            id = jsonMapper.readTree(param).get("id").asLong();
        } catch (IOException e) {
            throw new JsonParseException();
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok("删除成功");
    }
}
