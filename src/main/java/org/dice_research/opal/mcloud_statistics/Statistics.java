package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * Basic statistics.
 * 
 * @author Adrian Wilke
 */
public class Statistics {

	public final static boolean WRITE_FILE = true;
	final public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	final public static String CRAWLING_DATE = "2018-03-28";

	protected SortedSet<McloudDataset> datasets;
	protected String directory;
	protected HtmlGenerator generator = new HtmlGenerator();

	protected SortedMap<String, Integer> licenseNames = new TreeMap<String, Integer>();
	protected SortedMap<String, Integer> licenseLocations = new TreeMap<String, Integer>();
	protected Map<String, String> licenseNamesToLocations = new HashMap<String, String>();
	protected int licenseNameGiven = 0;
	protected int licenseNameNotGiven = 0;
	protected int licenseLocationGiven = 0;
	protected int licenseLocationNotGiven = 0;

	/**
	 * Directory containing mCloud JSON files
	 */
	public Statistics(String directory) {
		this.directory = directory;
		datasets = McloudDataset.getDatasets();
	}

	public Statistics calculate() {
		generator.generateHeader();

		List<String> list = new LinkedList<String>();
		list.add("Date of dataset crawling: " + CRAWLING_DATE);
		list.add("Date of statistics generation: " + HtmlGenerator.DATE);
		list.add("Number of datasets: " + datasets.size());
		list.add("Author: " + HtmlGenerator.AUTHOR);
		list.add("Code: " + HtmlGenerator.getHtmlLink(HtmlGenerator.GENERATOR_WEBSITE));
		generator.addList(list);

		doDescriptions();
		getLicenses();
		doLicenses();
		doJsonInfo();

		for (McloudDataset dataset : datasets) {
			generator.addHeading(dataset.getTitle());
			list = new LinkedList<String>();
			if (dataset.getProviderOrganisationurl().isEmpty()) {
				list.add("Provider: " + dataset.getProviderOrganisationname());
			} else {
				list.add("Provider: " + HtmlGenerator.getHtmlLink(dataset.getProviderOrganisationurl(),
						dataset.getProviderOrganisationname()));
			}
			list.add("Categories: " + dataset.getCategories().toList());
			if (!dataset.getDescription().isEmpty()) {
				list.add("Description: " + dataset.getDescription());
			}
			list.add("License: " + dataset.getLicenseLicensename());
			String sources = "";
			if (null != dataset.getSourcesAtom_feed()) {
				sources += HtmlGenerator.getHtmlLink(dataset.getSourcesAtom_feed(), "feed") + " ";
			}
			if (null != dataset.getSourcesPortal()) {
				sources += HtmlGenerator.getHtmlLink(dataset.getSourcesPortal(), "portal") + " ";
			}
			if (null != dataset.getSourcesWms()) {
				sources += HtmlGenerator.getHtmlLink(dataset.getSourcesWms(), "WMS") + " ";
			}
			if (!sources.isEmpty()) {
				list.add("Sources: " + sources);
			}
			if (null != dataset.getRetrievalTimestamp()) {
				list.add("Retrieval: " + DATE_FORMAT.format(new Date(dataset.getRetrievalTimestamp())));
			}
			list.add("File: " + dataset.getFile().getName() + " (" + dataset.getFile().length() + " bytes)");
			generator.addList(list);
		}
		return this;
	}

	protected void doJsonInfo() {
		for (McloudDataset mcloudDataset : datasets) {
			JsonInfo.add(new JsonInfo(mcloudDataset.getFile()));
		}
		List<String> list = new LinkedList<String>();
		generator.addHeading("JSON structure");
		for (String infoString : JsonInfo.getInfoStrings()) {
			list.add(infoString);
		}
		generator.addList(list);
	}

	protected void doDescriptions() {
		int min = Integer.MAX_VALUE;
		int max = 0;
		List<Integer> wordsList = new LinkedList<Integer>();
		Integer[] wordsArray = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int wordCounter = 0;
		for (McloudDataset dataset : datasets) {
			int words = dataset.getDescription().split(" ").length;
			wordCounter += words;
			if (dataset.getDescription().trim().isEmpty()) {
				min = 0;
			}
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
		list.add("Minimum number of words: " + min);
		list.add("Maximum number of words: " + max);
		list.add("Average number of words: " + (wordCounter / datasets.size()));
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

	protected void doLicenses() {
		generator.addHeading("Licenses");
		List<String> list = new LinkedList<String>();
		list.add("License name given: " + licenseNameGiven);
		list.add("License name not given: " + licenseNameNotGiven);
		list.add("Number of license names: " + licenseNames.size());
		list.add("License location given: " + licenseLocationGiven);
		list.add("License location not given: " + licenseLocationNotGiven);

		SortedMap<String, Integer> namesSortByUses = Utils.sortMapByValue(licenseNames);
		for (Entry<String, Integer> entry : namesSortByUses.entrySet()) {
			String name = entry.getKey();
			if (licenseNamesToLocations.containsKey(name) && !licenseNamesToLocations.get(name).equals("null")) {
				name = HtmlGenerator.getHtmlLink(licenseNamesToLocations.get(name), name);
			}
			int percent = (100 * entry.getValue() / datasets.size());
			list.add(entry.getValue() + " uses (" + percent + "%) of " + name);
		}

		generator.addList(list);
	}

	protected void getLicenses() {

		for (McloudDataset dataset : datasets) {
			if (dataset.getLicenseLicensename() != null && !dataset.getLicenseLicensename().isEmpty()) {
				if (licenseNames.containsKey(dataset.getLicenseLicensename())) {
					licenseNames.put(dataset.getLicenseLicensename(),
							licenseNames.get(dataset.getLicenseLicensename()) + 1);
				} else {
					licenseNames.put(dataset.getLicenseLicensename(), 1);
				}
				licenseNameGiven++;
			} else {
				licenseNameNotGiven++;
			}

			if (dataset.getLicenseLicenselocation() != null && !dataset.getLicenseLicenselocation().isEmpty()) {
				if (licenseLocations.containsKey(dataset.getLicenseLicenselocation())) {
					licenseLocations.put(dataset.getLicenseLicenselocation(),
							licenseLocations.get(dataset.getLicenseLicenselocation()) + 1);
				} else {
					licenseLocations.put(dataset.getLicenseLicenselocation(), 1);
				}
				licenseLocationGiven++;
			} else {
				licenseLocationNotGiven++;
			}

			if (dataset.getLicenseLicensename() != null && !dataset.getLicenseLicensename().isEmpty()
					&& dataset.getLicenseLicenselocation() != null && !dataset.getLicenseLicenselocation().isEmpty()) {
				if (licenseNamesToLocations.containsKey(dataset.getLicenseLicensename())) {
					if (!dataset.getLicenseLicenselocation()
							.equals(licenseNamesToLocations.get(dataset.getLicenseLicensename()))) {
						System.err.println("Warning: Different locations for " + dataset.getLicenseLicensename());
					}
				} else {
					licenseNamesToLocations.put(dataset.getLicenseLicensename(), dataset.getLicenseLicenselocation());
				}
			}
		}
	}

	public void writeHtmlFile() throws FileNotFoundException {
		if (WRITE_FILE) {
			File file = generator.generateFooter().writeToFile(new File(directory));
			System.out.println("Generated file: " + file.getPath());
		}
	}
}