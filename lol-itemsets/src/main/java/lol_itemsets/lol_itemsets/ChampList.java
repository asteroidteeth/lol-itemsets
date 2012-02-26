package lol_itemsets.lol_itemsets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

public class ChampList {
	private Map<String, ChampData> champs;

	public ChampList(AppConfig config) throws IOException {
		File champListJson = config.getChampList();
		File champImgDir = config.getChampImageDirectory();
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, ChampData> champs = new HashMap<String, ChampData>();
		
		for (Map<String, String> champMap : (List<Map>)objectMapper.readValue(champListJson, List.class)) {
			ChampData champ = new ChampData(champMap, champImgDir);
			champs.put(champ.getName(), champ);
		}
		setChamps(champs);
	}
	
	public Collection<ChampData> getChamps() {
		return champs.values();
	}
	
	public ChampData getChamp(String name) {
		return champs.get(name);
	}
	
	public Set<String> getChampNames() {
		return champs.keySet();
	}

	private void setChamps(Map<String, ChampData> champs) {
		this.champs = Collections.unmodifiableMap(champs);
	}
	


}
