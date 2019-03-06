package mapeditor;

import java.util.EnumSet;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import server.engine.state.map.tile.Tile;
import shared.Constants;
import shared.lists.EntityList;
import shared.lists.MapEditorAssetList;
import shared.lists.TileState;
import shared.lists.TileTypes;

public class MapEditor {
	
	private MapEditor mapEditor = this;
	private String fileName;
	private Stage stage;
	private StackPane root;
	private Scene scene;
	private StackPane background;
	private Rectangle mapViewerBackground;
	private Rectangle infoBackground;
	private GridPane mainViewer;
	private Canvas mapCanvas;
	private Image mapSnapshot;
	private StackPane infoViewer;
	private VBox info;
	//private HBox tileIDInfo;
	//private Label tileIDLabel1;
	//private Label tileIDLabel2;
	private ImageView tileImageView;
	private HBox tileTypeInfo;
	private Label tileTypeLabel1;
	private Label tileTypeLabel2;
	private HBox tileStateInfo;
	private Label tileStateLabel1;
	private Label tileStateLabel2;
	private HBox frictionInfo;
	private Label frictionLabel1;
	private Label frictionLabel2;
	private HBox bounceInfo;
	private Label bounceLabel1;
	private Label bounceLabel2;
	private MapSizeOption mapSizeOption;
	private int mapWidth;
	private int mapHeight;
	private GraphicsContext mapGc;
	private Tile[][] mapTiles;
	private HashMap<MapEditorAssetList, Image> mapEditorAssets;
	private HashMap<TileTypes, Image> tileSprite;
	private boolean keysActivated;
	
	// New map
	public MapEditor() {
		this.init();
	}
	
	// Open map
	public MapEditor(String fileName) {
		this.fileName = fileName;
		this.init();
	}
	
	public Tile[][] getMapTiles() {
		return this.mapTiles;
	}
	
