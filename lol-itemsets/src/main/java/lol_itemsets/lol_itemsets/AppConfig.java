package lol_itemsets.lol_itemsets;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AppConfig {

	private final String dataDir;
	private final String itemListFile = "lol-itemlist.json";
	private final String itemImgDir = "items";
	private final String champListFile = "champ-list.json";
	private final String champImgDir = "champs";
	private String loldir;
	private final String releasesDir = "RADS\\solutions\\lol_game_client_sln\\releases";
	private final String configDirTemplate = "RADS\\solutions\\lol_game_client_sln\\releases\\%s\\deploy\\DATA\\Characters";
	private File configDir;
	private final String classicIniFileName = "RecItemsCLASSIC.ini";
	private final String domIniFileName = "RecItemsODIN.ini";

	public AppConfig() {
		String prop = System.getProperty("itemset.data");
		dataDir = (prop != null) ? prop : "./data";
		setLoldir("C:\\Riot Games\\League of Legends");
	}

	public File getDataDir() {
		return new File(dataDir);
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

	public String getLoldir() {
		return loldir;
	}

	public File getClassicChampIni(String champName) {
		File dir = new File(configDir, champName);
		if (!dir.exists()) {
			dir.mkdir();
		}
		return new File(dir, classicIniFileName);

	}

	public File getDominionChampIni(String champName) {
		File dir = new File(configDir, champName);
		if (!dir.exists()) {
			dir.mkdir();
		}
		return new File(dir, domIniFileName);

	}

	public void setLoldir(String loldir) {
		this.loldir = loldir;
		String version = getLatestVersion(new File(loldir, releasesDir));
		configDir = new File(loldir,String.format(configDirTemplate, version));
	}

	private String getLatestVersion(File dir) {
		List<File> files = Arrays.asList(dir.listFiles());
		File latest = files.get(0);
		int latestVal = Integer.parseInt(latest.getName().split("\\.")[3]);
		for (File f : files.subList(1, files.size())) {
			int testVal = Integer.parseInt(f.getName().split("\\.")[3]);
			if (testVal > latestVal) {
				latest = f;
				latestVal = testVal;
			}
		}

		return latest.getName();
	}

	public static void main(String[] args) {
		AppConfig c = new AppConfig();
		System.out.println(c.getClassicChampIni("Annie"));
	}

}
