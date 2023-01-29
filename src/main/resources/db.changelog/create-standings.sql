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
    player  CHARACTER VARYING(100) PRIMARY KEY,
    games   INTEGER,
    goals   INTEGER,
    assists INTEGER,
    points  INTEGER
);