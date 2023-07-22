package com.rst.debuggraphqlk8s.controller;

import com.rst.debuggraphqlk8s.model.Author;
import com.rst.debuggraphqlk8s.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@GraphQlTest(AuthorController.class) // In brackets, we could choose which classes load to context.
// If none, all controllers will be loaded
@Import(AuthorRepository.class)
class AuthorControllerTest {
    public static final String FIRST_AUTHOR = "Josh Long";
    @Autowired
    private GraphQlTester graphQlTester;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void initData() {
        ReflectionTestUtils.setField(authorRepository, "authors", new ArrayList<>());
        ReflectionTestUtils.invokeMethod(authorRepository, "init");
    }

    @Test
    void findAllAuthors() {
        // """ brackets create multiline string since java 16.
        // Comment "language=GraphQL" enable query processing in string as a query, not plain text.
        // GIVEN
        // language=GraphQL
        String document = """
                query {
                  allAuthors{
                    lastName
                    firstName
                  }
                }
                """;

        // WHEN | THEN
        GraphQlTester.EntityList<Author> allAuthors = graphQlTester.document(document)
                .execute()
                .path("allAuthors")
                .entityList(Author.class)
                .hasSize(3);
        assertNotNull(allAuthors);
    }

    @Test
    void findOneAuthor() {
        // GIVEN
        // language=GraphQL
        String document = """
                query findOneAuthor($id: ID!) {
                  findOneAuthor(id: $id) {
                    id
                    lastName
                    firstName
                  }
                }
                """;

        // WHEN | THEN
        graphQlTester.document(document)
                .variable("id", 1)
                .execute()
                .path("findOneAuthor")
                .entity(Author.class)
                .satisfies(author -> {
                    assertEquals(1, author.id());
                    assertEquals(FIRST_AUTHOR, author.fullName());
                });
    }

    @Test
    void createAuthor() {
        // GIVEN
        // language=GraphQL
        String document = """
                mutation createAuthor($firstName: String!, $lastName: String!) {
                  createAuthor(firstName: $firstName, lastName: $lastName) {
                    id
                    lastName
                    firstName
                  }
                }
                """;
        int currentAuthorRepositorySize = authorRepository.findAll().size();

        // WHEN | THEN
        graphQlTester.document(document)
                .variable("firstName", "Robert")
                .variable("lastName", "Martin")
                .execute()
                .path("createAuthor")
                .entity(Author.class)
                .satisfies(author -> {
                    assertEquals(4, author.id());
                    assertEquals("Robert Martin", author.fullName());
                });
        assertEquals(currentAuthorRepositorySize + 1, authorRepository.findAll().size());
    }

    @Test
    void updateAuthor() {
        // GIVEN
        // language=GraphQL
        String document = """
                mutation updateAuthor($id: ID!, $firstName: String!, $lastName: String!) {
                  updateAuthor(id: $id, firstName: $firstName, lastName: $lastName) {
                    id
                    lastName
                    firstName
                  }
                }
                """;
        Author currentAuthor = authorRepository.findById(1).orElseThrow();

        // WHEN | THEN
        graphQlTester.document(document)
                .variable("id", 1)
                .variable("firstName", "Robert")
                .variable("lastName", "Martin")
                .execute()
                .path("updateAuthor")
                .entity(Author.class)
                .satisfies(author -> {
                    assertEquals(1, author.id());
                    assertEquals("Robert Martin", author.fullName());
                    assertNotEquals(currentAuthor.firstName(), author.firstName());
                    assertNotEquals(currentAuthor.lastName(), author.lastName());
                });
    }

    @Test
    void deleteAuthor() {
        // GIVEN
        // language=GraphQL
        String document = """
                mutation deleteAuthor($id: ID!) {
                  deleteAuthor(id: $id) {
                    id
                    lastName
                    firstName
                  }
                }
                """;
        int currentAuthorRepositorySize = authorRepository.findAll().size();

        // WHEN | THEN
        assertDoesNotThrow(() -> authorRepository.findByName(FIRST_AUTHOR));

        graphQlTester.document(document)
                .variable("id", 1)
                .executeAndVerify();

        assertEquals(currentAuthorRepositorySize - 1, authorRepository.findAll().size());
        assertThrows(RuntimeException.class, () -> authorRepository.findByName(FIRST_AUTHOR));
    }
}
