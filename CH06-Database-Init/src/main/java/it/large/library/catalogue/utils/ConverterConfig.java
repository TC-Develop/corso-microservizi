package it.large.library.catalogue.utils;

import it.large.library.catalogue.controller.payload.request.AuthorRequest;
import it.large.library.catalogue.controller.payload.request.BookRequest;
import it.large.library.catalogue.controller.payload.response.AuthorResponse;
import it.large.library.catalogue.controller.payload.response.BookResponse;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.model.BookModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Classe di conversione manuale degli oggetti
 */
public class ConverterConfig {

    public static BookModel converterBookRequestToBookModel(BookRequest bookRequest) {
        BookModel bookModel = new BookModel();
        bookModel.setTitle(bookRequest.getTitle());
        bookModel.setPrice(bookRequest.getPrice());
        bookModel.setAuthor(converterAuthorRequestToAuthorModel(bookRequest.getAuthor()));

        return bookModel;
    }

    public static AuthorModel converterAuthorRequestToAuthorModel(AuthorRequest authorRequest) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName(authorRequest.getName());
        authorModel.setBirthday(authorRequest.getBirthday());
        authorModel.setSurname(authorRequest.getSurname());
        authorModel.setDeathDay(authorRequest.getDeathDay());
        authorModel.setBirthCity(authorRequest.getBirthCity());

        return authorModel;
    }


    public static BookResponse converterBookModelToBookResponse(BookModel bookModel) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookId(bookModel.getBookId());
        bookResponse.setTitle(bookModel.getTitle());
        bookResponse.setPrice(bookModel.getPrice());
        bookResponse.setAuthor(converterAuthorModelToAuthorResponse(bookModel.getAuthor()));

        return bookResponse;
    }

    public static AuthorResponse converterAuthorModelToAuthorResponse(AuthorModel authorModel) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setAuthorId(authorModel.getAuthorId());
        authorResponse.setName(authorModel.getName());
        authorResponse.setSurname(authorModel.getSurname());
        authorResponse.setBirthday(authorModel.getBirthday());
        authorResponse.setDeathDay(authorModel.getDeathDay());
        authorResponse.setBirthCity(authorModel.getBirthCity());

        return authorResponse;
    }

    public static List<BookResponse> converterBookModelListToBookResponseList(List<BookModel> bookModelList) {
        BookResponse bookResponse = new BookResponse();
        List<BookResponse> bookResponseList = new ArrayList<>();
        for (BookModel bookModel: bookModelList) {
            bookResponse = converterBookModelToBookResponse(bookModel);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }


}