package com.rst.debuggraphqlk8s.model;

public record Book(Integer id, String title, Integer pages, Rating rating, Author author) {
}
