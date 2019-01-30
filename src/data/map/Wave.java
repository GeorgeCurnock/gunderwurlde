package data.map;

import data.entity.enemy.Enemy;

public class Wave implements Comparable<Wave> {
    protected long startTime;
    protected int spawnInterval; // interval between spawns won't be able to be quicker than game engine speed
                                 // obvs
    protected int amountPerSpawn;
    protected int totalToSpawn;
    protected Enemy enemyToSpawn;

    Wave(long startTime, int spawnInterval, Enemy enemyToSpawn, int amountPerSpawn, int totalToSpawn) {
        this.startTime = startTime;
        this.spawnInterval = spawnInterval;
        this.enemyToSpawn = enemyToSpawn;
        this.amountPerSpawn = amountPerSpawn;
        this.totalToSpawn = totalToSpawn;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getSpawnInterval() {
        return spawnInterval;
    }

    public Enemy getEnemyToSpawn() {
        return enemyToSpawn;
    }

    public int getAmountPerSpawn() {
        return amountPerSpawn;
    }

    public int getTotalToSpawn() {
        return totalToSpawn;
    }

    @Override
    public int compareTo(Wave w) {
        if (this.startTime < w.startTime)
            return -1;
        else if (this.startTime == w.startTime)
            return 0;
        else
            return 1;
    }

}
