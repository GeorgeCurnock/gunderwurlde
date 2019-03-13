package server.engine.ai;

import server.engine.state.entity.attack.Attack;
import server.engine.state.entity.attack.ProjectileAttack;
import server.engine.state.item.weapon.gun.Pistol;
import server.engine.state.physics.Force;
import shared.Constants;
import shared.Pose;
import shared.lists.ActionList;
import shared.lists.Teams;

import java.util.LinkedList;

public class MachineGunnerAI extends ZombieAI {

    //Probably needs a better name
    //The angle the enemy shoots
    private final int ATTACK_WIDTH;
    private final int BULLETS_PER_ATTACK;
    private boolean addToAngle = false;
    private int startOfAttackAngle;
    private int attackAngle;
    private int bulletsShotInThisAttack = 0;
    private boolean delayPast;
    private Pistol pistol = new Pistol();

    public MachineGunnerAI(int attackWidth, int bulletsPerAttack) {
        super();
        this.ATTACK_WIDTH = attackWidth;
        this.BULLETS_PER_ATTACK = bulletsPerAttack;
        distanceToPlayerForAttack = Constants.TILE_SIZE * 10;
        attackDelay = LONG_DELAY;
        randomizePath = false;
    }

    @Override
    public LinkedList<Attack> getAttacks() {
        LinkedList<Attack> attacks = new LinkedList<>();
        long now = System.currentTimeMillis();
        delayPast = (now - beginAttackTime) >= attackDelay;

        if (delayPast) {
            if (bulletsShotInThisAttack != BULLETS_PER_ATTACK) {
                attacks.add(new ProjectileAttack(pistol.getShotProjectiles(
                        new Pose(pose, attackAngle), Teams.ENEMY)));

                bulletsShotInThisAttack++;

                if (attackAngle == startOfAttackAngle + ATTACK_WIDTH) {
                    addToAngle = false;
                } else if (attackAngle == startOfAttackAngle) {
                    addToAngle = true;
                }

                if(addToAngle){
                    attackAngle++;
                }else{
                    attackAngle--;
                }

            } else {
                this.actionState = ActionList.NONE;
                attacking = false;
                bulletsShotInThisAttack = 0;
            }
        } else {
            startOfAttackAngle = Pose.normaliseDirection(getAngle(pose, closestPlayer) - ATTACK_WIDTH / 2);
            attackAngle = startOfAttackAngle;
        }

        return attacks;
    }

    @Override
    public Force getForceFromAttack(double maxMovementForce) {
        if (delayPast) {
            return new Force(attackAngle, 0.00001);
        } else {
            return new Force(0, 0);
        }
    }
}
