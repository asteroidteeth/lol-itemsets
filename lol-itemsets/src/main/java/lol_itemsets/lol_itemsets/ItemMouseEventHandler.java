package lol_itemsets.lol_itemsets;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class ItemMouseEventHandler implements EventHandler<MouseEvent> {

	private final int tooltipWidth = 150;
	private final int tooltipHeight = 40;

	private Group root;
	private int width;
	private int height;
	private TaggedImageView[] slots;

	public ItemMouseEventHandler(Group root, TaggedImageView[] slots, int width, int height) {
		this.root = root;
		this.slots = slots;
		this.width = width;
		this.height = height;
	}

	@Override
	public void handle(MouseEvent event) {
		@SuppressWarnings("unchecked")
		EventType<MouseEvent> type = (EventType<MouseEvent>) event.getEventType();

		if (event.isPrimaryButtonDown()) {
			dragTooltip(event);
		} else {
			if (type.equals(MouseEvent.MOUSE_RELEASED)) {
				dropTooltip(event);
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
		TooltipState.dragging = true;

		TilePane parent = (TilePane) imageView.getParent();

		Coords coords = calculateCoords(event, parent);
		Coords imgCoords = calculateImageCoords(event, parent);
		TooltipState.draggedImage.setX(imgCoords.x);
		TooltipState.draggedImage.setY(imgCoords.y);

		root.getChildren().add(TooltipState.draggedImage);
		root.getChildren().add(TooltipState.tooltip);

		for (Node tooltipPart : TooltipState.tooltip.getChildren()) {
			if (tooltipPart instanceof Rectangle) {
				((Rectangle) tooltipPart).setX(coords.x);
				((Rectangle) tooltipPart).setY(coords.y);
			} else if (tooltipPart instanceof Text) {
				((Text) tooltipPart).setX(coords.x + 5);
				((Text) tooltipPart).setY(coords.y + 10);
			}
		}
		System.out.println("Moved: " + coords.x + ", " + coords.y);
	}

	private void moveTooltip(MouseEvent event) {
		root.getChildren().remove(TooltipState.tooltip);

		TaggedImageView imageView = (TaggedImageView) event.getSource();

		TilePane parent = (TilePane) imageView.getParent();

		ItemData data = (ItemData) imageView.getProperty("itemdata");
		Coords coords = calculateCoords(event, parent);

		for (Node tooltipPart : TooltipState.tooltip.getChildren()) {
			if (tooltipPart instanceof Rectangle) {
				((Rectangle) tooltipPart).setX(coords.x);
				((Rectangle) tooltipPart).setY(coords.y);
			} else if (tooltipPart instanceof Text) {
				((Text) tooltipPart).setX(coords.x + 5);
				((Text) tooltipPart).setY(coords.y + 10);
			}
		}
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
		Text label = new Text(coords.x + 5, coords.y + 10, data.getName());
		TooltipState.tooltip = new Group(/* label, */tooltipRect, label);

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
		if (coords.y + tooltipHeight > height) {
			System.out.println("TOO LOW");
			coords.y -= 15 + tooltipHeight;
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
		TooltipState.dragging = false;
		TooltipState.dropped = false;

	}

	private void dropTooltip(MouseEvent event) {
		Point2D dropCoords = new Point2D(event.getSceneX(), event.getSceneY());
		for (TaggedImageView slot : slots) {
			if (slot.contains(dropCoords)) {
				slot.setImage(TooltipState.itemData.getImage());
				slot.setProperty("itemdata", TooltipState.itemData);
			}
		}
		root.getChildren().remove(TooltipState.draggedImage);
		root.getChildren().remove(TooltipState.tooltip);
		System.out.println("Dropped " + TooltipState.itemData.getName());
		TooltipState.draggedImage = null;
		TooltipState.tooltip = null;
		TooltipState.dragging = false;
	}

	private class Coords {
		int x;
		int y;
	}

}
