package data;

import java.util.LinkedHashMap;

public class ClientRequests {
	protected LinkedHashMap<Integer,Request> playerRequests;
	
	public ClientRequests(int numOfPlayers) {
		for (int i=0;i<numOfPlayers;i++) {
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
		} else return false;
	}
	
	public boolean playerRequestSelectItem(int playerID, int itemPosition) {
		if (playerRequests.containsKey(playerID)) {
			playerRequests.get(playerID).setSelectItem(itemPosition);;
			return true;
		} else return false;
	}
	
	public boolean playerRequestShoot(int playerID) {
		if (playerRequests.containsKey(playerID)) {
			playerRequests.get(playerID).requestShoot();
			return true;
		} else return false;
	}
	
	public boolean playerRequestReload(int playerID) {
		if (playerRequests.containsKey(playerID)) {
			playerRequests.get(playerID).requestReload();
			return true;
		} else return false;
	}
	
	public void playerRequestLeave(int playerID) {
		playerRequests.remove(playerID);
	}
}
