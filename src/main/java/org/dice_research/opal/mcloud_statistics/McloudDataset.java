package org.dice_research.opal.mcloud_statistics;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONArray;

/**
 * mCloud JSON handler.
 * 
 * @see https://mcloud.de/
 * 
 * @author Adrian Wilke
 */
public class McloudDataset extends Json {

	protected static SortedSet<McloudDataset> datasets = new TreeSet<McloudDataset>();

	public static void add(McloudDataset dataset) {
		datasets.add(dataset);
	}

	public static SortedSet<McloudDataset> getDatasets() {
		return datasets;
	}

	public McloudDataset(File file) {
		super(file);

	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(file.getPath());
		stringBuilder.append(file.length());
		stringBuilder.append(getDescription());
		return stringBuilder.toString();
	}

	public Long getRetrievalTimestamp() {return getJsonObject().getLong("retrievalTimestamp");}
	public String getDescription() {return getJsonObject().getString("description");}
	public String getTitle() {return getJsonObject().getString("title");}
	public JSONArray getCategories() {return getJsonObject().getJSONArray("categories");}
	public String getLicenseLicenselocation() {return getJsonObject().getJSONObject("license").getString("licenseLocation");}
	public String getLicenseLicensename() {return getJsonObject().getJSONObject("license").getString("licenseName");}
	public String getSourcesAtom_feed() {return getJsonObject().getJSONObject("sources").getString("ATOM_FEED");}
	public String getSourcesWms() {return getJsonObject().getJSONObject("sources").getString("WMS");}
	public String getSourcesPortal() {return getJsonObject().getJSONObject("sources").getString("PORTAL");}
	public String getProviderOrganisationname() {return getJsonObject().getJSONObject("provider").getString("organisationName");}
	public String getProviderOrganisationurl() {return getJsonObject().getJSONObject("provider").getString("organisationUrl");}
}