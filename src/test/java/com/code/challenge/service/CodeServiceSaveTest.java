package com.code.challenge.service;

import com.code.challenge.exception.NameAlreadyLoadedException;
import com.code.challenge.domain.Code;
import com.code.challenge.model.CodeResultEnum;
import com.code.challenge.repository.CodeRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Base64;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CodeServiceSaveTest {

    @Spy
    @InjectMocks
    private CodeService codeService;

    @Mock
    private CodeRepository codeRepository;

    @Test
    public void whenNotFound() throws NameAlreadyLoadedException {
        Code code = new Code(123L, "test", null);

        doReturn(Optional.empty()).when(codeRepository).findById(123L);

        codeService.save(123L, Base64.getEncoder().encodeToString("test".getBytes()), CodeResultEnum.NOT_FIND);

        verify(codeRepository, times(1)).save(code);
    }

    @Test(expected = NameAlreadyLoadedException.class)
    public void whenFoundButAlreadyHaveNameValueOnNull_shouldThrowNameAlreadyLoadedException() throws NameAlreadyLoadedException {
        Code code = new Code(123L, "test", null);

        doReturn(Optional.of(code)).when(codeRepository).findById(123L);

        codeService.save(123L, Base64.getEncoder().encodeToString("test".getBytes()), CodeResultEnum.FIND);
    }

    @Test(expected = NameAlreadyLoadedException.class)
    public void whenFoundButAlreadyHaveLastNameValueOnNull_shouldThrowNameAlreadyLoadedException() throws NameAlreadyLoadedException {
        Code code = new Code(123L, null, "test");

        doReturn(Optional.of(code)).when(codeRepository).findById(123L);

        codeService.save(123L, Base64.getEncoder().encodeToString("test".getBytes()), CodeResultEnum.FIND);
    }

    @Test
    public void whenFoundAndNameNull_shouldAddValue() throws NameAlreadyLoadedException {
        Code code = new Code(123L, null, "test");

        doReturn(Optional.of(code)).when(codeRepository).findById(123L);

        codeService.save(123L, Base64.getEncoder().encodeToString("test".getBytes()), CodeResultEnum.FIND);

        verify(codeRepository, times(1)).save(new Code(123L, "test", "test"));
    }

    @Test
    public void whenFoundAndLastNameNull_shouldAddValue() throws NameAlreadyLoadedException {
        Code code = new Code(123L, "test", null);

        doReturn(Optional.of(code)).when(codeRepository).findById(123L);

        codeService.save(123L, Base64.getEncoder().encodeToString("test".getBytes()), CodeResultEnum.FIND);

        verify(codeRepository, times(1)).save(new Code(123L, "test", "test"));
    }

}