	public HashMap<TileTypes, Image> getTileSprite() {
		return this.tileSprite;
	}
	
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	// Initialize
	private void init() {
		keysActivated = false;
		loadAssets();
		
		stage = new Stage();
		if(fileName != null) {
			stage.setTitle(fileName);
		}
		else {
			stage.setTitle("New Map");
		}
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.centerOnScreen();
        
        root = new StackPane();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        root.setAlignment(Pos.CENTER);
		
        // Background
		background = new StackPane();
		root.getChildren().add(background);
		background.setAlignment(Pos.CENTER);
		
		// > Map Viewer Background
		mapViewerBackground = new Rectangle(scene.getWidth(), scene.getHeight());
		mapViewerBackground.setFill(Color.GREY);
		background.getChildren().add(mapViewerBackground);
		
		// Main Viewer
		mainViewer = new GridPane();
		root.getChildren().add(mainViewer);
		mainViewer.setAlignment(Pos.CENTER);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth(500);
		col1.setHalignment(HPos.CENTER);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPrefWidth(300);
		col2.setHalignment(HPos.CENTER);
		mainViewer.getColumnConstraints().addAll(col1,col2);
		
		// > Map Viewer
		mapCanvas = new Canvas(0, 0);
		mainViewer.add(mapCanvas, 0, 0);
		mapCanvas.requestFocus();
		
		// > Info Viewer
		infoViewer = new StackPane();
		mainViewer.add(infoViewer, 1, 0);
		
		// > > Info Viewer Background
		infoBackground = new Rectangle(mainViewer.getColumnConstraints().get(1).getPrefWidth(), scene.getHeight());
		infoViewer.getChildren().add(infoBackground);
		infoBackground.setFill(Color.DARKGRAY);
		infoBackground.setOpacity(0.75);
		
		// > > Info VBox
		info = new VBox();
		infoViewer.getChildren().add(info);
		info.setSpacing(10);
		info.setAlignment(Pos.CENTER);
		
		/*
		// > > Tile ID
		tileIDInfo = new HBox();
		info.getChildren().add(tileIDInfo);
		tileIDInfo.setSpacing(10);
		tileIDInfo.setAlignment(Pos.CENTER);
		tileIDLabel1 = new Label("Tile ID:");
		tileIDInfo.getChildren().add(tileIDLabel1);
		tileIDLabel2 = new Label();
		tileIDInfo.getChildren().add(tileIDLabel2);
		*/
		
		// > > Tile Image
		tileImageView = new ImageView();
		info.getChildren().add(tileImageView);
		
		// > > Tile Type
		tileTypeInfo = new HBox();
		info.getChildren().add(tileTypeInfo);
		tileTypeInfo.setSpacing(10);
		tileTypeInfo.setAlignment(Pos.CENTER);
		tileTypeLabel1 = new Label("Tile Type:");
		tileTypeInfo.getChildren().add(tileTypeLabel1);
		tileTypeLabel2 = new Label("-");
		tileTypeInfo.getChildren().add(tileTypeLabel2);
		
		// > > Tile State
		tileStateInfo = new HBox();
		info.getChildren().add(tileStateInfo);
		tileStateInfo.setSpacing(10);
		tileStateInfo.setAlignment(Pos.CENTER);
		tileStateLabel1 = new Label("Tile State:");
		tileStateInfo.getChildren().add(tileStateLabel1);
		tileStateLabel2 = new Label("-");
		tileStateInfo.getChildren().add(tileStateLabel2);
		
		// > > Friction
		frictionInfo = new HBox();
		info.getChildren().add(frictionInfo);
		frictionInfo.setSpacing(10);
		frictionInfo.setAlignment(Pos.CENTER);
		frictionLabel1 = new Label("Friction Coefficient:");
		frictionInfo.getChildren().add(frictionLabel1);
		frictionLabel2 = new Label("-");
		frictionInfo.getChildren().add(frictionLabel2);
		
		// > > Bounce
		bounceInfo = new HBox();
		info.getChildren().add(bounceInfo);
		bounceInfo.setSpacing(10);
		bounceInfo.setAlignment(Pos.CENTER);
		bounceLabel1 = new Label("Bounce Coefficient");
		bounceInfo.getChildren().add(bounceLabel1);
		bounceLabel2 = new Label("-");
		bounceInfo.getChildren().add(bounceLabel2);
		
		// > > > Map Size Option Button
		Button mapSizeOptionButton = new Button("Map Size");
		info.getChildren().add(mapSizeOptionButton);
		mapSizeOptionButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mapSizeOption = new MapSizeOption(mapEditor, mapEditorAssets, mapWidth, mapHeight);
			}
		});
		
		stage.show();
        
		stage.setOnCloseRequest(we -> {
			stage.close();
        });
	}
	
	// NOT DONE
	protected void drawMapTiles() {
		mainViewer.getChildren().remove(mapCanvas);
		mapCanvas = new Canvas(mapWidth*Constants.TILE_SIZE, mapHeight*Constants.TILE_SIZE);
		mainViewer.add(mapCanvas, 0, 0);
		
		Tile[][] oldMapTiles;
		mapGc = mapCanvas.getGraphicsContext2D();
		mapGc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
		mapGc.rect(0, 0, mapWidth*Constants.TILE_SIZE, mapHeight*Constants.TILE_SIZE);
		mapGc.setFill(Color.BLACK);
		mapGc.fill();
		mapGc.setLineWidth(1);
		mapGc.setStroke(Color.GREY);
		// Draw vertical grid lines
		for(int i = 0 ; i < mapWidth ; i++) {
			mapGc.strokeLine(i*Constants.TILE_SIZE, 0, i*Constants.TILE_SIZE, mapHeight*Constants.TILE_SIZE);
		}
		// Draw horizontal grid lines
		for(int i = 0 ; i < mapHeight ; i++) {
			mapGc.strokeLine(0, i*Constants.TILE_SIZE, mapWidth*Constants.TILE_SIZE, i*Constants.TILE_SIZE);
		}
		
		if(mapTiles == null) {
			mapTiles = new Tile[mapWidth][mapHeight];
			oldMapTiles = new Tile[0][0];
		}
		else {
			oldMapTiles = mapTiles;
		}
		
		for(int i = 0 ; i < mapTiles.length - 1 ; i++) {
			for(int j = 0 ; j < mapTiles[0].length - 1 ; j++) {
				if(i < oldMapTiles.length - 1 && j < oldMapTiles.length - 1) {
				}
				else {
				}
			}
		}
		
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		mapSnapshot = mapCanvas.snapshot(params, null);
		
		if(!keysActivated()) {
			activateKeys();
		}
		
		mapCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectTile((int)event.getX()/32, (int)event.getY()/32);
				if(event.getClickCount() == 2) {
					TileSelector tileSelector = new TileSelector(mapEditor, (int)event.getX()/32, (int)event.getY()/32);
				}
			}
		});
		
		resetFocus();
	}
	
	private boolean keysActivated() {
		return keysActivated;
	}
	
	// Activate keyboard control
	private void activateKeys() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				moveCamera(event.getCode());
			}
		});
	}
	
	// Move camera
	private void moveCamera(KeyCode key) {
		switch(key) {
			case W:
				mapCanvas.setTranslateY(mapCanvas.getTranslateY() - (double)Constants.TILE_SIZE);
				break;
			case A:
				mapCanvas.setTranslateX(mapCanvas.getTranslateX() - (double)Constants.TILE_SIZE);
				break;
			case S:
				mapCanvas.setTranslateY(mapCanvas.getTranslateY() + (double)Constants.TILE_SIZE);
				break;
			case D:
				mapCanvas.setTranslateX(mapCanvas.getTranslateX() + (double)Constants.TILE_SIZE);
				break;
		}
		infoViewer.toFront();
	}
	
	private void resetFocus() {
		mapCanvas.requestFocus();
	}
	
	// Load needed assets
	private void loadAssets() {
		loadMapEditorAssets();
		loadTileSprite();
	}
	
	// Load assets used specifically for the editor
	private void loadMapEditorAssets() {
		mapEditorAssets = new HashMap<MapEditorAssetList, Image>();
		EnumSet.allOf(MapEditorAssetList.class).forEach(mapEditorAssetList -> mapEditorAssets.put(mapEditorAssetList, new Image(mapEditorAssetList.getPath())));
	}
	
	// Load tile sprites
	private void loadTileSprite() {
		tileSprite = new HashMap<TileTypes, Image>();
		EnumSet.allOf(TileTypes.class).forEach(tileTypes -> tileSprite.put(tileTypes, new Image(tileTypes.getEntityListName().getPath())));
	}
	
	// Tile selection
	private void selectTile(int x, int y) {
		System.out.println("x: " + x + " y: " + y);
		mapGc.drawImage(mapSnapshot, 0, 0);
		mapGc.setStroke(Color.YELLOW);
		mapGc.strokeLine(x*Constants.TILE_SIZE, y*Constants.TILE_SIZE, (x + 1)*Constants.TILE_SIZE, y*Constants.TILE_SIZE);
		mapGc.strokeLine(x*Constants.TILE_SIZE, (y + 1)*Constants.TILE_SIZE, (x + 1)*Constants.TILE_SIZE, (y + 1)*Constants.TILE_SIZE);
		mapGc.strokeLine(x*Constants.TILE_SIZE, y*Constants.TILE_SIZE, x*Constants.TILE_SIZE, (y + 1)*Constants.TILE_SIZE);
		mapGc.strokeLine((x + 1)*Constants.TILE_SIZE, y*Constants.TILE_SIZE, (x + 1)*Constants.TILE_SIZE, (y + 1)*Constants.TILE_SIZE);
		displayTileInfo(x, y);
	}
	
	// Display tile info on the right
	private void displayTileInfo(int x, int y) {
		Tile tile = mapTiles[x][y];
		if(tile != null) {
			setDisplayTileInfo(tile.getType(), tile.getState(), tile.getFrictionCoefficient(), tile.getBounceCoefficient());
		}
		else {
			tileTypeLabel2.setText("-");
			tileStateLabel2.setText("-");
			frictionLabel2.setText("-");
			bounceLabel2.setText("-");
		}
	}
	
	private void setDisplayTileInfo(TileTypes tileType, TileState tileState, double frictionCoefficient, double bounceCoefficient) {
		//tileIDLabel2.setText(Character.toString(tileID));
		tileImageView.setImage(tileSprite.get(tileType));
		tileTypeLabel2.setText(tileType.getEntityListName().toString());
		tileStateLabel2.setText(tileState.toString());
		frictionLabel2.setText(Double.toString(frictionCoefficient));
		bounceLabel2.setText(Double.toString(bounceCoefficient));
	}
	
}