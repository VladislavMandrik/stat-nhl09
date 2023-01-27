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