package lol_itemsets.lol_itemsets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

public class ChampData {
	private BufferedImage face;

	private String name;

	public ChampData(Map<String, String> champMap, File imgDir) throws IOException {
		setName(champMap.get("name"));
		setFace(ImageIO.read(new File(imgDir, champMap.get("face"))));
	}
	
	public BufferedImage getFace() {
		return face;
	}

	public void setFace(BufferedImage face) {
		this.face = face;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
