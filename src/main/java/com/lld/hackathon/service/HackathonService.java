package com.lld.hackathon.service;

import com.lld.hackathon.entity.Problem;
import com.lld.hackathon.entity.ProblemMetrics;
import com.lld.hackathon.entity.Tag;
import com.lld.hackathon.entity.User;
import com.lld.hackathon.enums.ProblemDifficulty;
import com.lld.hackathon.enums.SubmissionStatus;
import com.lld.hackathon.exceptions.UserNotFoundException;

import java.util.List;

public interface HackathonService {
    void addUser(User user);

    void addProblem(Problem problem);

    List<Problem> solve(User user, Problem problem, SubmissionStatus status, Long timeTaken) throws UserNotFoundException;

    List<Problem> fetchSolvedProblems(User user) throws UserNotFoundException;

    User getLeader();

    List<Problem> getTopNProblems(int n);

    List<Problem> getNRecommendedProblems(Problem problem, int n);

    // Fetch problems based on difficulty level
    List<ProblemMetrics> fetchProblems(ProblemDifficulty difficulty);

    // Fetch problems based on increasing value of score
    List<ProblemMetrics> fetchProblems(Boolean scoreIncreasing);

    // Fetch problems based on list of tags
    List<ProblemMetrics> fetchProblems(List<Tag> tags);

    void likeProblem(User user, Problem problem);

    void disLikeProblem(User user, Problem problem);
}
