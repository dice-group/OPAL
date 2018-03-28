package org.dice_research.opal.mcloud_statistics;

import java.util.List;
import java.util.Map.Entry;

/**
 * Generates code for the lazy author.
 * 
 * @author Adrian Wilke
 */
public abstract class CodeGenerator {

	public static void generate() {
		StringBuilder sb = new StringBuilder();

		for (Entry<String, List<String>> classInfo : JsonInfo.getClassesInfo().entrySet()) {

			if (classInfo.getKey().equals("java.lang.String")) {
				for (String key : classInfo.getValue()) {
					sb.append("public String get" + key.substring(0, 1).toUpperCase() + key.substring(1) + "() {");
					sb.append("return getJsonObject().getString(\"" + key + "\");");
					sb.append("}");
					sb.append(System.lineSeparator());
				}
			}

			else if (classInfo.getKey().equals("java.lang.Long")) {
				for (String key : classInfo.getValue()) {
					sb.append("public Long get" + key.substring(0, 1).toUpperCase() + key.substring(1) + "() {");
					sb.append("if(getJsonObject().has(\"" + key + "\")) {");
					sb.append("return getJsonObject().getLong(\"" + key + "\");");
					sb.append("} else { return null; }");
					sb.append(System.lineSeparator());
				}
			}

			else if (classInfo.getKey().equals("org.json.JSONArray")) {
				for (String key : classInfo.getValue()) {
					sb.append("public JSONArray get" + key.substring(0, 1).toUpperCase() + key.substring(1) + "() {");
					sb.append("return getJsonObject().getJSONArray(\"" + key + "\");");
					sb.append("}");
					sb.append(System.lineSeparator());
				}
			}

			else if (classInfo.getKey().equals("org.json.JSONObject")) {
				for (String key : classInfo.getValue()) {
					String secondKey = key.split(" ")[1];
					key = key.split(" ")[0];
					sb.append("public String get" + key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase()
							+ secondKey.substring(0, 1).toUpperCase() + secondKey.substring(1).toLowerCase() + "() {");
					sb.append("if (getJsonObject().has(\"" + key + "\") && getJsonObject().getJSONObject(\"" + key
							+ "\").has(\"" + secondKey + "\")) {");
					sb.append("return getJsonObject().getJSONObject(\"" + key + "\").get(\"" + secondKey
							+ "\").toString();");
					sb.append("} else { return null; }");
					sb.append("}");
					sb.append(System.lineSeparator());
				}
			}

			else {
				System.err
						.println("Warning: Additional JSON keys found in " + CodeGenerator.class.getName().toString());
			}
		}
		System.out.println(sb.toString());
	}
}