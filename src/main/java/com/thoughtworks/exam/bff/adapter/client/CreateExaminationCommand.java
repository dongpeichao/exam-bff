package com.thoughtworks.exam.bff.adapter.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateExaminationCommand {
    private String teacherId;
    private String paperId;
    private int duration;
    private List<BlankQuiz> quizzes;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlankQuiz {
        private String id;
        private Integer score;
    }
}
