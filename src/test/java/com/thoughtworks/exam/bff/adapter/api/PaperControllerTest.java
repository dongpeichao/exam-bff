package com.thoughtworks.exam.bff.adapter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.thoughtworks.exam.bff.adapter.client.CreatePaperCommand;
import com.thoughtworks.exam.bff.adapter.client.CreatePaperCommand.BlankQuiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureStubRunner(repositoryRoot = "stubs://file:///Users/peichao.dong/Documents/projects/dpc/sbux_jr/exam-quiz-stub/src/test/resources/contracts",
        ids = "com.thoughtworks:exam-quiz-service-stubs:8100", generateStubs = true, stubsMode = StubRunnerProperties.StubsMode.REMOTE)
@AutoConfigureMockMvc
class PaperControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void should_create_papers_successfully() throws Exception {
        CreatePaperCommand createPaperCommand = CreatePaperCommand.builder()
                .teacherId("9043inol9f4ifnflmakmfdas09fd4ifnflma")
                .quizzes(Arrays.asList(new BlankQuiz[]{
                        BlankQuiz.builder().quizId("9043inol9f4if-flmakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build()
                }))
                .build();
        String json = objectMapper.writeValueAsString(createPaperCommand);
        ResultActions resultActions = mockMvc.perform(post("/papers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(responseString).matches("[a-zA-Z-0-9]{36}");
    }

    @Test
    public void should_create_papers_fail_when_quiz_size_less_than_5() throws Exception {
        CreatePaperCommand createPaperCommand = CreatePaperCommand.builder()
                .teacherId("9043inol9f4ifnflmakmfdas09fd4ifnflma")
                .quizzes(Arrays.asList(new BlankQuiz[]{
                        BlankQuiz.builder().quizId("9043inol9f4if-flmakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().quizId("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build()
                }))
                .build();
        mockMvc.perform(post("/papers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(createPaperCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_query_papers_successfully() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/papers/9043inol9f4if-flmakmfdas09fd4-fnflma")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

}