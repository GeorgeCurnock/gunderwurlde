package server.engine.state.entity.enemy;

import server.engine.ai.enemyAI.EnemyAI;
import server.engine.state.entity.Entity;
import server.engine.state.entity.LivingEntity;
import shared.lists.EntityList;
import shared.lists.Team;

import java.util.LinkedHashSet;

public abstract class Enemy extends LivingEntity {
    protected final LinkedHashSet<Drop> drops;
    protected EnemyAI ai;
    protected int scoreOnKill;


    Enemy(int maxHealth, double acceleration, EntityList entityListName, int size, LinkedHashSet<Drop> drops, int scoreOnKill, EnemyAI ai, double mass) {
        super(maxHealth, acceleration, entityListName, size, mass);
        this.drops = drops;
        this.entityListName = entityListName;
        this.scoreOnKill = scoreOnKill;
        this.ai =ai;
    }

    abstract EnemyAI getNewAI();

    public EnemyAI getAI() {
        return ai;
    }
    
    public void setAI(EnemyAI ai) {
        this.ai = ai;
    }

    public int getScoreOnKill() {
        return scoreOnKill;
    }

    public LinkedHashSet<Drop> getDrops() {
        return drops;
    }

    @Override
    public Entity makeCopy(){
        return new Zombie(entityListName, maxHealth, acceleration, size, drops, scoreOnKill, getNewAI(), mass);
    }

    @Override
    public Team getTeam() {
        return Team.ENEMY;
    }

}
