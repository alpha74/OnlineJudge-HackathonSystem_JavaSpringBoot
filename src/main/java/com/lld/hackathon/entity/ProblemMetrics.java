package com.lld.hackathon.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProblemMetrics {
    private Problem problem;
    private Long likesCount;
    private Long avgSolveTimeInMin;
    private List<User> solvedUsers;

    public ProblemMetrics(Problem problem) {
        this.problem = problem;
        this.likesCount = Long.valueOf(0);
        this.avgSolveTimeInMin = Long.valueOf(0);
        this.solvedUsers = new ArrayList<>();
    }

    public void successSubmission(User user, Long timeTaken) {
        this.solvedUsers.add(user);
        this.avgSolveTimeInMin = (this.avgSolveTimeInMin + timeTaken) / this.solvedUsers.size();
    }
}
