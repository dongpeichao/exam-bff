package com.thoughtworks.exam.bff.adapter.api;

import com.thoughtworks.exam.bff.adapter.client.*;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/papers")
public class BlankPaperController {
    private final BlankPaperClient blankPaperClient;
    private final BlankQuizClient blankQuizClient;


    public BlankPaperController(BlankPaperClient blankPaperClient, BlankQuizClient blankQuizClient) {
        this.blankPaperClient = blankPaperClient;
        this.blankQuizClient = blankQuizClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreatePaperCommand command) {
        return blankPaperClient.create(command);
    }

    @GetMapping("/{paperId}")
    @ResponseStatus(HttpStatus.OK)
    public QueryPaperDTO query(@PathVariable("paperId") String paperId) {
        QueryPaperDTO queryPaperDTO = blankPaperClient.query(paperId);
        if(null != queryPaperDTO && !CollectionUtils.isEmpty(queryPaperDTO.getQuizzes())) {
            queryPaperDTO.getQuizzes().forEach(queryQuizDTO -> {
                QueryQuizDTO quiz = blankQuizClient.query(queryQuizDTO.getId());
                if(null == quiz) {
                    return;
                }
                queryQuizDTO.setQuestion(quiz.getQuestion());
                queryQuizDTO.setReferenceAnswer(quiz.getReferenceAnswer());
                queryQuizDTO.setTeacherId(quiz.getTeacherId());
            });
        }

        return queryPaperDTO;
    }
}