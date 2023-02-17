package com.lld.hackathon.repository;

import com.lld.hackathon.entity.Problem;
import com.lld.hackathon.entity.Tag;

import java.util.List;

public interface ProblemRepo {
    void addProblem(Problem problem);

    List<Problem> getAll();

    Problem getById(String id);

    List<Problem> getNProblemsByTag(Problem problem, int n);
}
