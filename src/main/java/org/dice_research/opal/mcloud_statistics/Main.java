package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Main entry point.
 * 
 * @author Adrian Wilke
 */
public abstract class Main {

	final static public String TASK_CODE_GENERATION = "code";
	final static public String TASK_EXPORT = "export";
	final static public String TASK_JSON_INFO = "json";
	final static public String TASK_MCLOUD = "mcloud";

	final static public String JSON_DIRECTORY = null;
	final static public String TASK = null;

	public static void main(String[] args) throws IOException {

		// Configuration
		String directoryJson = null;
		String task = null;
		if (args.length == 2) {
			directoryJson = args[0];
			task = args[1];
		}
		if (JSON_DIRECTORY != null && TASK != null) {
			directoryJson = JSON_DIRECTORY;
			task = TASK;
		}

		if (directoryJson != null && task != null) {

			// Run
			if (task.equals(TASK_MCLOUD)) {
				List<File> files = Json.getJsonFilesInDirectory(directoryJson);
				for (File file : files) {
					McloudDataset.add(new McloudDataset(file));
				}
				Statistics statistics = new Statistics(directoryJson);
				statistics.calculate().writeHtmlFile();

			} else if (task.equals(TASK_EXPORT)) {
				List<File> files = Json.getJsonFilesInDirectory(directoryJson);
				for (File file : files) {
					McloudDataset.add(new McloudDataset(file));
				}
				Export export = new Export(directoryJson);
				export.export();

			} else if (task.equals(TASK_JSON_INFO)) {
				List<File> files = Json.getJsonFilesInDirectory(directoryJson);
				for (File file : files) {
					JsonInfo.add(new JsonInfo(file));
				}
				JsonInfo.printInfo();

			} else if (task.equals(TASK_CODE_GENERATION)) {
				List<File> files = Json.getJsonFilesInDirectory(directoryJson);
				for (File file : files) {
					JsonInfo.add(new JsonInfo(file));
				}
				CodeGenerator.generate();
			}

			else {
				// Unknown task
				printInfo();
				System.exit(1);
			}

		} else {
			// Incorrect number of arguments
			printInfo();
			System.exit(0);
		}
	}

	protected static void printInfo() {
		System.out.println("Please specify two arguments:");
		System.out.println("1) The directory with mCloud JSON files");
		System.out.println("2) '" + TASK_MCLOUD + "' for mCloud statistics or");
		System.out.println("   '" + TASK_JSON_INFO + "' for information about the used JSON format or");
		System.out.println("   '" + TASK_CODE_GENERATION + "' for code generation");
	}
}