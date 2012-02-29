package lol_itemsets.lol_itemsets;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class ClearConfigHandler implements EventHandler<MouseEvent> {

	private AppConfig config;
	private ChoiceBox<ChampData> champChooser;

	public ClearConfigHandler(AppConfig config, ChoiceBox<ChampData> champChooser) {
		this.config = config;
		this.champChooser = champChooser;
	}

	@Override
	public void handle(MouseEvent event) {
		String name = champChooser.getSelectionModel().getSelectedItem().getFileName();
		File file = config.getClassicChampIni(name);
		file.delete();
		
	}

}
