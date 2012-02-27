package lol_itemsets.lol_itemsets;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemSlotMouseHandler implements EventHandler<MouseEvent> {



	@Override
	public void handle(MouseEvent event) {
		@SuppressWarnings("unchecked")
		EventType<MouseEvent> type = (EventType<MouseEvent>) event
				.getEventType();
		
		if (type.equals(MouseEvent.MOUSE_ENTERED)) {
			itemDropped(event);
		}
		
	}

	private void itemDropped(MouseEvent event) {
		
		if (TooltipState.dropped) {
			ItemData slottedItem = TooltipState.itemData;
			
			TaggedImageView slot = (TaggedImageView) event.getSource();
			slot.contains(new Point2D(event.getX(), event.getY()));
			
			slot.setImage(slottedItem.getImage());
			slot.setProperty("itemdata", slottedItem);
		}
		
	}

}
