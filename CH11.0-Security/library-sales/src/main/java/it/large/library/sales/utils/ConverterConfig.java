package it.large.library.sales.utils;

import it.large.library.sales.controller.payload.request.SaleRequest;
import it.large.library.sales.controller.payload.response.BookResponse;
import it.large.library.sales.controller.payload.response.SaleResponse;
import it.large.library.sales.document.BookDocument;
import it.large.library.sales.document.SaleDocument;
import it.large.library.sales.model.BookModel;
import it.large.library.sales.model.SaleModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Classe di conversione manuale degli oggetti

public class ConverterConfig {

    public static SaleModel convertRequestToModel(SaleRequest saleRequest) {
        SaleModel saleModel = new SaleModel();
        saleModel.setSaleId(saleRequest.getSaleId());

        List<BookModel> bookModels = new ArrayList<>();
        for (UUID bookId: saleRequest.getBooksIds()) {
            BookModel bookModel = new BookModel();
            bookModel.setBookId(bookId);
            bookModels.add(bookModel);
        }
        saleModel.setBooks(bookModels);

        return saleModel;
    }

    public static SaleDocument convertModelToDocument(SaleModel saleModel) {
        SaleDocument saleDocument = new SaleDocument();
        saleDocument.setSaleId(saleModel.getSaleId());
        saleDocument.setClientId(saleModel.getClientId());
        saleDocument.setAmount(saleModel.getAmount());
        saleDocument.setPurchaseDate(saleModel.getPurchaseDate().toInstant());

        List<BookDocument> bookDocuments = new ArrayList<>();
        for (BookModel bookModel: saleModel.getBooks()) {
            BookDocument bookDocument = new BookDocument();
            bookDocument.setBookId(bookModel.getBookId());
            bookDocument.setTitle(bookModel.getTitle());
            bookDocument.setPrice(bookModel.getPrice());
            bookDocuments.add(bookDocument);
        }
        saleDocument.setBooks(bookDocuments);
        return saleDocument;
    }

    public static SaleModel convertDocumentToModel(SaleDocument saleDocument) {
        SaleModel saleModel = new SaleModel();
        saleModel.setSaleId(saleDocument.getSaleId());
        saleModel.setClientId(saleDocument.getClientId());
        saleModel.setAmount(saleDocument.getAmount());
        saleModel.setPurchaseDate(Timestamp.from(saleDocument.getPurchaseDate()));

        List<BookModel> bookModels = new ArrayList<>();
        for (BookDocument bookDocument : saleDocument.getBooks()) {
            BookModel bookModel = new BookModel();
            bookModel.setBookId(bookDocument.getBookId());
            bookModel.setTitle(bookDocument.getTitle());
            bookModel.setPrice(bookDocument.getPrice());
            bookModels.add(bookModel);
        }
        saleModel.setBooks(bookModels);

        return saleModel;
    }

    public static SaleResponse convertModelToResponse(SaleModel saleModel) {
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(saleModel.getSaleId());
        saleResponse.setClientId(saleModel.getClientId());
        saleResponse.setPurchaseDate(saleModel.getPurchaseDate());
        saleResponse.setAmount(saleModel.getAmount());

        List<BookResponse> bookResponses = new ArrayList<>();
        for (BookModel bookModel : saleModel.getBooks()) {
            bookResponses.add(convertBookModelToResponse(bookModel));
        }
        saleResponse.setBooks(bookResponses);

        return saleResponse;
    }

    public static BookResponse convertBookModelToResponse(BookModel bookModel) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookId(bookModel.getBookId());
        bookResponse.setTitle(bookModel.getTitle());
        bookResponse.setPrice(bookModel.getPrice());
        return bookResponse;
    }

    public static List<SaleModel> convertDocumentListToModelList(List<SaleDocument> saleDocuments) {
        List<SaleModel> saleModels = new ArrayList<>();
        for (SaleDocument saleDocument : saleDocuments) {
            SaleModel saleModel = convertDocumentToModel(saleDocument);
            saleModels.add(saleModel);
        }
        return saleModels;
    }

    public static List<SaleResponse> convertModelListToResponseList(List<SaleModel> saleModels) {
        List<SaleResponse> saleResponses = new ArrayList<>();
        for (SaleModel saleModel : saleModels) {
            SaleResponse saleResponse = convertModelToResponse(saleModel);
            saleResponses.add(saleResponse);
        }
        return saleResponses;
    }


}