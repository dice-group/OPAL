package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Gets info of JSON structure.
 * 
 * @author Adrian Wilke
 */
public class JsonInfo extends Json {

	protected static SortedSet<JsonInfo> datasets = new TreeSet<JsonInfo>();

	public static void add(JsonInfo dataset) {
		datasets.add(dataset);
	}

	public static Map<String, List<String>> getClassesInfo() {
		SortedMap<String, List<String>> classesToKeys = new TreeMap<String, List<String>>();
		for (JsonInfo mcloudDataset : datasets) {
			classesToKeys.putAll(mcloudDataset.getClassInfo());
		}
		return classesToKeys;
	}

	public static void printClassesInfo() {
		for (Entry<String, List<String>> mcloudDataset : getClassesInfo().entrySet()) {
			System.out.println("Key(s) with values of type " + mcloudDataset.getKey());
			for (String key : mcloudDataset.getValue()) {
				System.out.println(" " + key);
			}
			System.out.println();
		}
	}

	public JsonInfo(File file) {
		super(file);
	}

	/**
	 * @see {@link JSONObject#toMap()}
	 */
	public Map<String, List<String>> getClassInfo() {
		Map<String, List<String>> classesToKeys = new HashMap<String, List<String>>();

		// Check JSON object
		for (Entry<String, Object> jsonKeyToValue : getJsonObject().toMap().entrySet()) {
			if (jsonKeyToValue.getValue() != null) {

				// Get class name of value
				String classNameOfValue = jsonKeyToValue.getValue().getClass().getName();

				// JSONObject
				if (classNameOfValue.equals("java.util.HashMap")) {
					JSONObject jsonObject = getJsonObject().getJSONObject(jsonKeyToValue.getKey());
					for (String key : jsonObject.keySet()) {
						classNameOfValue = "org.json.JSONObject";
						if (!classesToKeys.containsKey(classNameOfValue)) {
							classesToKeys.put(classNameOfValue, new LinkedList<String>());
						}
						classesToKeys.get(classNameOfValue).add(jsonKeyToValue.getKey() + " " + key);
					}

				} else {

					// JSONArray
					if (classNameOfValue.equals("java.util.ArrayList")) {
						classNameOfValue = "org.json.JSONArray";
					}

					// Put class-name and related JSON object key
					if (!classesToKeys.containsKey(classNameOfValue)) {
						classesToKeys.put(classNameOfValue, new LinkedList<String>());
					}
					classesToKeys.get(classNameOfValue).add(jsonKeyToValue.getKey());
				}
			}
		}
		return classesToKeys;
	}
}