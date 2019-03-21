package server.engine.state.entity.enemy;

import server.engine.ai.enemyAI.ZombieAI;
import server.engine.state.item.weapon.gun.Ammo;
import server.engine.state.map.tile.Tile;
import shared.lists.AmmoList;
import shared.lists.EntityList;

import java.util.LinkedHashSet;

public class RunnerZombie extends Zombie {

    public static final int DEFAULT_HEALTH = 1;
    public static final double DEFAULT_ACCELERATION = Tile.TILE_SIZE * 1.5;
    public static final int DEFAULT_SCORE_ON_KILL = 15;
    public static final double DEFAULT_MASS = 1;
    public static final LinkedHashSet<Drop> DEFAULT_DROPS = new LinkedHashSet<>();

    static {
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.BASIC_AMMO), 0.4, 4, 2));
        DEFAULT_DROPS.add(new Drop(new Ammo(AmmoList.SHOTGUN_ROUND), 0.05, 2, 1));
    }

    public RunnerZombie(int speed){
        super(EntityList.RUNNER, DEFAULT_HEALTH, DEFAULT_ACCELERATION * speed, DEFAULT_SIZE, DEFAULT_DROPS, DEFAULT_SCORE_ON_KILL, new ZombieAI(), DEFAULT_MASS);
    }

}
