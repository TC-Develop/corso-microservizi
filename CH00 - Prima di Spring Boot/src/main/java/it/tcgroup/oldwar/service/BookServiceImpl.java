package it.tcgroup.oldwar.service;

import it.tcgroup.oldwar.service.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDAO bookDAO;

    @Transactional( isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public List<Book> getAll(){
        List<Book> bookList = bookDAO.getAll();
        return bookList;
    }

    @Transactional( isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Book getById(UUID id){
        Book book = bookDAO.getById(id);
        return book;
    }

    @Transactional( isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Book save(Book book) {
        book = bookDAO.save(book);
        return book;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Book edit(Book book, UUID id) {
        book.setId(id);
        bookDAO.edit(book);
        return book;
    }


}
