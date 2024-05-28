package se.issuetrackingsystem.issue.dto;

import lombok.Getter;
import se.issuetrackingsystem.issue.domain.Issue;

import java.util.List;
import java.util.Map;

@Getter
public class IssueStatisticsResponse {

    private final Map<Issue.Status, Long> statusDistribution;
    private final Map<String, Long> reporterDistribution;
    private final Map<String, Long> assigneeDistribution;
    private final List<String> topCommentedIssues;

    public IssueStatisticsResponse(Map<Issue.Status, Long> statusDistribution,
                                   Map<String, Long> reporterDistribution,
                                   Map<String, Long> assigneeDistribution,
                                   List<String> topCommentedIssues) {
        this.statusDistribution = statusDistribution;
        this.reporterDistribution = reporterDistribution;
        this.assigneeDistribution = assigneeDistribution;
        this.topCommentedIssues = topCommentedIssues;
    }
}
