package com.rst.debuggraphqlk8s.controller;

import com.rst.debuggraphqlk8s.model.Book;
import com.rst.debuggraphqlk8s.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @SchemaMapping(typeName = "Query", value = "allBooks")
    // Or @QueryMapping(value = "allBooks") or @QueryMapping and name method as in schema.graphqls -> allBooks
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Book findOne(@Argument Integer id) {
        return bookRepository.findOne(id);
    }
}
