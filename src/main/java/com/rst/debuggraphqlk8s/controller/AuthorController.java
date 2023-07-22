package com.rst.debuggraphqlk8s.controller;

import com.rst.debuggraphqlk8s.model.Author;
import com.rst.debuggraphqlk8s.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @QueryMapping
    public List<Author> allAuthors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    public Optional<Author> findOneAuthor(@Argument Integer id) {
        return authorRepository.findById(id);
    }

    @MutationMapping
    public Author createAuthor(@Argument String firstName, @Argument String lastName) {
        return authorRepository.create(firstName, lastName);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Integer id, @Argument String firstName, @Argument String lastName) {
        return authorRepository.update(id, firstName, lastName);
    }

    @MutationMapping
    public Author deleteAuthor(@Argument Integer id) {
        return authorRepository.delete(id);
    }
}
