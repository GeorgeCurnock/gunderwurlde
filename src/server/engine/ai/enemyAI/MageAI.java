package server.engine.ai.enemyAI;

import server.engine.ai.AIAction;
import server.engine.state.entity.attack.AoeAttack;
import server.engine.state.entity.attack.Attack;
import server.engine.state.entity.attack.ProjectileAttack;
import server.engine.state.entity.enemy.Enemy;
import server.engine.state.item.weapon.gun.FireGun;
import server.engine.state.item.weapon.gun.Gun;
import server.engine.state.item.weapon.gun.IceGun;
import server.engine.state.physics.Force;
import shared.Pose;
import shared.lists.ActionList;
import shared.lists.Team;

import java.util.Random;

public class MageAI extends EnemyAI {

    private final long TIME_BETWEEN_TELEPORTS;
    private final int DISTANCE_TO_PLAYER;
    private long lastTeleport = 0;
    private Gun gun;
    private boolean teleportAway = false;
    long now;

    public MageAI(long timeBetweenTeleports, int distanceToPlayer){
        super(SHORT_DELAY);
        this.DISTANCE_TO_PLAYER = distanceToPlayer;
        this.TIME_BETWEEN_TELEPORTS = timeBetweenTeleports;
        Random rand = new Random();

        if(rand.nextInt(2) == 1){
            gun = new FireGun();
        }else{
            gun = new IceGun();
        }
    }

    @Override
    public AIAction getAction() {
        now = System.currentTimeMillis();
        if (attacking) {
            return AIAction.ATTACK;
        }else if(teleportAway){
            return AIAction.UPDATE;
        } else if (now - lastTeleport >= TIME_BETWEEN_TELEPORTS) {
            this.actionState = ActionList.ATTACKING;
            attacking = true;
            lastTeleport = System.currentTimeMillis();
            return AIAction.UPDATE;
        }
        return AIAction.WAIT;
    }

    @Override
    public Enemy getUpdatedEnemy() {

        if(teleportAway){
            if (now - lastTeleport >= TIME_BETWEEN_TELEPORTS) {
                teleportAway = false;
                lastTeleport = System.currentTimeMillis();
                enemy.setPose(findPoseAroundPlayer(DISTANCE_TO_PLAYER + 300, false));
                return enemy;
            }else{
                return enemy;
            }
        }else {
            enemy.setPose(findPoseAroundPlayer(DISTANCE_TO_PLAYER, true));
            return enemy;
        }
    }

    @Override
    protected Attack getAttackObj() {
        teleportAway = true;
        int attackAngle = getAngle(pose, closestPlayer);
//        return new ProjectileAttack(gun.getShotProjectiles(new Pose(pose, attackAngle), Team.ENEMY));
        return new AoeAttack(closestPlayer, 24, 1);
    }

    @Override
    protected Force generateMovementForce() {
        return new Force(pose.getDirection(), 0);
    }
}
