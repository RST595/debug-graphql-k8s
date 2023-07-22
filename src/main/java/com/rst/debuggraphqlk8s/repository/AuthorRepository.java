package com.rst.debuggraphqlk8s.repository;

import com.rst.debuggraphqlk8s.model.Author;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuthorRepository {
    public static final String ERROR_MESSAGE = "Invalid author";

    private final List<Author> authors = new ArrayList<>();

    @PostConstruct
    private void init() {
        authors.add(new Author(1,"Josh","Long"));
        authors.add(new Author(2,"Mark","Heckler"));
        authors.add(new Author(3,"Greg","Turnquist"));
    }

    public List<Author> findAll() {
        return authors;
    }

    public Optional<Author> findById(int id) {
        return authors.stream()
                .filter(author -> Objects.equals(author.id(), id))
                .findFirst();
    }

    public Author findByName(String name) {
        return authors.stream()
                .filter(author -> author.fullName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Author create(String firstName, String lastName) {
        Author newAuthor = new Author(authors.size() + 1, firstName, lastName);
        authors.add(newAuthor);
        return newAuthor;
    }

    public Author update(Integer id, String firstName, String lastName) {
        Author updatedAuthor = new Author(id, firstName, lastName);
        Optional<Author> optionalAuthor = authors.stream().filter(a -> Objects.equals(a.id(), id)).findFirst();

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            int index = authors.indexOf(author);
            authors.set(index, updatedAuthor);
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        return updatedAuthor;
    }

    public Author delete(Integer id) {
        Author author = authors.stream()
                .filter(a -> Objects.equals(a.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE));
        authors.remove(author);
        return author;
    }
}
