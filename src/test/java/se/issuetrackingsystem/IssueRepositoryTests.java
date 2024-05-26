package se.issuetrackingsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.issuetrackingsystem.issue.domain.Issue;
import se.issuetrackingsystem.issue.repository.IssueRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class IssueRepositoryTests {
    @Autowired
    IssueRepository issueRepository;

    @Test
    @DisplayName("이슈 만들기")
    void createIssue(){
        //given
        Issue issue1 = new Issue();
        issue1.setTitle("hello1");
        issue1.setDescription("hi1");

        Issue issue2 = new Issue();
        issue2.setTitle("hello2");
        issue2.setDescription("hi2");

        //when
        Issue result1 = this.issueRepository.save(issue1);
        Issue result2 = this.issueRepository.save(issue2);

        //then
        assertEquals(issue1,result1);
        assertEquals(issue2,result2);
    }

    @Test
    @DisplayName("이슈 찾기")
    void getIssue(){
        //given
        Issue issue1 = new Issue();
        issue1.setTitle("hello1");
        issue1.setDescription("hi1");
        this.issueRepository.save(issue1);

        Issue issue2 = new Issue();
        issue2.setTitle("hello2");
        issue2.setDescription("hi2");
        this.issueRepository.save(issue2);

        //when
        Issue result1 = this.issueRepository.findById(issue1.getId()).get();
        Issue result2 = this.issueRepository.findById(issue2.getId()).get();
        List<Issue> results = this.issueRepository.findAll();

        //then
        assertEquals(issue1,result1);
        assertEquals(issue2,result2);
        assertEquals(results.size(),2);
    }

    @Test
    @DisplayName("이슈 삭제")
    void deleteIssue() {
        //given
        Issue issue1 = new Issue();
        issue1.setTitle("hello1");
        issue1.setDescription("hi1");
        this.issueRepository.save(issue1);

        //when
        this.issueRepository.delete(issue1);

        //then
        assertTrue(this.issueRepository.findById(issue1.getId()).isEmpty());
    }
}
