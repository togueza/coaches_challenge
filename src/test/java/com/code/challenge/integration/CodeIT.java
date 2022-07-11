package com.code.challenge.integration;

import com.code.challenge.CodeApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeApplication.class)
public class CodeIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenSendingBase64Value_shouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/code/3")
                .content(Base64.getEncoder().encodeToString("test1".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }


    @Test
    public void whenDiffEquals_shouldReturnOkAndEquals() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/code/3")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertEquals("{\"result\":\"FIND\",\"code\":[{\"id\":3,\"Name\":test,\"LastName\":test}]}", result.getResponse().getContentAsString());
    }
}
