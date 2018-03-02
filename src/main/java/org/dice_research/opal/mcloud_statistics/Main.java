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

	final static public String CODE_GENERATION = "code";
	final static public String JSON_INFO = "json";
	final static public String MCLOUD = "mcloud";

	public static void main(String[] args) throws IOException {
		if (args.length == 2) {

			if (args[1].equals(MCLOUD)) {

				List<File> files = Json.getJsonFilesInDirectory(args[0]);
				for (File file : files) {
					McloudDataset.add(new McloudDataset(file));
				}
				Statistics statistics = new Statistics(args[0]);
				statistics.calculate().writeHtmlFile();

			} else if (args[1].equals(JSON_INFO)) {

				List<File> files = Json.getJsonFilesInDirectory(args[0]);
				for (File file : files) {
					JsonInfo.add(new JsonInfo(file));
				}
				JsonInfo.printInfo();

			} else if (args[1].equals(CODE_GENERATION)) {

				List<File> files = Json.getJsonFilesInDirectory(args[0]);
				for (File file : files) {
					JsonInfo.add(new JsonInfo(file));
				}
				CodeGenerator.generate();
			}

			else {
				printInfo();
				System.exit(1);
			}

		} else {
			printInfo();
			System.exit(0);
		}
	}

	protected static void printInfo() {
		System.out.println("Please specify two arguments:");
		System.out.println("1) The directory with mCloud JSON files");
		System.out.println("2) '" + MCLOUD + "' for mCloud statistics or");
		System.out.println("   '" + JSON_INFO + "' for information about the used JSON format or");
		System.out.println("   '" + CODE_GENERATION + "' for code generation");
	}
}