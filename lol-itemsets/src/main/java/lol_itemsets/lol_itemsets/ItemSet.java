package lol_itemsets.lol_itemsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSet {

	private ItemData[] list;
	
	private final static String iniTemplate = 
			"[ItemSet1]\n"+
			"SetName=Set1\n"+
			"RecItem1=%0$d\n"+
			"RecItem2=%1$d\n"+
			"RecItem3=%2$d\n"+
			"RecItem4=%3$d\n"+
			"RecItem5=%4$d\n"+
			"RecItem6=%5$d\n"+
					"\n"+
			"[ItemSet2]\n"+
			"SetName=Set2\n"+
			"RecItem1=%0$d\n"+
			"RecItem2=%1$d\n"+
			"RecItem3=%2$d\n"+
			"RecItem4=%3$d\n"+
			"RecItem5=%4$d\n"+
			"RecItem6=%5$d";

	public ItemSet() {
		this.list = new ItemData[6];
		
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
