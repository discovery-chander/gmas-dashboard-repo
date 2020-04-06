package com.accenture.gmas.constants;

public class MessageConstant {
	private MessageConstant(){
		throw new IllegalStateException("Message constant class");
	}
	public static final String OK_CODE="OK";
	public static final String NOT_OK_CODE="KO";
	public static final String NO_DATA_FOUND="No data found.";
	public static final String DATA_FOUND="Data retrieved successfully";
	public static final String EXECUTION_TIME="\nExecution time: ";
	public static final String DATA_SAVED="Data saved successfully";
	public static final String PPT_GENERATED="PPT generated successfully";
	public static final String XLS_GENERATED="EXCEL generated successfully";
	
	public static final String BRACES = "{}{}{}";
	public static final String PRIORITY_HIGH = "HIGH";
	public static final String PRIORITY_LOW = "LOW";
	public static final String FRW_SLASH = "/";
	public static final String DATA_UPDATED="Data updated successfully";
	public static final String DEMO_PROJECT="Demo Project";
	public static final int STARTINDEX=0;
	public static final int DEMOSIZE=10;
	public static final String FILE_DELETED= "File deleted successfully";
	
	public static final String TARGETS_NOT_PRESENT_MSG = "Targets not set for this KPI. Please go to Set KPI target page to complete target setting.";
	
	public static final String DATA_DELETED= "Data deleted successfully";
	
	public static final String TYPE_BUSINESS = "Business";
	public static final String TYPE_OPERATIONAL = "Operational";
	
	public static final String BUSINESS_KPI = "BKPI";
	public static final String OPERATIONAL_KPI = "OKPI";
	
	public static final String PARTIAL_DATA="Data retrieved partially";
	
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final String PARTIAL = "Partial";
	public static final String NOT_APPLICABLE = "N/A";
	
	public static final String SUCCESS_STATUS = "0";
	public static final String ERROR_STATUS = "1";
}
