DROP PROCEDURE IF EXISTS initGameData^;
DROP PROCEDURE IF EXISTS initMapData^;

CREATE PROCEDURE initGameData()
BEGIN
    DECLARE existCount INT;
    SET existCount = (SELECT COUNT(*) FROM game);

    IF existCount < 1
    THEN
        INSERT INTO game
            (id, name)
        VALUES
        (1, 'Wolfenstein: Enemy Territory')
             , (2, 'Counter-Strike: Global Offensive');
    END IF;
END^;

CREATE PROCEDURE initMapData()
BEGIN
    DECLARE existCount INT;
    SET existCount = (SELECT COUNT(*) FROM map);

    IF existCount < 1
    THEN
        INSERT INTO map
            (id, name, game_id)
        VALUES
        (1, 'supply', 1)
             , (2, 'sw_goldrush_te', 1)
             , (3, 'bremen_b3', 1)
             , (4, 'frostbite', 1)
             , (5, 'adlernest', 1)
             , (6, 'et_ice', 1)
             , (7, 'et_ufo_final', 1)
             , (8, 'radar', 1)
             , (9, 'missile_b3', 1)
             , (10, 'erdenberg_b3', 1);

        INSERT INTO map
            (id, name, game_id)
        VALUES
        (11, 'Inferno', 2)
             , (12, 'Train', 2)
             , (13, 'Mirage', 2)
             , (14, 'Nuke', 2)
             , (15, 'Overpass', 2)
             , (16, 'Dust II', 2)
             , (17, 'Vertigo', 2)
             , (18, 'Cache', 2)
             , (19, 'Cobblestone', 2);
    END IF;
END^;

CALL initGameData^;
CALL initMapData^;
