package it.large.library.catalogue.utils;

import it.large.library.catalogue.controller.payload.request.AuthorRequest;
import it.large.library.catalogue.controller.payload.request.BookRequest;
import it.large.library.catalogue.controller.payload.response.AuthorResponse;
import it.large.library.catalogue.controller.payload.response.BookResponse;
import it.large.library.catalogue.controller.payload.response.CategoryResponse;
import it.large.library.catalogue.entity.AuthorEntity;
import it.large.library.catalogue.entity.BookEntity;
import it.large.library.catalogue.entity.CategoryEntity;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/*
 * Classe di conversione manuale degli oggetti
 * NB Aggiungiamo da adesso anche le liste di Category e Book
 */
public class ConverterConfig {

    public static BookModel converterBookRequestToBookModel(BookRequest bookRequest) {
        BookModel bookModel = new BookModel();
        bookModel.setTitle(bookRequest.getTitle());
        bookModel.setPrice(bookRequest.getPrice());
        bookModel.setAuthor(convertAuthorRequestToAuthorModel(bookRequest.getAuthor()));
        bookModel.setCategories(convertCategoryRequestsToCategoryModels(bookRequest.getCategoriesId()));

        return bookModel;
    }

    public static AuthorModel convertAuthorRequestToAuthorModel(AuthorRequest authorRequest) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName(authorRequest.getName());
        authorModel.setBirthday(authorRequest.getBirthday());
        authorModel.setSurname(authorRequest.getSurname());
        authorModel.setDeathDay(authorRequest.getDeathDay());
        authorModel.setBirthCity(authorRequest.getBirthCity());

        return authorModel;
    }

    public static List<CategoryModel> convertCategoryRequestsToCategoryModels(Set<UUID> categoriesIds) {
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (UUID categoryId : categoriesIds) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCategoryId(categoryId);
            // Popolare altre proprietà se necessario
            categoryModels.add(categoryModel);
        }
        return categoryModels;
    }

    public static BookResponse converterBookModelToBookResponse(BookModel bookModel) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookId(bookModel.getBookId());
        bookResponse.setTitle(bookModel.getTitle());
        bookResponse.setPrice(bookModel.getPrice());
        bookResponse.setAuthor(convertAuthorModelToAuthorResponse(bookModel.getAuthor()));
        bookResponse.setCategories(convertCategoryModelsToCategoryResponses(bookModel.getCategories()));

        return bookResponse;
    }

    public static AuthorResponse convertAuthorModelToAuthorResponse(AuthorModel authorModel) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setAuthorId(authorModel.getAuthorId());
        authorResponse.setName(authorModel.getName());
        authorResponse.setSurname(authorModel.getSurname());
        authorResponse.setBirthday(authorModel.getBirthday());
        authorResponse.setDeathDay(authorModel.getDeathDay());
        authorResponse.setBirthCity(authorModel.getBirthCity());

        return authorResponse;
    }

    public static List<CategoryResponse> convertCategoryModelsToCategoryResponses(List<CategoryModel> categoryModels) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (CategoryModel categoryModel : categoryModels) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(categoryModel.getCategoryId());
            categoryResponse.setCategoryId(categoryModel.getCategoryId());
            categoryResponse.setName(categoryModel.getName());
            categoryResponse.setDescription(categoryModel.getDescription());
            categoryResponse.setType(categoryModel.getType());
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    public static BookEntity convertBookModelToBookEntity(BookModel bookModel) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(bookModel.getBookId());
        bookEntity.setTitle(bookModel.getTitle());
        bookEntity.setPrice(bookModel.getPrice());
        bookEntity.setAuthor(convertAuthorModelToAuthorEntity(bookModel.getAuthor()));
        bookEntity.setCategories(convertCategoryModelsToCategoryEntities(bookModel.getCategories()));

        return bookEntity;
    }

    public static AuthorEntity convertAuthorModelToAuthorEntity(AuthorModel authorModel) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorId(authorModel.getAuthorId());
        authorEntity.setName(authorModel.getName());
        authorEntity.setSurname(authorModel.getSurname());
        authorEntity.setBirthday(authorModel.getBirthday());
        authorEntity.setDeathDay(authorModel.getDeathDay());
        authorEntity.setBirthCity(authorModel.getBirthCity());

        return authorEntity;
    }

    public static List<CategoryEntity> convertCategoryModelsToCategoryEntities(List<CategoryModel> categoryModels) {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (CategoryModel categoryModel : categoryModels) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryId(categoryModel.getCategoryId());
            categoryEntity.setName(categoryModel.getName());
            categoryEntity.setDescription(categoryModel.getDescription());
            categoryEntity.setType(categoryModel.getType());
            categoryEntities.add(categoryEntity);
        }
        return categoryEntities;
    }

    public static BookModel convertBookEntityToBookModel(BookEntity bookEntity) {
        BookModel bookModel = new BookModel();
        bookModel.setBookId(bookEntity.getBookId());
        bookModel.setTitle(bookEntity.getTitle());
        bookModel.setPrice(bookEntity.getPrice());
        bookModel.setAuthor(convertAuthorEntityToAuthorModel(bookEntity.getAuthor()));
        bookModel.setCategories(convertCategoryEntitiesToCategoryModels(bookEntity.getCategories()));

        return bookModel;
    }

    public static AuthorModel convertAuthorEntityToAuthorModel(AuthorEntity authorEntity) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setAuthorId(authorEntity.getAuthorId());
        authorModel.setName(authorEntity.getName());
        authorModel.setSurname(authorEntity.getSurname());
        authorModel.setBirthday(authorEntity.getBirthday());
        authorModel.setDeathDay(authorEntity.getDeathDay());
        authorModel.setBirthCity(authorEntity.getBirthCity());

        return authorModel;
    }

    public static List<CategoryModel> convertCategoryEntitiesToCategoryModels(List<CategoryEntity> categoryEntities) {
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCategoryId(categoryEntity.getCategoryId());
            categoryModel.setName(categoryEntity.getName());
            categoryModel.setDescription(categoryEntity.getDescription());
            categoryModel.setType(categoryEntity.getType());
            categoryModels.add(categoryModel);
        }
        return categoryModels;
    }

    public static CategoryModel convertCategoryEntityToCategoryModel(CategoryEntity categoryEntity) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId(categoryEntity.getCategoryId());
        categoryModel.setName(categoryEntity.getName());
        categoryModel.setDescription(categoryEntity.getDescription());
        categoryModel.setType(categoryEntity.getType());

        return categoryModel;
    }

    public static List<CategoryResponse> convertCategoryEntitiesToCategoryResponses(List<CategoryEntity> categoryEntities) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(categoryEntity.getCategoryId());
            // Popolare altre proprietà se necessario
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    public static List<BookResponse> convertBookModelListToBookResponseList(List<BookModel> bookModelList) {
        List<BookResponse> bookResponseList = new ArrayList<>();
        for (BookModel bookModel : bookModelList) {
            BookResponse bookResponse = converterBookModelToBookResponse(bookModel);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

    public static List<BookModel> convertBookEntitiesToBookModels(List<BookEntity> bookEntities) {
        List<BookModel> bookModels = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            BookModel bookModel = convertBookEntityToBookModel(bookEntity);
            bookModels.add(bookModel);
        }
        return bookModels;
    }
}