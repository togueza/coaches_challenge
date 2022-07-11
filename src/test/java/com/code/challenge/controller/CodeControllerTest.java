package com.code.challenge.controller;

import com.code.challenge.domain.Code;
import com.code.challenge.exception.NameAlreadyLoadedException;
import com.code.challenge.model.CodeResult;
import com.code.challenge.model.CodeResultEnum;
import com.code.challenge.service.CodeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CodeController.class, secure = false)
public class CodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CodeService codeService;

    @Captor
    private ArgumentCaptor<CodeResultEnum> resultCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<String> valueCaptor;

    @Test
    public void save_whenValueIsNotBase64_shouldRespondException() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/code/123/store")
                .content("test not Base 64")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Not a Base64 encoded parameter", result.getResponse().getErrorMessage());
    }

    @Test
    public void save_whenAlreadyHaveValueForThatId_shouldRespondException() throws Exception {
        doThrow(new NameAlreadyLoadedException()).when(codeService).save(anyLong(), anyString(), any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/code/123/store")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Value already loaded to the informed direction for this id", result.getResponse().getErrorMessage());
    }

    @Test
    public void save_whenHappyPath_shouldCallServiceLayerWithCorrectValues() throws Exception {
        doNothing().when(codeService).save(idCaptor.capture(), valueCaptor.capture(), resultCaptor.capture());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/code/123/store")
                .content(Base64.getEncoder().encodeToString("test".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(204));

        assertEquals(Long.valueOf(123L), idCaptor.getValue());
        assertEquals("test", new String(Base64.getDecoder().decode(valueCaptor.getValue())));
        assertEquals(CodeResultEnum.FIND, resultCaptor.getValue());
    }


    @Test
    public void code_whenFind_shouldRespondResultFieldFind() throws Exception {
        Mockito.doReturn(new CodeResult(CodeResultEnum.FIND), new Code(3L, "test", "test")).when(codeService).find(123L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/code/123")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertEquals("{\"result\":\"FIND\",\"code\":[{\"id\":3,\"Name\":test,\"LastName\":test}]}", result.getResponse().getContentAsString());
    }

    @Test
    public void code_whenNotFind_shouldRespondResultFieldNotFind() throws Exception {
        doReturn(new CodeResult(CodeResultEnum.NOT_FIND), new Code(3L, "test", "test")).when(codeService).find(123L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/code/123")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertEquals("{\"result\":\"NOT_FIND\",\"code\":[{\"id\":3,\"Name\":test,\"LastName\":test}]}", result.getResponse().getContentAsString());
    }

}