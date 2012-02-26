package lol_itemsets.lol_itemsets;

import java.io.File;

public class AppConfig {

	private final String dataDir;
	private final String itemListFile = "lol-itemlist.json";
	private final String itemImgDir = "items";
	private final String champListFile = "champ-list.json";
	private final String champImgDir = "champs";
	
	public AppConfig() { 
		String prop = System.getProperty("itemset.data");
		dataDir = (prop != null) ? prop : "./data"; 
	}
	
	public File getItemList() {
		return new File(dataDir, itemListFile);
	}

	public File getItemImageDirectory() {
		return new File(dataDir, itemImgDir);
	}

	public File getChampList() {
		return new File(dataDir, champListFile);
	}

	public File getChampImageDirectory() {
		return new File(dataDir, champImgDir);
	}

}
