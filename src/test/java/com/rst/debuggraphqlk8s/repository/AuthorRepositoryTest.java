package com.rst.debuggraphqlk8s.repository;

import com.rst.debuggraphqlk8s.model.Author;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test Faker work. Generate some fake data for tests. Has popular fields, like book, name, etc.
 */
class AuthorRepositoryTest {
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final Faker faker = new Faker();

    @BeforeEach
    void init() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1,faker.name().firstName(),faker.name().lastName()));
        authors.add(new Author(2,faker.name().firstName(),faker.name().lastName()));
        authors.add(new Author(3,faker.name().firstName(),faker.name().lastName()));
        ReflectionTestUtils.setField(authorRepository, "authors", authors);
    }

    @Test
    void testFaker() {
        authorRepository.findAll().forEach(System.out::println);
        assertNotEquals("Josh", authorRepository.findById(1).get().firstName());
    }
}
