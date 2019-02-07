package client.data.entity;

import data.Pose;
import data.entity.EntityList;

import java.io.Serializable;

public class ItemDropView extends EntityView implements Serializable {
    private static final long serialVersionUID = 1L;

    public ItemDropView(Pose pose, int size, EntityList name) {
        super(pose, size, name);
    }

}