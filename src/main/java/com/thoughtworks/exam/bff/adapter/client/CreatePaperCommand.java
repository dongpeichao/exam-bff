
package com.thoughtworks.exam.bff.adapter.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreatePaperCommand {
    private String teacherId;
    private List<BlankQuiz> quizzes;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlankQuiz {
        private String quizId;
        private Integer score;
    }
}
