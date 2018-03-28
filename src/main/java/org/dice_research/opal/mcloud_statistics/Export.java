package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Export extends Statistics {

	/**
	 * Directory containing mCloud JSON files
	 */
	public Export(String directory) {
		super(directory);
	}

	public void export() throws FileNotFoundException {

		System.out.println("Number of mCloud datasets: " + datasets.size());

		// Descriptions
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, Integer> lengthCounter : getWordsInDescriptions().entrySet()) {
			sb.append(lengthCounter.getKey());
			sb.append(",");
			sb.append(lengthCounter.getValue());
			sb.append(System.lineSeparator());
		}
		writeToFile(sb, new File(directory), "description-length");

		// Licenses
		writeToFile(getLicensesString(), new File(directory), "licenses");

	}

	protected StringBuilder getLicensesString() {
		StringBuilder sb = new StringBuilder();
		for (McloudDataset dataset : datasets) {
			if (dataset.getLicenseLicensename() != null && !dataset.getLicenseLicensename().isEmpty()) {
				sb.append("\"");
				sb.append(dataset.getLicenseLicensename().replace("\"", "'"));
				sb.append("\"");
			}
			sb.append(",");

			if (dataset.getLicenseLicenselocation() != null && !dataset.getLicenseLicenselocation().isEmpty()) {
				sb.append("\"");
				sb.append(dataset.getLicenseLicenselocation().replace("\"", "'"));
				sb.append("\"");
			}
			sb.append(System.lineSeparator());
		}
		return sb;
	}

	protected Map<Integer, Integer> getWordsInDescriptions() {
		Map<Integer, Integer> lengthCounter = new HashMap<Integer, Integer>();
		for (McloudDataset dataset : datasets) {
			int words;
			if (dataset.getDescription().trim().isEmpty()) {
				words = 0;
			} else {
				words = dataset.getDescription().split(" ").length;
			}
			if (lengthCounter.containsKey(words)) {
				lengthCounter.put(words, lengthCounter.get(words) + 1);
			} else {
				lengthCounter.put(words, 1);
			}
		}
		return lengthCounter;
	}

	protected File writeToFile(StringBuilder content, File directory, String id) throws FileNotFoundException {
		directory = new File(directory, "out");
		directory.mkdirs();
		File file = new File(directory, "mCloud-statistics-" + id + "-" + HtmlGenerator.DATE + ".txt");
		PrintWriter out = new PrintWriter(file);
		out.println(content.toString());
		out.close();
		System.out.println("Generated file: " + file.getPath());
		return file;
	}
}
