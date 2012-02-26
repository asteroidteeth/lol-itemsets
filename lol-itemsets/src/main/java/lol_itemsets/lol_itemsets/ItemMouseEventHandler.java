package lol_itemsets.lol_itemsets;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ItemMouseEventHandler implements EventHandler<MouseEvent> {

	private final int tooltipWidth = 150;
	private final int tooltipHeight = 40;

	private Group root;



	public ItemMouseEventHandler(Group root) {
		this.root = root;
	}

	@Override
	public void handle(MouseEvent event) {
		@SuppressWarnings("unchecked")
		EventType<MouseEvent> type = (EventType<MouseEvent>) event
				.getEventType();

		if (event.isPrimaryButtonDown()) {
			dragTooltip(event);
		} else {
			if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				eraseTooltip(event);
			} else if (type.equals(MouseEvent.MOUSE_ENTERED)) {
				drawTooltip(event);
			} else if (type.equals(MouseEvent.MOUSE_EXITED)) {
				eraseTooltip(event);
			} else if (type.equals(MouseEvent.MOUSE_MOVED)) {
				moveTooltip(event);
			}
		}

	}

	private void dragTooltip(MouseEvent event) {
		root.getChildren().remove(TooltipState.tooltip);
		root.getChildren().remove(TooltipState.draggedImage);

		TaggedImageView imageView = (TaggedImageView) event.getSource();
		ItemData data = (ItemData) imageView.getProperty("itemdata");
		
		TooltipState.draggedImage = new ImageView();
		TooltipState.draggedImage.setImage(imageView.getImage());
		
		TooltipState.itemData = data;

		TilePane parent = (TilePane) imageView.getParent();

		Coords coords = calculateCoords(event, parent);
		Coords imgCoords = calculateImageCoords(event, parent);
		TooltipState.draggedImage.setX(imgCoords.x);
		TooltipState.draggedImage.setY(imgCoords.y);
		

		root.getChildren().add(TooltipState.draggedImage);
		root.getChildren().add(TooltipState.tooltip);

		Rectangle node = (Rectangle) TooltipState.tooltip.getChildren().get(0);
		node.setX(coords.x);
		node.setY(coords.y);
		System.out.println("Moved: " + coords.x + ", " + coords.y);
	}

	private void moveTooltip(MouseEvent event) {
		root.getChildren().remove(TooltipState.tooltip);

		TaggedImageView imageView = (TaggedImageView) event.getSource();

		TilePane parent = (TilePane) imageView.getParent();

		ItemData data = (ItemData) imageView.getProperty("itemdata");
		Coords coords = calculateCoords(event, parent);

		Rectangle node = (Rectangle) TooltipState.tooltip.getChildren().get(0);
		node.setX(coords.x);
		node.setY(coords.y);
		root.getChildren().add(TooltipState.tooltip);

		System.out.println("Moved: " + coords.x + ", " + coords.y);

	}

	private void drawTooltip(MouseEvent event) {
		TaggedImageView imageView = (TaggedImageView) event.getSource();

		TilePane parent = (TilePane) imageView.getParent();

		ItemData data = (ItemData) imageView.getProperty("itemdata");

		Coords coords = calculateCoords(event, parent);

		System.out.println("At: " + coords.x + ", " + coords.y);

		Rectangle tooltipRect = new Rectangle(tooltipWidth, tooltipHeight);
		tooltipRect.setFill(Color.YELLOW);
		tooltipRect.setX(coords.x);
		tooltipRect.setY(coords.y);
		// Text label = new Text(x+5,y+10,data.getName());
		TooltipState.tooltip = new Group(/* label, */tooltipRect);

		root.getChildren().add(TooltipState.tooltip);

		System.out.println("Moused over " + data.getName());
		System.out.println(event.getEventType().toString());

	}

	private Coords calculateCoords(MouseEvent event, TilePane parent) {
		Coords coords = new Coords();
		coords.x = (int) (event.getSceneX() + 15);
		coords.y = (int) (event.getSceneY() + 15);
		System.out.println("At: " + coords.x + ", " + coords.y);

		if (coords.x + tooltipWidth > parent.getWidth()) {
			coords.x -= 15 + tooltipWidth;
		}
		return coords;
	}

	private Coords calculateImageCoords(MouseEvent event, TilePane parent) {
		Coords coords = new Coords();
		coords.x = (int) (event.getSceneX() - 32);
		coords.y = (int) (event.getSceneY() - 32);

		return coords;
	}

	private void eraseTooltip(MouseEvent event) {
		root.getChildren().remove(TooltipState.draggedImage);
		root.getChildren().remove(TooltipState.tooltip);
		TooltipState.draggedImage = null;
		TooltipState.tooltip = null;
		TooltipState.dropped = true;

	}

	private class Coords {
		int x;
		int y;
	}

}
