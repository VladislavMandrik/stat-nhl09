package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Nhl09ServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void testEssentialBeansArePresent() {
        assertThat(applicationContext.containsBean("standingsServiceImpl")).isTrue();
        assertThat(applicationContext.containsBean("statsServiceImpl")).isTrue();
        assertThat(applicationContext.containsBean("playerStatsRepository")).isTrue();
        assertThat(applicationContext.containsBean("standingsRepository")).isTrue();
        assertThat(applicationContext.containsBean("defensemanStatsRepository")).isTrue();
        assertThat(applicationContext.containsBean("goalieStatsRepository")).isTrue();
        assertThat(applicationContext.containsBean("teamStatsRepository")).isTrue();
        assertThat(applicationContext.containsBean("transfersRepository")).isTrue();
        assertThat(applicationContext.containsBean("dataSource")).isTrue();
    }

    @Test
    void testApplicationStarts() {
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        assertThat(activeProfiles).contains("test");
    }
}