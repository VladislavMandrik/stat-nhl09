package com.example.demo.controller.standings;

import com.example.demo.controller.StandingsControllerImpl;
import com.example.demo.model.TeamStats;
import com.example.demo.repository.TeamStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StandingsControllerImplTest {

    @Mock
    private TeamStatsRepository tSRepository;

    @Mock
    private Model model;

    @InjectMocks
    private StandingsControllerImpl standingsController;

    private static TeamStats expectedTs;
    private static List<TeamStats> expectedStatsList;

    private static final String TEAM = "CHI";
    private static final Integer ONETIMER_SHOTS = 45;
    private static final Integer ONETIMER_GOALS = 12;
    private static final String ONETIMER_GOALS_PERCENTAGE = "26.7";
    private static final Integer GAMES = 82;
    private static final String OTPG = "0.37";
    private static final String OTGPG = "0.15";
    private static final String POWER_PLAY = "23.5";
    private static final String PENALTY_KILL = "81.2";
    private static final String SHOTS_PG = "31.4";
    private static final String SHOTS_PERCENTAGE = "9.8";
    private static final String GSPG = "3.12";
    private static final String GMPG = "2.89";

    @BeforeEach
    void setUp() {
        expectedTs = createTs();
        expectedStatsList = Arrays.asList(expectedTs);
    }

    private static TeamStats createTs() {
        TeamStats ts = new TeamStats();
        ts.setTeam(TEAM);
        ts.setOnetimerShots(ONETIMER_SHOTS);
        ts.setOnetimerGoals(ONETIMER_GOALS);
        ts.setOnetimerGoalsPercentage(ONETIMER_GOALS_PERCENTAGE);
        ts.setGames(GAMES);
        ts.setOTPG(OTPG);
        ts.setOTGPG(OTGPG);
        ts.setPowerPlay(POWER_PLAY);
        ts.setPenaltyKill(PENALTY_KILL);
        ts.setShotsPG(SHOTS_PG);
        ts.setShotsPercentage(SHOTS_PERCENTAGE);
        ts.setGSPG(GSPG);
        ts.setGMPG(GMPG);
        return ts;
    }

    @Test
    void findTsByOrderByPowerPlayDesc() {
        when(tSRepository.findAllByOrderByPowerPlayDesc())
                .thenReturn(expectedStatsList);

        String viewName = standingsController.teamstats(model);

        assertEquals("teamStats_page", viewName);
        verify(model, times(1)).addAttribute("teamStats", expectedStatsList);
        verify(tSRepository, times(1)).findAllByOrderByPowerPlayDesc();
        verifyNoMoreInteractions(tSRepository, model);
    }
}