package lol_itemsets.lol_itemsets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class BuildButtonHandler implements EventHandler<MouseEvent> {

	private AppConfig config;
	private ChoiceBox<ChampData> champChooser;
	private TaggedImageView[] slots;

	public BuildButtonHandler(AppConfig config, TaggedImageView[] selectedItems, ChoiceBox<ChampData> champChooser) {
		this.config = config;
		this.slots = selectedItems;
		this.champChooser = champChooser;
	}

	@Override
	/**
	 * When the build button is clicked, the ini for the selected hero is output.
	 * No error handling right now. There is no result if either all the slots are not filled or the champion is not selected.
	 */
	public void handle(MouseEvent event) {
		ChampData champ = champChooser.getSelectionModel().getSelectedItem();
		if (champ == null) return;

		
		ItemData[] itemdata = new ItemData[6];
		for (int i = 0; i < 6; ++i) {
			ItemData d =  (ItemData) slots[i].getProperty("itemdata");
			if (d == null) return;
			itemdata[i] = d;
		}
		
		ItemSet set = new ItemSet(itemdata);
		String iniContent = set.buildIni();
		
		String name = champ.getFileName();
		File outputFile = config.getClassicChampIni(name);
		
		try {
			FileWriter writer = new FileWriter(outputFile);
			System.out.print(iniContent);
			writer.write(iniContent);
			writer.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
	}

}
