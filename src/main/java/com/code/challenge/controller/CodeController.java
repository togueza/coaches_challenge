package com.code.challenge.controller;

import com.code.challenge.exception.NameAlreadyLoadedException;
import com.code.challenge.model.CodeResult;
import com.code.challenge.model.CodeResultEnum;
import com.code.challenge.service.CodeService;
import com.code.challenge.exception.NameNotLoadedException;
import com.code.challenge.validation.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/code")
@Api(description = "Code API")
public class CodeController {

    @Autowired
    private CodeService codeService;


    @PostMapping(path = "/{id}/store")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Store value provided")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No content returned for successful savings"),
            @ApiResponse(code = 400, message = "A bad request will be thrown in case of the person already exists to the informed ID or if value received is not a Base64 encoded string")
    })
    public void savePersonInfo(@PathVariable("id") Long id,
                          @Valid @NotNull @NotBlank @Base64 @RequestBody String value) throws NameAlreadyLoadedException {
        codeService.save(id, value, CodeResultEnum.FIND);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Person values for the provided ID entity")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK returned for successful"),
            @ApiResponse(code = 400, message = "A bad request will be thrown in case of the person are not loaded for the informed ID"),
            @ApiResponse(code = 404, message = "Entity not found will be thrown in case no entity exists for the informed ID")
    })
    public CodeResult getPersonInfo(@PathVariable("id") Long id) throws NameNotLoadedException {
        return codeService.find(id);
    }
}
