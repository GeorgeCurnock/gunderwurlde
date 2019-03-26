package server.engine.state.item.weapon.gun;

import server.engine.state.entity.projectile.FireBullet;
import server.engine.state.entity.projectile.Projectile;
import shared.lists.AmmoList;
import shared.lists.ItemList;

public class FireGun extends ProjectileGun {
    public static final ItemList NAME = ItemList.FIRE_GUN;
    public static final int DEFAULT_CLIP_SIZE = 10;
    public static final int DEFAULT_RELOAD_TIME = 2800;
    public static final int DEFAULT_AMMO_PER_SHOT = 1;
    public static final int DEFAULT_PROJECTILES_PER_SHOT = 1;
    public static final Projectile DEFAULT_PROJECTILE = new FireBullet();
    public static final AmmoList DEFAULT_AMMO_TYPE = AmmoList.MAGIC_ESSENCE;
    public static final int DEFAULT_SPREAD = 0;
    public static final int DEFAULT_COOL_DOWN = 500;
    public static final int DEFAULT_ACCURACY = 1;

    public FireGun() {
        super(NAME, DEFAULT_CLIP_SIZE, DEFAULT_RELOAD_TIME, DEFAULT_AMMO_PER_SHOT,
                DEFAULT_PROJECTILE, DEFAULT_AMMO_TYPE, DEFAULT_SPREAD, DEFAULT_COOL_DOWN, DEFAULT_PROJECTILES_PER_SHOT, DEFAULT_ACCURACY);
    }

}
