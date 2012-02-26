package lol_itemsets.lol_itemsets;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

public class ItemList {
	
	private Map<Integer, ItemData> items;
	
	public ItemList(AppConfig config) throws IOException {
		File itemListJson = config.getItemList();
		File itemImgDir = config.getItemImageDirectory();
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<Integer, ItemData> items = new HashMap<Integer, ItemData>();
		
		for (Map<String, String> itemMap : (List<Map>)objectMapper.readValue(itemListJson, List.class)) {
			ItemData item = new ItemData(itemMap, itemImgDir);
			items.put(item.getCode(), item);
		}
		setItems(items);
	}
	
	public Set<Integer> getCodes() {
		return items.keySet();
	}
	
	public ItemData getItem(Integer code) {
		return items.get(code);
	}

	public Collection<ItemData> getItems() {
		return items.values();
	}

	private void setItems(Map<Integer, ItemData> items) {
		this.items = Collections.unmodifiableMap(items);
	}


}
