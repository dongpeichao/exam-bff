package com.thoughtworks.exam.bff.adapter.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
}
