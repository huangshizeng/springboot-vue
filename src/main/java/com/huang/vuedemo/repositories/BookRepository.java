package com.huang.vuedemo.repositories;


import com.huang.vuedemo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByNameIgnoreCaseContainingOrderByIdDesc(String name, Pageable request);

    Page<Book> findByNameContainingOrAuthorContainingOrPublisherContainingAllIgnoreCaseOrderByIdDesc(String name, String author, String publisher, Pageable request);

    Page<Book> findByNameContainingOrAuthorContainingOrPublisherContainingAllIgnoreCase(String name, String author, String publisher, Pageable request);

    Integer deleteByIdIn(List<Long> list);
}
