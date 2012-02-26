package lol_itemsets.lol_itemsets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class ChampData {
	private Image face;

	private String name;

	public ChampData(Map<String, String> champMap, File imgDir) throws IOException {
		setName(champMap.get("name"));
		setFace(new Image(new FileInputStream(new File(imgDir, champMap.get("face")))));
	}
	
	public Image getFace() {
		return face;
	}

	public void setFace(Image face) {
		this.face = face;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
