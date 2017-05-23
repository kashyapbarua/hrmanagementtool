package com.zaloni.models;

import java.util.List;

public class MajorDimension {
	private String majorDimensions;
	private String range;
	private List<List<Object>> values;
	public String getMajorDimensions() {
		return majorDimensions;
	}
	public void setMajorDimensions(String majorDimensions) {
		this.majorDimensions = majorDimensions;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public List<List<Object>> getValues() {
		return values;
	}
	public void setValues(List<List<Object>> values) {
		this.values = values;
	}

}
