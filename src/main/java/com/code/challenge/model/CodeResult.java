package com.code.challenge.model;

import com.code.challenge.domain.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class
CodeResult {

    @NonNull
    private CodeResultEnum result;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Code code;


}
