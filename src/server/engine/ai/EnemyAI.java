package server.engine.ai;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.HashSet;
import java.util.LinkedList;

import server.engine.state.entity.attack.Attack;
import server.engine.state.map.tile.Tile;
import shared.Constants;
import shared.Pose;

public abstract class EnemyAI {

    protected Pose pose;
    private int size;
    private HashSet<Pose> playerPoses;
    protected Pose closestPlayer;
    protected Tile[][] tileMap;
    private boolean isProcessing = false;

    protected EnemyAI() {
    }

    public abstract LinkedList<Attack> getAttacks();

    protected abstract Pose generateNextPose(double maxDistanceToMove, Pose closestPlayer);

    public abstract AIAction getAction();

    protected HashSet<Pose> getPlayerPoses() {
        return playerPoses;
    }

    public Pose getCurrentPose(){
        return pose;
    }

    public Pose getClosestPlayer(){ return closestPlayer; }

    public Pose getNewPose(double maxDistanceToMove) {
        return generateNextPose(maxDistanceToMove, closestPlayer);
    }

    protected int getDistToPlayer(Pose player) {
        return (int) sqrt(pow(pose.getY() - player.getY(), 2) + pow(pose.getX() - player.getX(), 2));
    }

    public void setInfo(Pose pose, int size, HashSet<Pose> playerPoses, Tile[][] tileMap) {
        this.pose = pose;
        this.size = size;
        this.playerPoses = playerPoses;
        this.tileMap = tileMap;
        this.closestPlayer = findClosestPlayer(playerPoses);
    }

    // May not need this
    public boolean isProcessing() {
        return isProcessing;
    }

    protected Pose findClosestPlayer(HashSet<Pose> playerPoses) {
        Pose closestPlayer = playerPoses.iterator().next();
        int distToClosest = Integer.MAX_VALUE;

        for (Pose playerPose : playerPoses) {
            int distToPlayer = getDistToPlayer(playerPose);
            if(distToPlayer < distToClosest){
                distToClosest = distToPlayer;
                closestPlayer = playerPose;
            }
        }

        return closestPlayer;
    }

}
