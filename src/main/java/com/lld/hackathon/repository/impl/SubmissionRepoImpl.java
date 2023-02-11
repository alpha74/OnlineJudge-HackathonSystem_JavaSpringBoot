package com.lld.hackathon.repository.impl;

import com.lld.hackathon.entity.Submission;
import com.lld.hackathon.entity.User;
import com.lld.hackathon.repository.SubmissionRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubmissionRepoImpl implements SubmissionRepo {
    private HashMap<User, List<Submission>> submissionHashMap;

    public SubmissionRepoImpl() {
        this.submissionHashMap = new HashMap<>();
    }

    @Override
    public List<Submission> findByUser(User user) {
        List<Submission> submissionList = new ArrayList<>();

        if( submissionHashMap.containsKey(user))
            submissionList = submissionHashMap.get(user);

        return submissionList;
    }

    @Override
    public void addSubmission(User user, Submission submission) {
        List<Submission> submissionList = new ArrayList<>();

        if( submissionHashMap.containsKey(user))
            submissionList = submissionHashMap.get(user);

        submissionList.add(submission);

        submissionHashMap.put(user, submissionList);
    }
}
