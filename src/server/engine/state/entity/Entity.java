package server.engine.state.entity;

import server.engine.state.map.tile.Tile;
import shared.Location;
import shared.Pose;
import shared.lists.EntityList;
import shared.lists.StatusEffect;

// Class for renderable entities
public abstract class Entity {
    public static final int MAX_SIZE = (3 * Tile.TILE_SIZE);
    
    protected Pose pose;
    protected EntityList entityListName;
    protected StatusEffect status; // TODO add to view
    protected int size;
    protected boolean cloaked; // "invisible"

    protected Entity(Pose pose, int size, EntityList entityListName) {
        this.pose = pose;
        this.size = size;
        this.entityListName = entityListName;
        this.cloaked = false;
    }
    
    protected Entity(int size, EntityList entityListName) {
        this (new Pose(), size, entityListName);
    }

    protected Entity(Location location, int size, EntityList entityListName) {
        this(new Pose(location), size, entityListName);
    }

    public boolean isCloaked() {
        return cloaked;
    }

    public void setCloaked(boolean cloaked) {
        this.cloaked = cloaked;
    }

    public StatusEffect getStatus() {
        return status;
    }

    public void setStatus(StatusEffect status) {
        this.status = status;
    }

    public Pose getPose() {
        return pose;
    }

    public EntityList getEntityListName() {
        return entityListName;
    }

    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public Location getLocation() {
        return pose;
    }

    public void setLocation(Location location) {
        this.pose = new Pose(location, this.pose.getDirection());
    }

    public int getSize() {
        return size;
    }

    public void changeSize(int amount) {
        if (amount <= -this.size) {
            this.size = 1;
        } else if ((amount + this.size) > MAX_SIZE) {
            this.size = MAX_SIZE;
        } else {
            this.size += amount;
        }
    }

    public void setSize(int size) {
        if (size <= 0) {
            this.size = 1;
        } else if (size > MAX_SIZE) {
            this.size = MAX_SIZE;
        } else {
            this.size = size;
        }
    }
    
}
