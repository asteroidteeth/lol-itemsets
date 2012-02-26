package lol_itemsets.lol_itemsets;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.ImageView;

public class TaggedImageView extends ImageView {
	private Map<String,Object> properties = new HashMap<String,Object>();
	
	public Object getProperty(String key) {
		return properties.get(key);
	}
	
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}
}
