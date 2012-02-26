package lol_itemsets.lol_itemsets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

public class ItemsetSerializationTest {

	static ItemList itemList;

	@BeforeClass
	public static void setup() throws IOException {
		AppConfig config = new AppConfig();
		itemList = new ItemList(config);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void ItemSetCreation() {
		ItemSet itemSet = new ItemSet();
		Assert.assertTrue(itemSet.get(0) == null);
		Assert.assertTrue(itemSet.get(5) == null);
		itemSet.get(6);
	}

	@Test
	public void itemSetCreation2() {
		ArrayList<ItemData> items = new ArrayList<ItemData>(itemList.getItems());
		ItemSet itemSet = buildItemSet(items);
		for (int i = 0; i < 6; i++) {
			Assert.assertEquals(items.get(i).getImage(), itemSet.get(i).getImage());
		}
	}


	
	@Test
	public void itemSerializationTest() {

		List<ItemData> items = (new ArrayList<ItemData>(itemList.getItems())).subList(0, 6);
		ItemSet itemSet = buildItemSet(items);
		String expected = buildItemIni(items);
		
		Assert.assertEquals(expected, itemSet.buildIni());
	}
	
	private ItemSet buildItemSet(List<ItemData> items) {
		ItemSet itemSet = new ItemSet();
		
		for (int i = 0; i < 6; i++) {
			itemSet.set(i, items.get(i));
		}
		
		return itemSet;
	}
	
	public String buildItemIni(List<ItemData> items) {
		String expectedTemplate =
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
		
		String expected = String.format(expectedTemplate, items.get(0).getCode(),items.get(1).getCode(),items.get(2).getCode(),items.get(3).getCode(),items.get(4).getCode(),items.get(5).getCode());
		return expected;
	}
}
