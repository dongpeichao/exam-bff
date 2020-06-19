
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
public class QueryPaperDTO {
    private String paperId;
    private String teacherId;
    private List<QueryQuizDTO> quizzes;
}
