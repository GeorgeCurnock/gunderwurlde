package shared.lists;

// List of all entities as well as renderable objects
public enum EntityList {
    // Item
    PISTOL("file:assets/img/entity/item/pistol.png"),

    // Player
    PLAYER("file:assets/img/entity/player/player_1.png"),
    PLAYER_1("file:assets/img/entity/player/player_1.png"),
    PLAYER_2("file:assets/img/entity/player/player_2.png"),
    PLAYER_3("file:assets/img/entity/player/player_3.png"),
    PLAYER_4("file:assets/img/entity/player/player_4.png"),

    // Enemy
    ZOMBIE("file:assets/img/entity/enemy/zombie.png"),

    // Projectile
    BASIC_BULLET("file:assets/img/entity/projectile/basic_bullet.png"),

    // Tiles
    GRASS_TILE("file:assets/img/tiles/grass.png"),
    WOOD_TILE("file:assets/img/tiles/wood.png"),
    DEFAULT("file:assets/img/tiles/default.png"),

    // Other/Not real entities - some applicable to Item
    AMMO_CLIP("file:assets/img/other/ammo_clip.png"),
    HEART_FULL("file:assets/img/other/heart_full.png"),
    HEART_HALF("file:assets/img/other/heart_half.png"),
    HEART_LOST("file:assets/img/other/heart_lost.png");

    private String spritePath;

    EntityList(String spritePath) {
        this.spritePath = spritePath;
    }

    public String getPath() {
        return spritePath;
    }
}