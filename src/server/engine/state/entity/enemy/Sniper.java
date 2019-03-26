package server.engine.state.entity.enemy;

import java.util.LinkedHashSet;

import server.engine.ai.enemyAI.EnemyAI;
import server.engine.ai.enemyAI.SniperAI;
import server.engine.state.item.pickup.Ammo;
import server.engine.state.item.pickup.Health;
import shared.lists.AmmoList;
import shared.lists.EntityList;

public class Sniper extends Zombie {

    public static final int DEFAULT_HEALTH = 1;
    public static final double DEFAULT_MOVEMENT_FORCE = 4;
    public static final int DEFAULT_SCORE_ON_KILL = 35;
    public static final LinkedHashSet<Drop> DEFAULT_DROPS = new LinkedHashSet<>();

    static {
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.4, 4, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.HEAVY_AMMO), 0.8, 3, 1));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(2), 0.03, 2));
        DEFAULT_DROPS.add(new Drop(Health.makeHealth(1), 0.2, 1));
    }

    private final int RANGE_TO_RUN_WAY;

    public Sniper(int rangeToRunAway) {
        super(EntityList.SNIPER, DEFAULT_HEALTH, DEFAULT_MOVEMENT_FORCE, DEFAULT_SIZE, DEFAULT_DROPS, DEFAULT_SCORE_ON_KILL, new SniperAI(rangeToRunAway),
                DEFAULT_MASS);

        this.RANGE_TO_RUN_WAY = rangeToRunAway;
    }

    @Override
    EnemyAI getNewAI() {
        return new SniperAI(RANGE_TO_RUN_WAY);
    }
}
