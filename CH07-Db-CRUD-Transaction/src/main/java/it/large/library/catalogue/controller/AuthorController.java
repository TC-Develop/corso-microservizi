package it.large.library.catalogue.controller;

import it.large.library.catalogue.controller.payload.group.PostValidation;
import it.large.library.catalogue.controller.payload.request.AuthorRequest;
import it.large.library.catalogue.controller.payload.response.AuthorResponse;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.service.AuthorService;
import it.large.library.catalogue.utils.ConverterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("catalogue/api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @PostMapping(path = "")
    public ResponseEntity<AuthorResponse> add(
            @Validated(PostValidation.class) @RequestBody AuthorRequest authorRequest
    ) {
        AuthorModel authorModel = ConverterConfig.converterAuthorRequestToAuthorModel(authorRequest);
        authorModel = authorService.add(authorModel);
        AuthorResponse authorResponse = ConverterConfig.converterAuthorModelToAuthorResponse(authorModel);

        return ResponseEntity.ok(authorResponse);
    }


    @PutMapping(path = "/{author_id}")
    public ResponseEntity<AuthorResponse> edit(
            @Validated(PostValidation.class) @RequestBody AuthorRequest authorRequest,
            @PathVariable(name = "author_id") UUID authorId
            ) {
        AuthorModel authorModel = ConverterConfig.converterAuthorRequestToAuthorModel(authorRequest);
        authorModel.setAuthorId(authorId);
        authorModel = authorService.edit(authorModel);
        AuthorResponse authorResponse = ConverterConfig.converterAuthorModelToAuthorResponse(authorModel);

        return ResponseEntity.ok(authorResponse);
    }


    @DeleteMapping(path = "/{author_id}")
    public ResponseEntity<Boolean> remove(
            @PathVariable(name = "author_id") UUID authorId
    ) {
        authorService.remove(authorId);

        return ResponseEntity.ok(Boolean.TRUE);
    }


}