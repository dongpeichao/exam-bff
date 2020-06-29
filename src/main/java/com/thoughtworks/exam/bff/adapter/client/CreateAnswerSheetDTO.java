package com.thoughtworks.exam.bff.adapter.client;

import lombok.Data;

import java.util.List;

@Data
public class CreateAnswerSheetDTO {
    private String answerSheetId;

    private String examinationId;

    private int duration;

    private List<CreateExaminationCommand.BlankQuiz> quizzes;
}
