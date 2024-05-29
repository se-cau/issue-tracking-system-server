package se.issuetrackingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(properties = "spring.config.location=" +
        "classpath:/application.yml")
class IssueTrackingSystemApplicationTests {
    @Test
    void contextLoads() {
    }

}