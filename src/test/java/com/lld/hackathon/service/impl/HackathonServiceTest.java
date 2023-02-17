package com.lld.hackathon.service.impl;

import com.lld.hackathon.entity.Problem;
import com.lld.hackathon.entity.Tag;
import com.lld.hackathon.entity.User;
import com.lld.hackathon.enums.Department;
import com.lld.hackathon.enums.ProblemDifficulty;
import com.lld.hackathon.enums.SubmissionStatus;
import com.lld.hackathon.repository.ProblemMetricsRepo;
import com.lld.hackathon.repository.ProblemRepo;
import com.lld.hackathon.repository.SubmissionRepo;
import com.lld.hackathon.repository.UserRepo;
import com.lld.hackathon.repository.impl.ProblemMetricsRepoImpl;
import com.lld.hackathon.repository.impl.ProblemRepoImpl;
import com.lld.hackathon.repository.impl.SubmissionRepoImpl;
import com.lld.hackathon.repository.impl.UserRepoImpl;
import com.lld.hackathon.service.HackathonService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class HackathonServiceTest {
    private UserRepo userRepository;
    private ProblemRepo problemRepository;
    private ProblemMetricsRepo problemMetricsRepository;
    private SubmissionRepo submissionRepository;

    private HackathonService hackathonService;

    @BeforeEach
    public void setup() {
        userRepository = new UserRepoImpl();
        problemRepository = new ProblemRepoImpl();
        problemMetricsRepository = new ProblemMetricsRepoImpl();
        submissionRepository = new SubmissionRepoImpl();

        hackathonService = new HackathonServiceImpl(userRepository,
                problemRepository,
                problemMetricsRepository,
                submissionRepository);
    }

    @SneakyThrows
    @Test
    public void testFlow() {

        // Create few users
        User user1 = new User("1", "User 1", Department.COMPUTER_ENGG);
        User user2 = new User("2", "User 2", Department.ELECTRONICS_ENGG);
        User user3 = new User("3", "User 3", Department.MECHANICAL_ENGG);

        // Add users to system
        hackathonService.addUser(user1);
        hackathonService.addUser(user2);
        hackathonService.addUser(user3);

        // Create few scores
        Long Score50 = Long.valueOf(50);
        Long Score100 = Long.valueOf(100);
        Long Score150 = Long.valueOf(150);

        // Create few Problem tags
        Tag tagSorting = new Tag("Sorting");
        Tag tagBFS = new Tag("BFS");
        Tag tagDP = new Tag("DP");
        Tag tagBST = new Tag("BST");
        Tag tagDFS = new Tag("DFS");

        // Create few questions
        Problem problem1 = new Problem("1",
                "Problem Statement1",
                Arrays.asList(tagDP),
                ProblemDifficulty.HARD,
                Score150);

        Problem problem2 = new Problem("2",
                "Problem Statement2",
                Arrays.asList(tagSorting),
                ProblemDifficulty.EASY,
                Score50);

        Problem problem3 = new Problem("3",
                "Problem Statement3",
                Arrays.asList(tagSorting),
                ProblemDifficulty.MEDIUM,
                Score100);

        Problem problem4 = new Problem("4",
                "Problem Statement4",
                Arrays.asList(tagBST),
                ProblemDifficulty.MEDIUM,
                Score100);

        Problem problem5 = new Problem("5",
                "Problem Statement5",
                Arrays.asList(tagBFS),
                ProblemDifficulty.HARD,
                Score150);

        // Add problems to system
        hackathonService.addProblem(problem1);
        hackathonService.addProblem(problem2);
        hackathonService.addProblem(problem3);
        hackathonService.addProblem(problem4);
        hackathonService.addProblem(problem5);

        // Create few time taken to solve (in mins)
        Long timeTaken5 = Long.valueOf(5);
        Long timeTaken10 = Long.valueOf(10);
        Long timeTaken15 = Long.valueOf(15);
        Long timeTaken30 = Long.valueOf(30);
        Long timeTaken60 = Long.valueOf(60);
        Long timeTaken100 = Long.valueOf(100);


        // Solve questions by users
        hackathonService.solve(user1, problem1, SubmissionStatus.SUCCESS, timeTaken5);
        hackathonService.solve(user1, problem2, SubmissionStatus.SUCCESS, timeTaken10);
        hackathonService.solve(user1, problem3, SubmissionStatus.FAILURE, timeTaken30);
        hackathonService.solve(user1, problem4, SubmissionStatus.SUCCESS, timeTaken60);

        hackathonService.solve(user2, problem2, SubmissionStatus.SUCCESS, timeTaken10);
        hackathonService.solve(user2, problem3, SubmissionStatus.FAILURE, timeTaken30);
        hackathonService.solve(user2, problem5, SubmissionStatus.SUCCESS, timeTaken100);

        hackathonService.solve(user3, problem5, SubmissionStatus.FAILURE, timeTaken15);
        hackathonService.solve(user3, problem3, SubmissionStatus.FAILURE, timeTaken100);

        // User Like few questions
        hackathonService.likeProblem(user1, problem1);
        hackathonService.likeProblem(user1, problem2);

        hackathonService.likeProblem(user2, problem1);

        hackathonService.likeProblem(user3, problem3);

        // Get and verify questions solved
        assert hackathonService.fetchSolvedProblems(user1).size() == 3 ;
        assert hackathonService.fetchSolvedProblems(user2).size() == 2 ;
        assert hackathonService.fetchSolvedProblems(user3).size() == 0 ;

        // Get and verify top liked questions
        // assert hackathonService.getTopNProblems(3).get(0) == problem1;

        // Get and verify leader user
        assert hackathonService.getLeader() == user1;

        // Get and relevant questions list
        hackathonService.solve(user3, problem3, SubmissionStatus.FAILURE, timeTaken100);

        // Fetch and verify Easy problem list
        assert hackathonService.fetchProblems(ProblemDifficulty.EASY).size() == 1;
    }

}
