package server.engine.state.entity.enemy;

import java.util.LinkedHashSet;

import server.engine.ai.enemyAI.EnemyAI;
import server.engine.ai.enemyAI.ZombieAI;
import server.engine.state.item.pickup.Ammo;
import server.engine.state.item.pickup.Health;
import server.engine.state.item.weapon.gun.HeavyPistol;
import server.engine.state.item.weapon.gun.LaserPistol;
import server.engine.state.item.weapon.gun.Pistol;
import shared.lists.AmmoList;
import shared.lists.EntityList;

/**
 * Basic Zombie class.
 * 
 * @author Richard
 *
 */
public class Zombie extends Enemy {
    public static final int DEFAULT_HEALTH = 2;
    public static final double DEFAULT_MOVEMENT_FORCE = 2;
    public static final int DEFAULT_SIZE = EntityList.ZOMBIE.getSize() / 2;
    public static final int DEFAULT_SCORE_ON_KILL = 10;
    public static final double DEFAULT_MASS = 2;
    public static final LinkedHashSet<Drop> DEFAULT_DROPS = new LinkedHashSet<>();

    static {
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.5, 4, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.SHOTGUN_ROUND), 0.1, 2, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.HEAVY_AMMO), 0.05, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ENERGY), 0.05, 16, 4));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.MAGIC_ESSENCE), 0.05, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ROCKET_AMMO), 0.01, 1, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 0.03, 1));
        DEFAULT_DROPS.add(new Drop(new Pistol(), 0.08, 1, 1));
        DEFAULT_DROPS.add(new Drop(new LaserPistol(), 0.02, 1, 1));
        DEFAULT_DROPS.add(new Drop(new HeavyPistol(), 0.02, 1, 1));
    }

    public Zombie() {
        this(EntityList.ZOMBIE, DEFAULT_HEALTH, DEFAULT_MOVEMENT_FORCE, DEFAULT_SIZE, DEFAULT_DROPS, DEFAULT_SCORE_ON_KILL, new ZombieAI(), DEFAULT_MASS);
    }

    Zombie(EntityList enemyType, int maxHealth, double acceleration, int size, LinkedHashSet<Drop> drops, int scoreOnKill, EnemyAI ai, double mass) {
        super(maxHealth, acceleration, enemyType, size, drops, scoreOnKill, ai, mass);
    }

    @Override
    EnemyAI getNewAI() {
        return new ZombieAI();
    }

}
