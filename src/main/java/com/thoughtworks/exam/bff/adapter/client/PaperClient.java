package com.thoughtworks.exam.bff.adapter.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaperClient {

    @Value("${paperService.host}")
    private String paperHost;

    @Value("${paperService.port}")
    private String paperPort;

    private RestTemplate restTemplate;

    public PaperClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String create(CreatePaperCommand createPaperCommand) {
        return restTemplate.postForObject( paperHost + ":" + paperPort + "/papers", createPaperCommand, PaperDTO.class).toString();
    }

    public QueryPaperDTO query(String paperId) {
        return restTemplate.getForObject( paperHost + ":" + paperPort + "/papers/" + paperId, QueryPaperDTO.class);
    }
}