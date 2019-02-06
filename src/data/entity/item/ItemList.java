package data.entity.item;

import data.entity.EntityList;
import data.entity.item.weapon.gun.AmmoList;

public enum ItemList {
    PISTOL(EntityList.PISTOL),
    BASIC_AMMO(EntityList.AMMO_CLIP);

    private String spritePath;
    private EntityList entityName;

    ItemList(EntityList entityName) {
        this.entityName = entityName;
        this.spritePath = entityName.getPath();
    }

    public AmmoList toAmmoList() { //only works if you know the item is definitely ammo TODO make this less error prone
        return AmmoList.valueOf(this.toString());
    }

    public String getSpritePath() {
        return spritePath;
    }

    public EntityList getEntityList() {
        return entityName;
    }
}

