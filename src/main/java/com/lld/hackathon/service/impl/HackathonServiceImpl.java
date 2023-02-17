package com.lld.hackathon.service.impl;

import com.lld.hackathon.entity.*;
import com.lld.hackathon.enums.ProblemDifficulty;
import com.lld.hackathon.enums.SubmissionStatus;
import com.lld.hackathon.exceptions.UserNotFoundException;
import com.lld.hackathon.repository.ProblemMetricsRepo;
import com.lld.hackathon.repository.ProblemRepo;
import com.lld.hackathon.repository.SubmissionRepo;
import com.lld.hackathon.repository.UserRepo;
import com.lld.hackathon.service.HackathonService;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.lld.hackathon.constants.GlobalConstants.DISLIKE_ONE;
import static com.lld.hackathon.constants.GlobalConstants.LIKE_ONE;

public class HackathonServiceImpl implements HackathonService {
    private UserRepo userRepository;
    private ProblemRepo problemRepository;
    private ProblemMetricsRepo problemMetricsRepository;
    private SubmissionRepo submissionRepository;

    public HackathonServiceImpl(UserRepo userRepository,
                                ProblemRepo problemRepository,
                                ProblemMetricsRepo problemMetricsRepository,
                                SubmissionRepo submissionRepository) {
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.problemMetricsRepository = problemMetricsRepository;
        this.submissionRepository = submissionRepository;
    }


    @Override
    public void addUser(@NonNull User user) {
        userRepository.addOrUpdateUser(user);
    }

    @Override
    public void addProblem(@NonNull Problem problem) {
        // Create new problem metrics
        ProblemMetrics problemMetrics = new ProblemMetrics(problem);

        problemRepository.addProblem(problem);
        problemMetricsRepository.addOrUpdateProblemMetrics(problemMetrics);
    }

    @Override
    public List<Problem> solve(User user, Problem problem, SubmissionStatus status, Long timeTaken) throws UserNotFoundException {
        if(Objects.isNull(userRepository.findById(user.getId())))
            throw new UserNotFoundException(user.getId());

        List<Problem> relevantProblems = new ArrayList<>();
        Submission submission = new Submission(user, problem, status);
        submissionRepository.addSubmission(user, submission);

        if(SubmissionStatus.SUCCESS.equals(status)) {
            // Add problem metrics
            ProblemMetrics problemMetrics = problemMetricsRepository.fetchMetrics(problem);

            if(problemMetrics == null)
                problemMetrics = new ProblemMetrics(problem);

            problemMetrics.successSubmission(user, timeTaken);

            problemMetricsRepository.addOrUpdateProblemMetrics(problemMetrics);

            user.setScore(user.getScore() + getScore(problem, timeTaken));

            // Update user score
            userRepository.addOrUpdateUser(user);

            // Get relevant problems
            relevantProblems = getNRecommendedProblems(problem, 3);
        }

        return relevantProblems;
    }

    @Override
    public List<Problem> fetchSolvedProblems(User user) throws UserNotFoundException {
        if(Objects.isNull(userRepository.findById(user.getId())))
            throw new UserNotFoundException(user.getId());

        List<Problem> solvedProblems = new ArrayList<>();

        List<Submission> submissionList = submissionRepository.findByUser(user);

        for(Submission submission : submissionList) {
            if(SubmissionStatus.SUCCESS.equals(submission.getStatus())) {
                solvedProblems.add(submission.getProblem());
            }
        }

        return solvedProblems;
    }

    @Override
    public User getLeader() {
        User leader = null ;
        Long topScore = Long.valueOf(0);

        for(User user: userRepository.getAllUsers()) {
            if(user.getScore() > topScore) {
                topScore = user.getScore();
                leader = user;
            }
        }

        return leader;
    }

    @Override
    public List<Problem> getTopNProblems(int n) {
        return null;
        // TODO
    }

    @Override
    public List<Problem> getNRecommendedProblems(Problem problem, int n) {
        List<Problem> problemList = new ArrayList<>() ;

        // Passing n+1 as
        problemList = problemRepository.getNProblemsByTag(problem, n);

        return problemList;
    }

    @Override
    public List<ProblemMetrics> fetchProblems(ProblemDifficulty difficulty) {
        List<ProblemMetrics> problemMetricsList = new ArrayList<>();

        for(Problem problem : problemRepository.getAll()) {
            if(problem.getDifficultyLevel().equals(difficulty)) {
                problemMetricsList.add(problemMetricsRepository.fetchMetrics(problem));
            }
        }

        return problemMetricsList;
    }

    @Override
    public List<ProblemMetrics> fetchProblems(Boolean scoreIncreasing) {
        return null;
    }

    @Override
    public List<ProblemMetrics> fetchProblems(List<Tag> tags) {
        return null;
    }

    @Override
    public void likeProblem(User user, Problem problem) {
        Problem currProblem = problemRepository.getById(problem.getId());

        if(currProblem == null)
            return ;

        ProblemMetrics problemMetrics = problemMetricsRepository.fetchMetrics(currProblem);
        problemMetrics.changeLikesCount(LIKE_ONE);
    }


    @Override
    public void disLikeProblem(User user, Problem problem) {
        Problem currProblem = problemRepository.getById(problem.getId());

        if(currProblem == null)
            return ;

        ProblemMetrics problemMetrics = problemMetricsRepository.fetchMetrics(currProblem);
        problemMetrics.changeLikesCount(DISLIKE_ONE);
    }


    private Long getScore(Problem problem, Long timeTaken) {
        if(timeTaken <= 5)
            return problem.getScore();

        else if(timeTaken <= 30)
            return Long.valueOf((long) ( problem.getScore() * (100-timeTaken)/100));

        else
            return Long.valueOf((long) ( problem.getScore() * 0.5));
    }
}
