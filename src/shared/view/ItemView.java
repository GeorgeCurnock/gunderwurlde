package shared.view;

import java.io.Serializable;

import shared.lists.AmmoList;
import shared.lists.ItemList;

public class ItemView implements Serializable {
    private static final long serialVersionUID = 1L;
    protected ItemList name;
    protected String pathToGraphic;
    protected AmmoList ammoType;
    protected int clipSize;
    protected int ammoInClip;

    public ItemView(ItemList name, AmmoList ammoType, int clipSize, int ammoInClip) {
        this.name = name;
        this.ammoType = ammoType;
        this.clipSize = clipSize;
        this.ammoInClip = ammoInClip;

        switch (name) {
        case BASIC_AMMO:
            break;
        case PISTOL:
            this.pathToGraphic = "file:assets/img/items/pistol.png";
            break;
        }
    }

    public ItemList getItemListName() {
        return name;
    }

    public String getPathToGraphic() {
        return pathToGraphic;
    }

    public AmmoList getAmmoType() {
        return ammoType;
    }

    public int getClipSize() {
        return clipSize;
    }

    public int getAmmoInClip() {
        return ammoInClip;
    }
}