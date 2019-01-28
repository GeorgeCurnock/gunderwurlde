package server.request;

import java.util.LinkedHashMap;
import data.Pose;

public class ClientRequests {
    protected LinkedHashMap<Integer, Request> playerRequests;

    public ClientRequests(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++) {
            playerRequests.put(i, new Request());
        }
    }

    public LinkedHashMap<Integer, Request> getPlayerRequests() {
        return playerRequests;
    }

    public boolean playerRequestPose(int playerID, Pose pose) {
        if (playerRequests.containsKey(playerID)) {
            playerRequests.get(playerID).setPose(pose);
            return true;
        } else
            return false;
    }

    public boolean playerRequestSelectItem(int playerID, int itemPosition) {
        if (playerRequests.containsKey(playerID)) {
            playerRequests.get(playerID).setSelectItem(itemPosition);
            ;
            return true;
        } else
            return false;
    }

    public boolean playerRequestShoot(int playerID) {
        if (playerRequests.containsKey(playerID)) {
            playerRequests.get(playerID).requestShoot();
            return true;
        } else
            return false;
    }

    public boolean playerRequestReload(int playerID) {
        if (playerRequests.containsKey(playerID)) {
            playerRequests.get(playerID).requestReload();
            return true;
        } else
            return false;
    }

    // use once player leave has been processed
    public boolean playerRequestRemove(int playerID) {
        if (playerRequests.remove(playerID) != null)
            return true;
        else
            return false;
    }

    public boolean playerRequestLeave(int playerID) {
        if (playerRequests.containsKey(playerID)) {
            playerRequests.get(playerID).requestLeave();
            return true;
        } else
            return false;
    }
}
