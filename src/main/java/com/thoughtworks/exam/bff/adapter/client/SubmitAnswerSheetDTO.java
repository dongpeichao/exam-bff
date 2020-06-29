package com.thoughtworks.exam.bff.adapter.client;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmitAnswerSheetDTO {
    private String answerSheetId;
    private String examinationId;
    private String studentId;
    private String answer;
    private LocalDateTime starTime;
    private LocalDateTime submitTime;
}
