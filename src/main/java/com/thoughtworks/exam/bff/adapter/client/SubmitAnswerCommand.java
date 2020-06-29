package com.thoughtworks.exam.bff.adapter.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SubmitAnswerCommand {
    private String studentId;
    private String answer;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
}
