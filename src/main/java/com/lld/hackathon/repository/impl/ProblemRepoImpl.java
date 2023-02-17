package com.lld.hackathon.repository.impl;

import com.lld.hackathon.entity.Problem;
import com.lld.hackathon.entity.Tag;
import com.lld.hackathon.repository.ProblemRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ProblemRepoImpl implements ProblemRepo {

    private HashMap<String, Problem> problemHashMap;
    private HashMap<Tag, HashSet<Problem>> tagProblemHashMap;

    public ProblemRepoImpl() {
        this.problemHashMap = new HashMap<>();
        this.tagProblemHashMap = new HashMap<>();
    }

    @Override
    public void addProblem(Problem problem) {
        problemHashMap.put(problem.getId(), problem);

        List<Tag> problemTags = problem.getTags();

        // Add problem for each tag
        for(Tag tag : problemTags) {
            // If tag already exists
            if(tagProblemHashMap.containsKey(tag)) {
                HashSet<Problem> problemSet = tagProblemHashMap.get(tag);
                problemSet.add(problem);

                tagProblemHashMap.put(tag, problemSet);
            } else {
                HashSet<Problem> problemSet = new HashSet<>();
                problemSet.add(problem);

                tagProblemHashMap.put(tag, problemSet);
            }
        }
    }

    @Override
    public List<Problem> getAll() {
        List<Problem> problemList = new ArrayList<>() ;

        for(String key : problemHashMap.keySet()) {
            problemList.add(problemHashMap.get(key));
        }

        return problemList;
    }

    @Override
    public Problem getById(String id) {
        if(problemHashMap.get(id) != null)
            return problemHashMap.get(id);
        return null;
    }

    @Override
    public List<Problem> getNProblemsByTag(Problem problem, int n) {
        HashSet<Problem> resultSet = new HashSet<>();

        for(Tag tag: problem.getTags()) {
            HashSet<Problem> tagProblemSet = tagProblemHashMap.get(tag);

            for(Problem tagProblem : tagProblemSet) {
                if(!tagProblem.getId().equals(problem.getId())) {
                    resultSet.add(tagProblem);

                    if(n == resultSet.size()) {
                        return resultSet.stream().toList();
                    }
                }
            }
        }

        return resultSet.stream().toList();
    }
}
