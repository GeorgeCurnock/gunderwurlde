package server.game_engine.ai;

import data.Pose;
import data.map.Meadow;
import data.map.tile.Tile;
import data.map.tile.TileState;
import data.map.tile.TileTypes;
import javafx.util.Pair;

import java.util.ArrayList;

public class AStarTest {
    public static void main(String[] args) {

        Meadow map = new Meadow();
        final Tile[][] meadow = map.getTileMap();

        final double COST_OF_TRAVEL = 0.8;

        Pose playerPose = new Pose(3,5);
        Pose enemPose = new Pose(15,25);


        Pair<Integer, Integer> playerLoc = new Pair<>(playerPose.getY(), playerPose.getX()); //y and x
        Pair<Integer, Integer> enemLoc = new Pair<>(enemPose.getY(), enemPose.getX());
        AStar aStarForMeadow = new AStar(COST_OF_TRAVEL, meadow);

        System.out.println(enemLoc);

        ArrayList<Pair<Integer, Integer>> path = aStarForMeadow.aStar(enemLoc, playerLoc);

        for (Pair<Integer, Integer> pair : path) {
            System.out.println(pair);
        }
//        TileTypes tileType = TileTypes.GRASS;
//        TileState tileState = TileState.PASSABLE;

//        Tile tile1 = new Tile(TileTypes.GRASS, TileState.PASSABLE);
//        Tile tile2 = new Tile(TileTypes.WOOD, TileState.PASSABLE);
//
//        System.out.println(tile1.getState() == tile2.getState());
    }
}