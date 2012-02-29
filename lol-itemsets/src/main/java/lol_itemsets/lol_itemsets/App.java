package lol_itemsets.lol_itemsets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

	public class ChampComparator implements Comparator<ChampData> {

		@Override
		public int compare(ChampData o1, ChampData o2) {
			return o1.getName().compareTo(o2.getName());
		}

	}

	public class ItemNameComparator implements Comparator<ItemData> {

		@Override
		public int compare(ItemData o1, ItemData o2) {
			return o1.getName().compareTo(o2.getName());
		}

	}

	private final Random random = new Random();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		AppConfig config = new AppConfig();
		ItemList itemlist = null;
		ChampList champlist = null;
		try {
			itemlist = new ItemList(config);
			champlist = new ChampList(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		Group root = new Group();
		root.autosize();
		Scene scene = new Scene(root, 800, 600, Color.WHITE);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		BorderPane mainPane = new BorderPane();
		Pane topPane = new Pane();
		topPane.setPrefWidth(800);
		topPane.setPrefHeight(80);

		Image emptySlot = null;
		try {
			emptySlot = new Image(new FileInputStream(new File(config.getDataDir(), "EmptySlot.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		TaggedImageView[] selectedItems = new TaggedImageView[6];
		int slotY = 8;
		int slotX = 8;
		for (int i = 0; i < selectedItems.length; ++i) {
			TaggedImageView slot = new TaggedImageView();
			slot.setImage(emptySlot);
			slot.setX(slotX);
			slot.setY(slotY);
			slot.setFitWidth(64);
			slot.setFitHeight(64);
			slotX += 8 + 64;
			selectedItems[i] = slot;
		}

		ChoiceBox<ChampData> champChooser = new ChoiceBox<ChampData>();
		List<ChampData> sortedChamps = new ArrayList<ChampData>(champlist.getChamps());
		Collections.sort(sortedChamps, new ChampComparator());
		champChooser.getItems().addAll(sortedChamps);
			
		champChooser.setLayoutX(slotX + 8);
		champChooser.setLayoutY(8);
		
		
		
		Button buildButton = new Button("Save configuration");
		buildButton.setLayoutX(slotX + 8);
		
		buildButton.setOnMouseClicked(new BuildButtonHandler(config, selectedItems, champChooser));
		
		Button clearConfigButton = new Button("Clear configuration");
		clearConfigButton.setOnMouseClicked(new ClearConfigHandler(config, champChooser));
		
		
		topPane.getChildren().addAll(selectedItems);
		topPane.getChildren().addAll(champChooser, buildButton, clearConfigButton);

		

		ItemSet itemset = new ItemSet();

		mainPane.setTop(topPane);

		ItemMouseEventHandler itemMouseHandler = new ItemMouseEventHandler(root, selectedItems, 800, 600);

		List<ItemData> sortedItemList = new ArrayList<ItemData>(itemlist.getItems());
		Collections.sort(sortedItemList, new ItemNameComparator());

		List<TaggedImageView> itemImages = new LinkedList<TaggedImageView>();
		for (ItemData item : sortedItemList) {
			TaggedImageView iv = new TaggedImageView();
			iv.setImage(item.getImage());
			iv.setFitHeight(64);
			iv.setFitWidth(64);
			iv.setProperty("itemdata", item);
			iv.setOnMouseExited(itemMouseHandler);
			iv.setOnMouseEntered(itemMouseHandler);
			iv.setOnMouseMoved(itemMouseHandler);
			iv.setOnMouseDragged(itemMouseHandler);
			iv.setOnMouseReleased(itemMouseHandler);
			itemImages.add(iv);
		}

		TilePane tiles = new TilePane(10, 10);
		tiles.autosize();
		tiles.setPadding(new Insets(10));
		tiles.setPrefSize(800, 520);
		tiles.getChildren().addAll(itemImages);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.autosize();
		scrollPane.setPrefSize(800, 520);
		scrollPane.setContent(tiles);

		mainPane.setCenter(scrollPane);

		root.getChildren().add(mainPane);

		primaryStage.show();

		champChooser.setPrefWidth(buildButton.getWidth());
		buildButton.setLayoutY(champChooser.getHeight() + 16);
		clearConfigButton.setLayoutX(buildButton.getLayoutX() + buildButton.getWidth() + 8);
		clearConfigButton.setLayoutY(champChooser.getHeight() + 16);
	}

	private float random() {
		return random.nextFloat();
	}
}
