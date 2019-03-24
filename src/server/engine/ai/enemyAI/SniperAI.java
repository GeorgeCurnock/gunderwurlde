package server.engine.ai.enemyAI;

import server.engine.ai.AIAction;
import server.engine.ai.aStar.AStar;
import server.engine.state.entity.attack.Attack;
import server.engine.state.entity.attack.ProjectileAttack;
import server.engine.state.item.weapon.gun.Gun;
import server.engine.state.item.weapon.gun.Pistol;
import server.engine.state.item.weapon.gun.SniperRifle;
import server.engine.state.map.tile.Tile;
import server.engine.state.physics.Force;
import shared.Pose;
import shared.lists.Team;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class SniperAI extends AStarUsingEnemy {

    private final int RANGE_TO_RUN_AWAY;
    Gun gun = new SniperRifle();
    private Pose poseToGo;
    private boolean runningAway;
    private boolean inPositionToAttack = false;
    private boolean playerAiming = false;
    private LinkedList<Pose> posePath;
    private boolean AStartProcessing = false;
    private int expandedRange = 5;

    public SniperAI(int rangeToRunAway) {
        super(LONG_DELAY * 2);
        this.RANGE_TO_RUN_AWAY = rangeToRunAway;
    }

    @Override
    public AIAction getAction() {
        if (attacking) {
            return AIAction.ATTACK;
        } else if (stillInPositionToAttack() && inPositionToAttack) {
            attacking = true;
            beginAttackTime = System.currentTimeMillis();
            return AIAction.ATTACK;
        } else {
            return AIAction.MOVE;
        }
    }

    private boolean stillInPositionToAttack() {
        double distanceBetween = getDistToPlayer(closestPlayer);
        boolean stillInPosition = distanceBetween > RANGE_TO_RUN_AWAY - RANGE_TO_RUN_AWAY * 0.05
                && distanceBetween <= RANGE_TO_RUN_AWAY + RANGE_TO_RUN_AWAY * 0.2;

        if(stillInPosition){
            return true;
        }else{
            inPositionToAttack = false;
            return false;
        }
    }

//    public AIAction attackController() {
//
//
//        return AIAction.WAIT;
//    }

    private boolean inRangeToRun() {
        return false;
    }


    @Override
    protected Attack getAttackObj() {
        int angle = getAngle(pose, closestPlayer);
        return new ProjectileAttack(gun.getShotProjectiles(new Pose(pose, angle), Team.ENEMY));
    }

    @Override
    protected Force generateMovementForce() {
        //SNIPER 400
//            System.out.println(tileMap.length + " " + tileMap[0].length);
//            int[] tile = new int[2];
//
//            for(int i = 0; i <= tileMap.length; i ++){
//                for(int j = 0; j <= tileMap[0].length; j ++){
//                    tile[0] = i;
//                    tile[1] = j;
//                    System.out.print(tileNotSolid(tile, tileMap));
//                }
//                System.out.println();
//            }
        if (posePath == null) {
            if (!AStartProcessing) {
                Pose endPose = findPositionToAttack();
                new AStar(this, 1, transposeMatrix(tileMap), pose, endPose).start();
                AStartProcessing = true;
            }
        } else {
            Force angle = getForceFromPath();
            if (angle != null) return angle;
        }

//        else {
//            if (posePath == null) {
//                if (!AStartProcessing) {
//                    (new AStar(this, 1, tileMap, pose, closestPlayer)).start();
//                    AStartProcessing = true;
//                } else {
//                    //cloak and move somewhere?
//                }
//            } else {
////                System.out.println("pose to go: " + posePath.peekLast());
//                Force force = getForceFromPath();
//                if (force != null) return force;
//            }
//        }
        return new Force(pose.getDirection(), 0);
    }

    private Force getForceFromPath() {
        if (!Pose.compareLocation(pose, posePath.peekLast(), 5)) {
            int angle = getAngle(pose, posePath.peekLast());
            return new Force(angle, maxMovementForce);
        } else {
            posePath.pollLast();
            if (posePath.size() == 0) {
                posePath = null;
                inPositionToAttack = true;
            }
        }
        return null;
    }

    private Pose findPositionToAttack() {
        Pose positionToAttack;
        int angle = getAngle(closestPlayer, pose);
        do {
            positionToAttack = poseInDistance(closestPlayer,
                    ThreadLocalRandom.current().nextInt(angle - expandedRange, angle + expandedRange),
                    ThreadLocalRandom.current().nextInt(RANGE_TO_RUN_AWAY, RANGE_TO_RUN_AWAY + 20));
            expandedRange += 5;
        } while ((!pathUnobstructed(positionToAttack, closestPlayer, tileMap))
                || (!tileNotSolid(Tile.locationToTile(positionToAttack), tileMap)));

        expandedRange = 5;
        return positionToAttack;
    }

    public void setTilePath(LinkedList<Pose> aStar) {
//        try {
//            for (Pose pose : aStar) {
//                System.out.println(Tile.locationToTile(pose)[0] + " " + Tile.locationToTile(pose)[1]);
//            }
//        }catch (Exception e){
//            System.out.println("AStar returned null");
//        }

        posePath = aStar;
        AStartProcessing = false;
    }

}