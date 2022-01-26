package com.example.g15_bugkiller;

//Klassedient nur zum Testen

// trichter.json


import MapGeneration.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestInputData {



    public static Input createMapData(){
        List<Tile> tiles =
                List.of(
                        new Tile("main",
                                List.of(
                                        new TileVersion(
                                                List.of(
                                                        List.of(new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.WALL,new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL,new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.EXIT, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.GEM, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.ME, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.STONE, new Values())),
                                                                new Field(new Gegenstand(Type.BRICKS, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.PATH, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))
                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.MUD, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))

                                                        ),

                                                        List.of(new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values())),
                                                                new Field(new Gegenstand(Type.WALL, new Values()))

                                                        )
                                                )
                                        )
                                )
                        )
                );

        List<TilesAt> tilesAts = List.of(
                new TilesAt(new Coordinate(0,0), "main", 0)
        );

        List<ConnectBy> connectBys = List.of(

        );

        Field defaultField = new Field(new Gegenstand(Type.EXIT, new Values()));

        Coordinate mapSize = new Coordinate(15, 10);

        return new Input(tiles, tilesAts, connectBys, defaultField, mapSize);
    }

    public static Level createLevelData() {




        int[] gems = {8, 12, 16};

        String name = "Trichter";

        int[] ticks = {130, 50, 25};
        Level level = new Level(name, gems, createMapData(), ticks);
        level.setMainRules(List.of(new Rule(
                Situation.ANY,
                Direction.EAST,
                List.of(
                        new RuleComponent(
                                Type.ME,
                                new Values(
                                        new HashMap<>(){
                                            {put(ValuesNames.MOVED, 0); }
                                        }
                                        )
                        ),
                        new RuleComponent(
                                Type.PATH,
                                new Values(
                                        new HashMap<>(){
                                            {put(ValuesNames.MOVED, 0); }
                                        }
                                )
                        )
                ),
                List.of(
                        new RuleComponent(
                                Type.PATH,
                                new Values(
                                        new HashMap<>(){
                                            {put(ValuesNames.MOVED, 1); }
                                        }
                                )
                        ),
                        new RuleComponent(
                                Type.ME,
                                new Values(
                                        new HashMap<>(){
                                            {put(ValuesNames.MOVED, 1); }
                                        }
                                )
                        )
                )
        ),
                new Rule(
                        Situation.LEFT,
                        Direction.WEST,
                        List.of(
                                new RuleComponent(
                                        Type.ME,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 0); }
                                                }
                                        )
                                ),
                                new RuleComponent(
                                        Type.PATH,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 0); }
                                                }
                                        )
                                )
                        ),
                        List.of(
                                new RuleComponent(
                                        Type.PATH,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 1); }
                                                }
                                        )
                                ),
                                new RuleComponent(
                                        Type.ME,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 1); }
                                                }
                                        )
                                )
                        )
                ),
                new Rule(
                        Situation.DOWN,
                        Direction.SOUTH,
                        List.of(
                                new RuleComponent(
                                        Type.ME,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 0); }
                                                }
                                        )
                                ),
                                new RuleComponent(
                                        Type.PATH,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 0); }
                                                }
                                        )
                                )
                        ),
                        List.of(
                                new RuleComponent(
                                        Type.PATH,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 1); }
                                                }
                                        )
                                ),
                                new RuleComponent(
                                        Type.ME,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 1); }
                                                }
                                        )
                                )
                        )
                ),
                new Rule(
                        Situation.UP,
                        Direction.NORTH,
                        List.of(
                                new RuleComponent(
                                        Type.ME,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 0); }
                                                }
                                        )
                                ),
                                new RuleComponent(
                                        Type.PATH,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 0); }
                                                }
                                        )
                                )
                        ),
                        List.of(
                                new RuleComponent(
                                        Type.PATH,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 1); }
                                                }
                                        )
                                ),
                                new RuleComponent(
                                        Type.ME,
                                        new Values(
                                                new HashMap<>(){
                                                    {put(ValuesNames.MOVED, 1); }
                                                }
                                        )
                                )
                        )
                )));
        return level;
    }
}
