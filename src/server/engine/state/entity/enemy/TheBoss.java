package server.engine.state.entity.enemy;

import java.util.LinkedHashSet;

import server.engine.ai.enemyAI.EnemyAI;
import server.engine.ai.enemyAI.TheBossAI;
import server.engine.state.item.pickup.Ammo;
import server.engine.state.item.pickup.Health;
import shared.lists.AmmoList;
import shared.lists.EntityList;

public class TheBoss extends Zombie{
    public static final int DEFAULT_HEALTH = 25;
    public static final double DEFAULT_MOVEMENT_FORCE = 0.9;
    public static final int DEFAULT_SIZE = EntityList.THEBOSS.getSize() / 2;
    public static final int DEFAULT_SCORE_ON_KILL = 500;
    public static final double DEFAULT_MASS = 50000000;
    public static final LinkedHashSet<Drop> DEFAULT_DROPS = new LinkedHashSet<>();


    static {
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 1, 6, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.9, 6, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.9, 6, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.9, 6, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.9, 6, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.SHOTGUN_ROUND), 1, 6, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.SHOTGUN_ROUND), 0.8, 6, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.SHOTGUN_ROUND), 0.8, 6, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.SHOTGUN_ROUND), 0.8, 6, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.HEAVY_AMMO), 1, 4, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.HEAVY_AMMO), 0.7, 4, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.HEAVY_AMMO), 0.7, 4, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.HEAVY_AMMO), 0.7, 4, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ENERGY), 1, 16, 4));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ENERGY), 0.6, 16, 4));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ENERGY), 0.6, 16, 4));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.MAGIC_ESSENCE), 1, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.MAGIC_ESSENCE), 0.5, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.MAGIC_ESSENCE), 0.5, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.MAGIC_ESSENCE), 0.5, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ROCKET_AMMO), 1, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ROCKET_AMMO), 0.2, 1, 1));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.ROCKET_AMMO), 0.2, 1, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 1, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 1, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 0.7, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 0.7, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 0.7, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 0.7, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(2), 0.3, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(2), 0.3, 1));
    }

    private final long TIME_BETWEEN_ATTACKS;

    public TheBoss(long timeBetweenAttacks) {
        super(EntityList.THEBOSS, DEFAULT_HEALTH, DEFAULT_MOVEMENT_FORCE, DEFAULT_SIZE, DEFAULT_DROPS, DEFAULT_SCORE_ON_KILL, new TheBossAI(timeBetweenAttacks),
                DEFAULT_MASS);

        this.TIME_BETWEEN_ATTACKS = timeBetweenAttacks;
    }

    @Override
    EnemyAI getNewAI() {
        return new TheBossAI(TIME_BETWEEN_ATTACKS);
    }
}
