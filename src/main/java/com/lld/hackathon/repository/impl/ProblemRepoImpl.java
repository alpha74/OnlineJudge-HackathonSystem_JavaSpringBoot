package com.lld.hackathon.repository.impl;

import com.lld.hackathon.entity.Problem;
import com.lld.hackathon.repository.ProblemRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProblemRepoImpl implements ProblemRepo {

    private HashMap<String, Problem> problemHashMap;

    public ProblemRepoImpl() {
        this.problemHashMap = new HashMap<>();
    }

    @Override
    public void addProblem(Problem problem) {
        problemHashMap.put(problem.getId(), problem);
    }

    @Override
    public List<Problem> getAll() {
        List<Problem> problemList = new ArrayList<>() ;

        for(String key : problemHashMap.keySet()) {
            problemList.add(problemHashMap.get(key));
        }

        return problemList;
    }
}
