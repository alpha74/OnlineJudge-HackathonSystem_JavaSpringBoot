package com.lld.hackathon.repository;

import com.lld.hackathon.entity.Submission;
import com.lld.hackathon.entity.User;

import java.util.List;

public interface SubmissionRepo {
    public List<Submission> findByUser(User user);

    public void addSubmission(User user, Submission submission);
}
