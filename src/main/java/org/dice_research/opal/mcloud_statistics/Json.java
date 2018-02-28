package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

/**
 * Generic JSON file and object handler.
 * 
 * @see http://stleary.github.io/JSON-java/
 * @see https://github.com/stleary/JSON-java
 * 
 * @author Adrian Wilke
 */
public abstract class Json implements Comparable<Json> {

	final static public String JSON_EXTENSION = ".json";

	public static List<File> getJsonFilesInDirectory(String directoryPath) throws IOException {
		List<File> files = new LinkedList<File>();
		File directory = new File(directoryPath);
		if (!directory.canRead()) {
			System.err.println("Error: Can not read directory: " + directoryPath);
		} else {
			File[] fileArray = directory.listFiles(new FilenameFilter() {
				public boolean accept(File directory, String fileName) {
					if (fileName.toLowerCase().endsWith(JSON_EXTENSION)) {
						return true;
					} else {
						return false;
					}
				}
			});
			files.addAll(Arrays.asList(fileArray));
		}
		return files;
	}

	protected File file;
	protected JSONObject jsonObject;

	public Json(File file) {
		this.file = file;
	}

	public int compareTo(Json json) {
		return getFile().getName().compareTo(json.getFile().getName());
	}

	public File getFile() {
		return file;
	}

	public JSONObject getJsonObject() {
		if (jsonObject == null) {
			parse();
		}
		return jsonObject;
	}

	/**
	 * @see https://stackoverflow.com/a/36290229
	 */
	public void parse() {
		String text;
		try {
			text = new String(Files.readAllBytes(Paths.get(this.getFile().getPath())), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Cold not parse " + this.getFile().getPath());
			return;
		}
		this.jsonObject = new JSONObject(text);
	}
}