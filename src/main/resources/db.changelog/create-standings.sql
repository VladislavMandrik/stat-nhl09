CREATE TABLE IF NOT EXISTS standings
(
    team          CHARACTER VARYING(30) PRIMARY KEY,
    games         INTEGER,
    wins          INTEGER,
    loses         INTEGER,
    losesOT       INTEGER,
    goals_scored  INTEGER,
    missing_goals INTEGER,
    points        INTEGER
);

CREATE TABLE IF NOT EXISTS stats
(
    player     CHARACTER VARYING(100) PRIMARY KEY,
    games      INTEGER,
    goals      INTEGER,
    assists    INTEGER,
    points     INTEGER,
    PPG        CHARACTER VARYING(100),
    plus_minus INTEGER
);

CREATE TABLE IF NOT EXISTS goalie_stats
(
    player          CHARACTER VARYING(100) PRIMARY KEY,
    games           INTEGER,
    shots_against   INTEGER,
    goals_against   INTEGER,
    save_percentage CHARACTER VARYING(100),
    GAA             CHARACTER VARYING(100),
    TOI             DOUBLE PRECISION,
    assists         INTEGER
);

CREATE TABLE IF NOT EXISTS team_stats
(
    team                      CHARACTER VARYING(100) PRIMARY KEY,
    onetimer_shots            INTEGER,
    onetimer_goals            INTEGER,
    onetimer_goals_percentage CHARACTER VARYING(100),
    games                     INTEGER,
    OTPG                      CHARACTER VARYING(100),
    OTGPG                     CHARACTER VARYING(100),
    power_play                CHARACTER VARYING(100),
    penalty_kill              CHARACTER VARYING(100),
    shotspg                   CHARACTER VARYING(100),
    shots_percentage          CHARACTER VARYING(100),
    gspg                      CHARACTER VARYING(100),
    gmpg                      CHARACTER VARYING(100)
);

CREATE TABLE IF NOT EXISTS defenseman_stats
(
    player     CHARACTER VARYING(100) PRIMARY KEY,
    games      INTEGER,
    goals      INTEGER,
    assists    INTEGER,
    points     INTEGER,
    PPG        CHARACTER VARYING(100),
    plus_minus INTEGER
);

CREATE TABLE IF NOT EXISTS results
(
    first_team   CHARACTER VARYING(100) PRIMARY KEY,
    first_goals  CHARACTER VARYING(100),
    total        CHARACTER VARYING(100),
    second_team  CHARACTER VARYING(100),
    second_goals CHARACTER VARYING(100)
);

CREATE TABLE IF NOT EXISTS res
(
    res   CHARACTER VARYING(500) PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS playerstat
(
    id           BIGSERIAL PRIMARY KEY,
    playerstat   CHARACTER VARYING(500)
    );

CREATE TABLE IF NOT EXISTS teamstat
(
    id           BIGSERIAL PRIMARY KEY,
    teamstat   CHARACTER VARYING(500)
    );

CREATE VIEW a AS SELECT * FROM standings where team in ('UTC', 'NFK', 'WBS', 'HFD', 'HAM', 'MIL', 'ADR', 'SEA');
CREATE VIEW b AS SELECT * FROM standings where team in ('BNG', 'HER', 'IOW', 'ROC', 'OKL', 'GRA', 'STJ', 'TEX');

create table if not exists logger
(
    log CHARACTER VARYING(500) PRIMARY KEY
);