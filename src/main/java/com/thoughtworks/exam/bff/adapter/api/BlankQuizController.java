package com.thoughtworks.exam.bff.adapter.api;

import com.thoughtworks.exam.bff.adapter.client.BlankQuizClient;
import com.thoughtworks.exam.bff.adapter.client.BlankQuizDTO;
import com.thoughtworks.exam.bff.adapter.client.CreateQuizCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/quizzes")
public class BlankQuizController {
    private final BlankQuizClient blankQuizClient;


    public BlankQuizController(BlankQuizClient blankQuizClient) {
        this.blankQuizClient = blankQuizClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateQuizCommand command) {
        return blankQuizClient.create(command);
    }

    @PutMapping("/{blankQuizId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable("blankQuizId") String blankQuizId, @RequestBody CreateQuizCommand command) {
        blankQuizClient.update(blankQuizId, command);
    }
}