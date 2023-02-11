package com.lld.hackathon.repository;

import com.lld.hackathon.entity.Problem;

import java.util.List;

public interface ProblemRepo {
    void addProblem(Problem problem);

    List<Problem> getAll();
}
