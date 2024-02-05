package it.tcgroup.oldwar.util;

import it.tcgroup.oldwar.controller.payload.request.BookRequest;
import it.tcgroup.oldwar.controller.payload.response.BookResponse;

public class ClassCoverter {

    public static BookResponse BookRequestToBookResponse(BookRequest request){
        if(request==null){
            return null;
        }
        BookResponse bookResponse = new BookResponse();
        bookResponse.setAutor(request.getAutor());
        bookResponse.setTitle(request.getTitle());
        bookResponse.setReleaseDate(request.getReleaseDate());
        bookResponse.setCode(request.getCode());
        bookResponse.setVote(request.getVote());
        bookResponse.setPrice(request.getPrice());
        return bookResponse;
    }

}
