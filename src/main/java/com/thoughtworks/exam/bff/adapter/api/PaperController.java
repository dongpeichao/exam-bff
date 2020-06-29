package com.thoughtworks.exam.bff.adapter.api;

import com.thoughtworks.exam.bff.adapter.client.*;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/papers")
public class PaperController {
    private final PaperClient paperClient;
    private final BlankQuizClient blankQuizClient;


    public PaperController(PaperClient paperClient, BlankQuizClient blankQuizClient) {
        this.paperClient = paperClient;
        this.blankQuizClient = blankQuizClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreatePaperCommand command) {
        return paperClient.create(command);
    }

    @GetMapping("/{paperId}")
    @ResponseStatus(HttpStatus.OK)
    public QueryPaperDTO query(@PathVariable("paperId") String paperId) {
        QueryPaperDTO queryPaperDTO = paperClient.query(paperId);
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