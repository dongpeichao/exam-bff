package com.thoughtworks.exam.bff.adapter.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlankPaperDTO {
    private String paperId;

    @Override
    public String toString() {
        return paperId;
    }
}
