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
    OTGPG                     CHARACTER VARYING(100)
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