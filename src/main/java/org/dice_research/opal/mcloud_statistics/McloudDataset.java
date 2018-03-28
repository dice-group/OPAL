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
		return file.getPath().toString();
	}

	/**
	 * 	Note: Removed in mCloud between Jan and March 28th
	 */
	public Long getRetrievalTimestamp() {
		if (getJsonObject().has("retrievalTimestamp")) {
			return getJsonObject().getLong("retrievalTimestamp");
		} else {
			return null;
		}
	}

	// Generated code
	
	public String getDescription() {return getJsonObject().getString("description");}
	public String getTitle() {return getJsonObject().getString("title");}
	public JSONArray getCategories() {return getJsonObject().getJSONArray("categories");}
	public String getLicenseLicenselocation() {if (getJsonObject().has("license") && getJsonObject().getJSONObject("license").has("licenseLocation")) {return getJsonObject().getJSONObject("license").get("licenseLocation").toString();} else { return null; }}
	public String getLicenseLicensename() {if (getJsonObject().has("license") && getJsonObject().getJSONObject("license").has("licenseName")) {return getJsonObject().getJSONObject("license").get("licenseName").toString();} else { return null; }}
	public String getSourcesAtom_feed() {if (getJsonObject().has("sources") && getJsonObject().getJSONObject("sources").has("ATOM_FEED")) {return getJsonObject().getJSONObject("sources").get("ATOM_FEED").toString();} else { return null; }}
	public String getSourcesWms() {if (getJsonObject().has("sources") && getJsonObject().getJSONObject("sources").has("WMS")) {return getJsonObject().getJSONObject("sources").get("WMS").toString();} else { return null; }}
	public String getSourcesPortal() {if (getJsonObject().has("sources") && getJsonObject().getJSONObject("sources").has("PORTAL")) {return getJsonObject().getJSONObject("sources").get("PORTAL").toString();} else { return null; }}
	public String getProviderOrganisationname() {if (getJsonObject().has("provider") && getJsonObject().getJSONObject("provider").has("organisationName")) {return getJsonObject().getJSONObject("provider").get("organisationName").toString();} else { return null; }}
	public String getProviderOrganisationurl() {if (getJsonObject().has("provider") && getJsonObject().getJSONObject("provider").has("organisationUrl")) {return getJsonObject().getJSONObject("provider").get("organisationUrl").toString();} else { return null; }}
}