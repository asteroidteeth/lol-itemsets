package lol_itemsets.lol_itemsets;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class DeserializationTest {

	@Test
	/**
	 * The system property itemset.data should be set to the data directory location.
	 */
	public void configShoudNotBeNull() {
		AppConfig config = new AppConfig();
		Assert.assertNotNull(config.getChampImageDirectory());
		Assert.assertNotNull(config.getChampList());
		Assert.assertNotNull(config.getItemImageDirectory());
		Assert.assertNotNull(config.getItemList());
	}

	@Test
	public void itemListDeserializationTest() throws IOException {
		ItemList l = new ItemList(new AppConfig());
		Assert.assertTrue(l.getItems().size() > 1);
	}

	@Test
	public void itemsShouldContainImageData() throws IOException {
		ItemList l = new ItemList(new AppConfig());
		for (ItemData data : l.getItems()) {
			Assert.assertTrue(data.getImage().getWidth() > 0);
			Assert.assertTrue(data.getImage().getHeight() > 0);
		}
	}

	@Test
	public void championListDeserializationTest() throws IOException {
		ChampList l = new ChampList(new AppConfig());
		Assert.assertTrue(l.getChamps().size() > 1);
	}

	@Test
	public void champsShouldContainData() throws IOException {
		ChampList l = new ChampList(new AppConfig());
		for (ChampData data : l.getChamps()) {
			Assert.assertTrue(data.getName() != null && data.getName() != "");
			Assert.assertTrue(data.getFace().getWidth() > 0 && data.getFace().getHeight() > 0);
		}
	}
}
