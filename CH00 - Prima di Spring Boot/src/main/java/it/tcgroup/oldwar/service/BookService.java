package it.tcgroup.oldwar.service;

import it.tcgroup.oldwar.service.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<Book> getAll();
    public Book getById(UUID id);
    Book save(Book book);
    Book edit(Book book, UUID id);
}
