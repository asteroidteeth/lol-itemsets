package lol_itemsets.lol_itemsets;

import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressedHandler implements EventHandler<KeyEvent> {

	private Timeline timeline;

	public KeyPressedHandler(Timeline timeline) {
		this.timeline = timeline;
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.SPACE) {
			Status status = timeline.getStatus();
			if (status.equals(Status.RUNNING)) {
				timeline.pause();
			} else if (status.equals(Status.STOPPED) || status.equals(Status.PAUSED)) {
				timeline.play();
			}
			
		}
		
	}
 

}
