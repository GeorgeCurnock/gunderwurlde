package server.engine.ai;

import server.engine.state.entity.attack.AoeAttack;
import server.engine.state.entity.attack.Attack;
import shared.Constants;
import shared.Pose;
import shared.lists.ActionList;

import java.util.LinkedList;
import java.util.Random;

public class ZombieAI extends EnemyAI {

    long attackDelay;
    long beginAttackTime;
    boolean attacking;
    private boolean turnLeft;
    private int stepsUntilNormPath = 0;

    public ZombieAI() {
        super();
        this.beginAttackTime = System.currentTimeMillis();
        this.attackDelay = DEFAULT_DELAY;
        this.attacking = false;
    }

    @Override
    public AIAction getAction() {
        if (attacking) {
            return AIAction.ATTACK;
        } else if (getDistToPlayer(closestPlayer) >= Constants.TILE_SIZE) {
            return AIAction.MOVE;
        } else if (getDistToPlayer(closestPlayer) < Constants.TILE_SIZE) {
            this.actionState = ActionList.ATTACKING;
            attacking = true;
            beginAttackTime = System.currentTimeMillis();
            return AIAction.ATTACK;
        }
        return AIAction.WAIT;
    }

    @Override
    public LinkedList<Attack> getAttacks() {
        LinkedList<Attack> attacks = new LinkedList<>();
        long now = System.currentTimeMillis();

        if ((now - beginAttackTime) >= attackDelay) {
            attacks.add(new AoeAttack(getClosestPlayer(), 24, 1));
            attacking = false;
            this.actionState = ActionList.NONE;
        }
        return attacks;
    }

    @Override
    protected Pose generateNextPose(double maxDistanceToMove, Pose closestPlayer) {
        Pose nextPose = checkIfInSpawn();

        if(outOfSpawn) {
            for (double i = 0.1; i < maxDistanceToMove; i += 0.1) {
                double angle = getAngle(pose, closestPlayer);
                nextPose = poseByAngle(randomizePath(angle), angle);
            }
        }

        return nextPose;
    }

    //Maybe needs some more balancing
    private double randomizePath(double angle) {
        Random rand = new Random();
        //change of moving from direct path
        int r = rand.nextInt(500);

        if(stepsUntilNormPath == 0) {
            if (r == 1) {
                turnLeft = true;
                //How much to move to a side
                stepsUntilNormPath = rand.nextInt(100) + 20;
            } else if (r == 0) {
                turnLeft = false;
                stepsUntilNormPath = rand.nextInt(100) + 20;
            } else {
                return angle;
            }
        } else {
            if (turnLeft) {
                angle -= 50;
            } else {
                angle += 50;
            }
            stepsUntilNormPath--;
        }

        return angle;
    }
}
