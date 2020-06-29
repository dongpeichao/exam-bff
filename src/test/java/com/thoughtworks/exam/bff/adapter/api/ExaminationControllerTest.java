package com.thoughtworks.exam.bff.adapter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.exam.bff.adapter.client.CreateAnswerSheetDTO;
import com.thoughtworks.exam.bff.adapter.client.CreateExaminationCommand;
import com.thoughtworks.exam.bff.adapter.client.CreateExaminationCommand.BlankQuiz;
import com.thoughtworks.exam.bff.adapter.client.SubmitAnswerCommand;
import com.thoughtworks.exam.bff.adapter.client.SubmitAnswerSheetDTO;
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

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureStubRunner(repositoryRoot = "stubs://file:///Users/peichao.dong/Documents/projects/dpc/sbux_jr/exam-service-stub/src/test/resources/contracts",
        ids = "com.thoughtworks:exam-service-stubs:8100", generateStubs = true, stubsMode = StubRunnerProperties.StubsMode.REMOTE)
@AutoConfigureMockMvc
class ExaminationControllerTest {
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
        CreateExaminationCommand createExaminationCommand = CreateExaminationCommand.builder()
                .teacherId("9043inol9f4ifnflmakmfdas09fd4ifnflma")
                .paperId("9043ino9f4if-flmakttfdas09fd4-fnflma")
                .duration(120)
                .quizzes(Arrays.asList(new BlankQuiz[]{
                        BlankQuiz.builder().id("9043inol9f4if-flmakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().id("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().id("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().id("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build(),
                        BlankQuiz.builder().id("9043inol9f3rf-fldakmfdas09fd4-fnflma").score(10).build()
                }))
                .build();
        String json = objectMapper.writeValueAsString(createExaminationCommand);
        ResultActions resultActions = mockMvc.perform(post("/examinations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(responseString).matches("[a-zA-Z-0-9]{36}");
    }

    @Test
    public void should_create_answer_sheet_successfully() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/examinations/9idk4-lokfu-jr874j3-h8d9j4-hor82kd77/answer-sheets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseString = resultActions.andReturn().getResponse().getContentAsString();

        CreateAnswerSheetDTO response = objectMapper.readValue(responseString, CreateAnswerSheetDTO.class);
        assertThat(response.getAnswerSheetId()).matches("[a-zA-Z-0-9]{36}");
    }

    @Test
    public void should_submit_answer_sheet_successfully() throws Exception {
        SubmitAnswerCommand submitAnswerCommand = SubmitAnswerCommand.builder()
                .studentId("9043inol9f4if-flmakmfdas09fd4-fnflma")
                .answer("a,b,c")
                .startTime(LocalDateTime.parse("2020-06-29T12:00:00"))
                .submitTime(LocalDateTime.parse("2020-06-29T13:15:00"))
                .build();
        ResultActions resultActions = mockMvc.perform(put("/examinations/9idk4-lokfu-jr874j3-h8d9j4-hor82kd77/answer-sheet/9idk4-lokfu-jr874j3-u8d9j4-hor82kd77")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(submitAnswerCommand)))
                .andExpect(status().isOk());

        String responseString = resultActions.andReturn().getResponse().getContentAsString();

        SubmitAnswerSheetDTO response = objectMapper.readValue(responseString, SubmitAnswerSheetDTO.class);
        assertThat(response.getAnswer()).isEqualTo("a,b,c");
    }

    @Test
    public void should_submit_answer_sheet_fail_when_expired() throws Exception {
        SubmitAnswerCommand submitAnswerCommand = SubmitAnswerCommand.builder()
                .studentId("9043inol9f4if-flmakmfdas09fd4-fnflma")
                .answer("a,b,c")
                .startTime(LocalDateTime.parse("2020-06-29T12:00:00"))
                .submitTime(LocalDateTime.parse("2020-06-29T15:00:00"))
                .build();
        mockMvc.perform(put("/examinations/9idk4-lokfu-jr874j3-h8d9j4-hor82kd77/answer-sheet/9idk4-lokfu-jr874j3-u8d9j4-hor82kd77")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(submitAnswerCommand)))
                .andExpect(status().isBadRequest());
    }
}