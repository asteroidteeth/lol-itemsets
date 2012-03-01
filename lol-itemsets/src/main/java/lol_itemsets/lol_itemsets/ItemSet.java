package lol_itemsets.lol_itemsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSet {

	private ItemData[] list;
	
	private final static String iniTemplate = 
			"[ItemSet1]\r\n"+
			"SetName=Set1\r\n"+
			"RecItem1=%1$d\r\n"+
			"RecItem2=%2$d\r\n"+
			"RecItem3=%3$d\r\n"+
			"RecItem4=%4$d\r\n"+
			"RecItem5=%5$d\r\n"+
			"RecItem6=%6$d\r\n"+
					"\r\n"+
			"[ItemSet2]\r\n"+
			"SetName=Set2\r\n"+
			"RecItem1=%1$d\r\n"+
			"RecItem2=%2$d\r\n"+
			"RecItem3=%3$d\r\n"+
			"RecItem4=%4$d\r\n"+
			"RecItem5=%5$d\r\n"+
			"RecItem6=%6$d";

	public ItemSet() {
		this.list = new ItemData[6];
		
	}
	
	public ItemSet(ItemData[] itemData) {
		if (itemData.length != 6) {
			throw new IllegalArgumentException("An item set contains 6 items, whereas this itemdata array contains " + itemData.length);
		}
		this.list = itemData;
	}

	public ItemData get(int index) {
		return list[index];
	}
	
	public void set(int index, ItemData itemData) {
		if (index >= list.length) {
			throw new IndexOutOfBoundsException("An ItemSet only contains six slots");
		}
		list[index] = itemData;
	}

	public String buildIni() {
		Integer[] codes = new Integer[list.length];
		for (int i = 0; i < list.length; ++i) {
			codes[i] = list[i].getCode();
		}
		return String.format(iniTemplate, codes);
	}
}
