package server.engine.state.entity.attack;

import server.engine.state.entity.Entity;
import server.engine.state.physics.Force;
import server.engine.state.physics.Physics;
import shared.Location;
import shared.lists.EntityList;

//AOE - Area of Effect
public class AoeAttack extends Entity implements Attack {
    private static final int FORCE_SCALING = 800;
    private static final double FALL_OFF = 0.75;

    protected int damage;

    public AoeAttack(Location attackLocation, int attackSize, int damage) {
        super(attackLocation, attackSize, EntityList.DEFAULT);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public AttackType getAttackType() {
        return AttackType.AOE;
    }

    @Override
    public Entity makeCopy() {
        return new AoeAttack(pose, size, damage);
    }

    public Force getForce(Location location, Location backUp) {
        Location locationToUse = this.pose;
        double[] components = Physics.fromComponents(location.getX() - locationToUse.getX(), location.getY() - locationToUse.getY());
        double force = Math.pow(1.6, damage) * FORCE_SCALING;
        
        double falloffDist = this.size * FALL_OFF;

        if (components[1] > falloffDist) {
            force *= (1 - ((components[1] - falloffDist) / (this.size - falloffDist)));
        }
        if (components[1] == 0) {
            locationToUse = backUp;
        }
        components = Physics.fromComponents(location.getX() - locationToUse.getX(), location.getY() - locationToUse.getY());
        return new Force((int) components[0], force);
    }
}
