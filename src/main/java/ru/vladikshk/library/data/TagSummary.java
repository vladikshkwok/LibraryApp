package ru.vladikshk.library.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagSummary {
    private String tagName;
    private int bookCount;
    private long authorCount;


}
