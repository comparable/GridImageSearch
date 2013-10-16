package com.julie.gridimagesearch;

import java.io.Serializable;

public class Filter  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8340737774992159839L;
	
	private String colorFilter;
	private String typeFilter;
	private String siteFilter;
	private String sizeFilter;
	
	public Filter(String size, String color,String type,String site){
		this.sizeFilter = size;
		this.colorFilter = color;
		this.typeFilter = type;
		this.siteFilter = site;
		
	}
	
	public String getSize(){
		return sizeFilter;
	}
	
	public String getColor(){
		return colorFilter;
	}
	public String getType(){
		return typeFilter;
	}
	public String getSite(){
		return siteFilter;
	}
	
}
