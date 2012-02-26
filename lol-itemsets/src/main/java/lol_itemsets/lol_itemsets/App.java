package lol_itemsets.lol_itemsets;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

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
		try {
			itemlist = new ItemList(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		Group root = new Group();
		root.autosize();
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		
		ItemMouseEventHandler itemMouseHandler = new ItemMouseEventHandler(root);
		
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
		
		

		
		TilePane tiles = new TilePane(10,10);
		tiles.autosize();
		tiles.setPadding(new Insets(10));
		tiles.setPrefSize(800, 600);
		tiles.getChildren().addAll(itemImages);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.autosize();
		scrollPane.setPrefSize(800,600);
		scrollPane.setContent(tiles);


		root.getChildren().add(scrollPane);
		


		primaryStage.show();
	}

	private float random() {
		return random.nextFloat();
	}
}
