package se.issuetrackingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.issuetrackingsystem.comment.service.CommentService;
import se.issuetrackingsystem.issue.service.IssueService;
import se.issuetrackingsystem.project.service.ProjectService;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest
class IssueTrackingSystemApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    IssueService issueService;
    @Autowired
    CommentService commentService;

//    @Test
//    void addData() throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\db\\users.txt"));
//
//        String str;
//        ArrayList<String> strs;
//        while ((str = bufferedReader.readLine()) != null)
//        {
//            strs = new ArrayList<>(Arrays.asList(str.split("\t")));
//
//            RegisterRequest req = new RegisterRequest(strs.get(0),"1234",strs.get(2));
//            userService.register(req);
//        }
//    }

}