
package com.thoughtworks.exam.bff.adapter.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QueryQuizDTO {
    private String id;
    private String teacherId;
    private String question;
    private Integer score;
    private String referenceAnswer;
}
