package it.tcgroup.oldwar.util;

import it.tcgroup.oldwar.controller.payload.request.BookRequest;
import it.tcgroup.oldwar.controller.payload.response.BookResponse;
import it.tcgroup.oldwar.service.model.Book;

public class ClassCoverter {

    public static BookResponse BookRequestToBookResponse(BookRequest request){
        if(request==null){
            return null;
        }
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(request.getId());
        bookResponse.setAuthor(request.getAuthor());
        bookResponse.setTitle(request.getTitle());
        bookResponse.setReleaseDate(request.getReleaseDate());
        bookResponse.setCode(request.getCode());
        bookResponse.setVote(request.getVote());
        bookResponse.setPrice(request.getPrice());
        return bookResponse;
    }

    public static BookResponse BookToBookResponse(Book book){
        if(book==null){
            return null;
        }
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setReleaseDate(book.getReleaseDate());
        bookResponse.setCode(book.getCode());
        bookResponse.setVote(book.getVote());
        bookResponse.setPrice(book.getPrice());
        return bookResponse;
    }

    public static Book BookRequestToBook(BookRequest request){
        if(request==null){
            return null;
        }
        Book book = new Book();
        book.setId(request.getId());
        book.setAuthor(request.getAuthor());
        book.setTitle(request.getTitle());
        book.setReleaseDate(request.getReleaseDate());
        book.setCode(request.getCode());
        book.setVote(request.getVote());
        book.setPrice(request.getPrice());
        return book;
    }
}
