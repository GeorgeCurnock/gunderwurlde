package server.game_engine;

import java.util.Random;

import client.GameRenderer;
import client.data.GameView;
import data.entity.player.Teams;
import data.map.MapList;
import javafx.application.Application;
import javafx.stage.Stage;
import server.request.ClientRequests;

public class TestEngine extends Application implements HasEngine {
    private ProcessGameState engine;
    private GameRenderer rend;
    private GameView view;
    private ClientRequests requests;

    private static final int LOOPS = 1000;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void updateGameView(GameView view) {
        this.view = view;
    }

    @Override
    public void removePlayer(int playerID) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void requestClientRequests() {
        if (requests != null) engine.setClientRequests(requests);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.engine = new ProcessGameState(this, MapList.MEADOW, "Bob");
        stage.setResizable(false);
        engine.start();
        engine.addPlayer("Bob2", Teams.RED);
        engine.addPlayer("Bob3", Teams.RED);
        engine.addPlayer("Bob4", Teams.RED);
        requests = new ClientRequests(4);
        loopDeDoop(stage);
        boolean firstRender = true;
        while (firstRender) {
            if (view != null) {
                stage.show();
                rend = new GameRenderer(stage, view, 0);
                firstRender = false;
                rend.run();
                System.out.println("Timer started");
                startTheTimer();
            } else {
                System.out.println("View is null");
            }
        }
    }

    private void loopDeDoop(Stage stage) {
        Random rand = new Random();
        new Thread() {
            public void run() {
                for (int i = 0; i < LOOPS; i++) {
                    requests.playerRequestFacing(0, rand.nextInt(360));
                    requests.playerRequestMovement(0, rand.nextInt(360));
                    requests.playerRequestShoot(0);
                    requests.playerRequestFacing(1, rand.nextInt(360));
                    requests.playerRequestMovement(1, rand.nextInt(360));
                    requests.playerRequestShoot(1);
                    requests.playerRequestFacing(2, rand.nextInt(360));
                    requests.playerRequestMovement(2, rand.nextInt(360));
                    requests.playerRequestShoot(2);
                    requests.playerRequestFacing(3, rand.nextInt(360));
                    requests.playerRequestMovement(3, rand.nextInt(360));
                    requests.playerRequestShoot(3);
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                engine.handlerClosing();
            }
        }.start();
    }

    private void startTheTimer() {
        new Thread() {
            public void run() {
                GameView oldGameView = view;
                for (int i = 0; i < LOOPS; i++) {
                    if (oldGameView != view) {
                        oldGameView = view;
                        rend.updateGameView(oldGameView);
                    }
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
