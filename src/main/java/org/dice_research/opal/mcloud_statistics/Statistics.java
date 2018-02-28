package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

/**
 * Basic statistics.
 * 
 * @author Adrian Wilke
 */
public class Statistics {

	SortedSet<McloudDataset> datasets;
	String directory;
	HtmlGenerator generator = new HtmlGenerator();

	public Statistics(String directory) {
		this.directory = directory;
		datasets = McloudDataset.getDatasets();
	}

	public Statistics calculate() {
		generator.generateHeader();

		List<String> list = new LinkedList<String>();
		list.add("Date of generation: " + HtmlGenerator.DATE);
		list.add("Number of datasets: " + datasets.size());
		generator.addList(list);

		doDescription();

		for (McloudDataset dataset : datasets) {
			generator.addHeading(dataset.getTitle());
			list = new LinkedList<String>();
			list.add("File: " + dataset.getFile().getName() + " (" + dataset.getFile().length() + " bytes)");
			list.add("Description: " + dataset.getDescription());
			generator.addList(list);
		}
		return this;
	}

	protected void doDescription() {
		int min = Integer.MAX_VALUE;
		int max = 0;
		List<Integer> wordsList = new LinkedList<Integer>();
		Integer[] wordsArray = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (McloudDataset dataset : datasets) {
			int words = dataset.getDescription().split(" ").length;
			if (words < min) {
				min = words;
			}
			if (words > max) {
				max = words;
			}
			if (dataset.getDescription().trim().isEmpty()) {
				wordsArray[0]++;
			} else if (words <= 1) {
				wordsArray[1]++;
			} else if (words <= 2) {
				wordsArray[2]++;
			} else if (words <= 3) {
				wordsArray[3]++;
			} else if (words <= 4) {
				wordsArray[4]++;
			} else if (words <= 5) {
				wordsArray[5]++;
			} else if (words <= 10) {
				wordsArray[6]++;
			} else if (words <= 20) {
				wordsArray[7]++;
			} else if (words <= 50) {
				wordsArray[8]++;
			} else if (words <= 100) {
				wordsArray[9]++;
			} else {
				wordsArray[10]++;
			}
			wordsList.add(words);
		}
		generator.addHeading("Descriptions");
		List<String> list = new LinkedList<String>();
		list.add("Minimum words: " + min);
		list.add("Maximum words: " + max);
		list.add("No description: " + wordsArray[0] + " (" + (100 * wordsArray[0] / datasets.size() + "%)"));
		list.add("Up to 1 word: " + wordsArray[1] + " (" + (100 * wordsArray[1] / datasets.size() + "%)"));
		list.add("Up to 2 words: " + wordsArray[2] + " (" + (100 * wordsArray[2] / datasets.size() + "%)"));
		list.add("Up to 3 words: " + wordsArray[3] + " (" + (100 * wordsArray[3] / datasets.size() + "%)"));
		list.add("Up to 4 words: " + wordsArray[4] + " (" + (100 * wordsArray[4] / datasets.size() + "%)"));
		list.add("Up to 5 words: " + wordsArray[5] + " (" + (100 * wordsArray[5] / datasets.size() + "%)"));
		list.add("Up to 10 words: " + wordsArray[6] + " (" + (100 * wordsArray[6] / datasets.size() + "%)"));
		list.add("Up to 20 words: " + wordsArray[7] + " (" + (100 * wordsArray[7] / datasets.size() + "%)"));
		list.add("Up to 50 words: " + wordsArray[8] + " (" + (100 * wordsArray[8] / datasets.size() + "%)"));
		list.add("Up to 100 words: " + wordsArray[9] + " (" + (100 * wordsArray[9] / datasets.size() + "%)"));
		list.add("More than 100 words: " + wordsArray[10] + " (" + (100 * wordsArray[10] / datasets.size() + "%)"));
		generator.addList(list);
	}

	public void writeHtmlFile() throws FileNotFoundException {
		File file = generator.generateFooter().writeToFile(new File(directory));
		System.out.println("Generated file: " + file.getPath());
	}
}