package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Generates HTML and writes HTML file.
 * 
 * @author Adrian Wilke
 */
public class HtmlGenerator {

	final public static String TITLE = "mCloud statistics";
	final public static String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	protected StringBuilder stringBuilder;

	public HtmlGenerator() {
		stringBuilder = new StringBuilder();
	}

	public HtmlGenerator generateFooter() {
		stringBuilder.append("</div>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("</body>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("</html>");
		return this;
	}

	public HtmlGenerator generateHeader() {
		stringBuilder.append("<!doctype html>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<html>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<head>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<meta charset=\"utf-8\"/>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<title>" + TITLE + "</title>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<meta name=\"author\" content=\"Adrian Wilke\">");
		stringBuilder.append(System.lineSeparator());
		stringBuilder
				.append("<link href=\"https://fonts.googleapis.com/css?family=Roboto:400,700\" rel=\"stylesheet\">");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<style>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("html {font-family: 'Roboto',sans-serif}");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("h2 {border-bottom:1px solid #000}");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("div {width:60%; margin:auto}");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("</style>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("</head>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<body>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<div>");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("<h1>" + TITLE + "</h1>");
		stringBuilder.append(System.lineSeparator());
		return this;
	}

	public HtmlGenerator addHeading(String heading) {
		stringBuilder.append("<h2>");
		stringBuilder.append(heading);
		stringBuilder.append("</h2>");
		stringBuilder.append(System.lineSeparator());
		return this;
	}

	public HtmlGenerator addList(List<String> list) {
		stringBuilder.append("<ul>");
		stringBuilder.append(System.lineSeparator());
		for (String text : list) {
			stringBuilder.append("<li>");
			stringBuilder.append(text);
			stringBuilder.append("</li>");
			stringBuilder.append(System.lineSeparator());
		}
		stringBuilder.append("</ul>");
		stringBuilder.append(System.lineSeparator());
		return this;
	}

	protected File writeToFile(File directory) throws FileNotFoundException {
		directory.mkdirs();
		File file = new File(directory, "mCloud-statistics-" + DATE + ".htm");
		PrintWriter out = new PrintWriter(file);
		out.println(stringBuilder.toString());
		out.close();
		return file;
	}
}