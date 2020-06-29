package com.thoughtworks.exam.bff.adapter.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class ExaminationClient {
    @Value("${examinationService.host}")
    private String paperHost;

    @Value("${examinationService.port}")
    private String paperPort;

    private RestTemplate restTemplate;

    public ExaminationClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String create(CreateExaminationCommand createExaminationCommand) {
        return restTemplate.postForObject(paperHost + ":" + paperPort + "/examinations",
                createExaminationCommand, ExaminationDTO.class).toString();
    }

    public CreateAnswerSheetDTO createAnswerSheet(String examinationId) {
        return restTemplate.postForObject(paperHost + ":" + paperPort + "/examinations/" + examinationId + "/answer-sheets",
                null, CreateAnswerSheetDTO.class);
    }

    public SubmitAnswerSheetDTO submitAnswer(String examinationId, String answerId, SubmitAnswerCommand submitAnswerCommand) {
        String url = paperHost + ":" + paperPort + "/examinations/" + examinationId + "/answer-sheet/" + answerId;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("ContentType", Arrays.asList("application/json"));
        HttpEntity<SubmitAnswerCommand> httpEntity = new HttpEntity(submitAnswerCommand, headers);
        ResponseEntity<SubmitAnswerSheetDTO> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,  httpEntity, SubmitAnswerSheetDTO.class);
        return responseEntity.getBody();
    }
}
