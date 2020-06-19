package com.thoughtworks.exam.bff.adapter.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class BlankPaperClient {

    @Value("${paperService.host}")
    private String paperHost;

    @Value("${paperService.port}")
    private String paperPort;

    private RestTemplate restTemplate;

    public BlankPaperClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String create(CreatePaperCommand createPaperCommand) {
        return restTemplate.postForObject( paperHost + ":" + paperPort + "/papers", createPaperCommand, BlankPaperDTO.class).toString();
    }

    public QueryPaperDTO query(String paperId) {
        return restTemplate.getForObject( paperHost + ":" + paperPort + "/papers/" + paperId, QueryPaperDTO.class);
    }
}