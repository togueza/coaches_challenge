package com.code.challenge.service;

import com.code.challenge.model.CodeResult;
import com.code.challenge.model.CodeResultEnum;
import com.code.challenge.domain.Code;
import com.code.challenge.exception.NameNotLoadedException;
import com.code.challenge.repository.CodeRepository;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CodeServiceCodeTest {

    @Spy
    @InjectMocks
    private CodeService codeService;

    @Mock
    private CodeRepository codeRepository;

    @Test(expected = EntityNotFoundException.class)
    public void whenNoFound_shouldThrowEntityNotFoundException() throws NameNotLoadedException {
        codeService.find(123L);
    }

    @Test(expected = NameNotLoadedException.class)
    public void whenLastNameIsEmpty_shouldThrowNameNotLoadedException() throws NameNotLoadedException {
        doReturn(Optional.of(new Code(123L, "test", null))).when(codeRepository).findById(123L);
        codeService.find(123L);
    }

    @Test(expected = NameNotLoadedException.class)
    public void whenNameEmpty_shouldThrowNameNotLoadedException() throws NameNotLoadedException {
        doReturn(Optional.of(new Code(123L, null, "test"))).when(codeRepository).findById(123L);
        codeService.find(123L);
    }

    @Test
    public void whenNotFindData_shouldReturnFINDOne() throws NameNotLoadedException {
        doReturn(Optional.of(new Code(123L, "test", "test"))).when(codeRepository).findById(123L);
        CodeResult result = codeService.find(123L);
        assertThat(result, hasProperty("result", equalTo(CodeResultEnum.FIND)));
    }

    @Test
    public void whenFindData_shouldReturnFINDTwo() throws NameNotLoadedException {
        doReturn(Optional.of(new Code(123L, "testing", "test"))).when(codeRepository).findById(123L);
        CodeResult result = codeService.find(123L);
        assertThat(result, hasProperty("result", equalTo(CodeResultEnum.NOT_FIND)));
    }

}
