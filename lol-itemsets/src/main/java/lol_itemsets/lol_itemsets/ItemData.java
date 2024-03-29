package lol_itemsets.lol_itemsets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

public class ItemData {
	private Integer code;
	private Image image;
	private String link;
	private String mode;
	private String name;

	public ItemData(Map<String, String> itemMap, File imgDir) throws IOException {
		setCode(Integer.parseInt(itemMap.get("code")));
		setImage(new Image(new FileInputStream(new File(imgDir, itemMap.get("image")))));
		setLink(itemMap.get("link"));
		setMode(itemMap.get("mode"));
		setName(itemMap.get("name"));
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
