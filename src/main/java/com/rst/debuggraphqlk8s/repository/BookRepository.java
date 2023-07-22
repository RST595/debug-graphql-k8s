package com.rst.debuggraphqlk8s.repository;

import com.rst.debuggraphqlk8s.model.Book;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rst.debuggraphqlk8s.model.Rating.FIVE_STARS;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final AuthorRepository authorRepository;
    private final List<Book> books = new ArrayList<>();

    @PostConstruct
    private void init() {
        books.add(new Book(1,
                "Reactive Spring",
                484,
                FIVE_STARS,
                authorRepository.findByName("Josh Long")));
        books.add(new Book(2,
                "Spring Boot Up & Running",
                328,
                FIVE_STARS,
                authorRepository.findByName("Mark Heckler")));
        books.add(new Book(3,
                "Hacking with Spring Boot 2.3",
                392,
                FIVE_STARS,
                authorRepository.findByName("Greg Turnquist")));
    }

    public List<Book> findAll() {
        return books;
    }

    public Book findOne(Integer id) {
        return books.stream()
                .filter(book -> Objects.equals(book.id(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
