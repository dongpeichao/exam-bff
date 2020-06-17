package com.thoughtworks.exam.bff.adapter.api;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.thoughtworks.exam.bff.adapter.client.CreateQuizCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureStubRunner
@AutoConfigureMockMvc
class BlankQuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void should_create_quizzes_successfully() throws Exception {
        CreateQuizCommand createQuizCommand = CreateQuizCommand.builder().score(5)
                .question("防腐测试是什么？")
                .referenceAnswer("防腐测试是为了及时预警第三方API的破坏，防止因反馈的缺失而继续发生腐化的测试")
                .teacherId("9043inol9f4ifnflmakmfdas09fd4ifnflma")
                .build();
        ResultActions resultActions = mockMvc.perform(post("/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(createQuizCommand)))
                .andExpect(status().isCreated());
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(responseString).matches("[a-zA-Z-0-9]{36}");
    }

    @Test
    public void should_update_quizzes_successfully() throws Exception {
        CreateQuizCommand createQuizCommand = CreateQuizCommand.builder().score(5)
                .question("防腐测试是什么？")
                .referenceAnswer("防腐测试是为了及时预警第三方API的破坏，防止因反馈的缺失而继续发生腐化的测试")
                .teacherId("9043inol9f4ifnflmakmfdas09fd4ifnflma")
                .build();
        mockMvc.perform(put("/quizzes/" + "d18f752b-c34e-4be9-b7d1-766a618497f1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(createQuizCommand)))
                .andExpect(status().isNoContent());
    }

}