package se.issuetrackingsystem.issue.dto;

import lombok.Getter;
import se.issuetrackingsystem.issue.domain.Issue;

import java.util.List;
import java.util.Map;

@Getter
public class IssueStatisticsResponse {

    private Map<Issue.Status, Long> statusDistribution;
    private Map<String, Long> reporterDistribution;
    private Map<String, Long> assigneeDistribution;
    private List<Issue> topCommentedIssues;

    public IssueStatisticsResponse(Map<Issue.Status, Long> statusDistribution,
                                   Map<String, Long> reporterDistribution,
                                   Map<String, Long> assigneeDistribution,
                                   List<Issue> topCommentedIssues) {
        this.statusDistribution = statusDistribution;
        this.reporterDistribution = reporterDistribution;
        this.assigneeDistribution = assigneeDistribution;
        this.topCommentedIssues = topCommentedIssues;
    }
}
