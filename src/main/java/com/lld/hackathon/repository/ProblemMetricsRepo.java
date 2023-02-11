package com.lld.hackathon.repository;

import com.lld.hackathon.entity.Problem;
import com.lld.hackathon.entity.ProblemMetrics;

import java.util.List;

public interface ProblemMetricsRepo {
    void addOrUpdateProblemMetrics(ProblemMetrics problemMetrics);

    ProblemMetrics fetchMetrics(Problem problem);

    List<ProblemMetrics> getAll();
}
