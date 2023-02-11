package com.lld.hackathon.entity;

import com.lld.hackathon.enums.ProblemDifficulty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Problem {
    private String id;
    private String description;
    private List<Tag> tags;
    private ProblemDifficulty difficultyLevel;
    private Long score;

    public Problem(String id, String description, List<Tag> tags, ProblemDifficulty difficultyLevel, Long score) {
        this.id = id;
        this.description = description;
        this.tags = tags;
        this.difficultyLevel = difficultyLevel;
        this.score = score;
    }
}
