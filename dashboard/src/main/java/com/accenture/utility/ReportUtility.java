package com.accenture.utility;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.accenture.dao.entity.TDhhSquadMst;
import com.accenture.dao.entity.TDhhSurveyMst;
import com.accenture.dao.entity.TDhhUserMst;


@Component
public class ReportUtility {
	private static final Logger logger = LoggerFactory.getLogger(ReportUtility.class);
	
	public void createExcelColumns(TDhhSurveyMst survey,XSSFWorkbook wb, XSSFSheet workingSheet, String header){
		List<TDhhSquadMst>  squadList = survey.getTDhhSquadMsts();
		Row row1 = workingSheet.createRow(1);
		Row row2 = workingSheet.createRow(2);
		Row row3 = workingSheet.createRow(3);
		Row row4 = workingSheet.createRow(4);
		
		createDataCell(wb,"SQUADS",0,row1);
		createDataCell(wb,"SURVEY TAKEN BY",0,row2);
		createDataCell(wb,header,1,row3);
		
		int cellCount = 1;
		int count = 0;
		for(TDhhSquadMst squadMst : squadList){	
			count = cellCount;
			if(squadMst.getActiveFlag().equalsIgnoreCase("Y")){
				createDataCellAlignLeft(wb,squadMst.getSquadName(),cellCount,row1);
				List<TDhhUserMst> userList = squadMst.getTDhhUserMsts();
				for(TDhhUserMst userMst : userList){					
					createDataCellAlignLeft(wb,userMst.getUserName(),cellCount,row2);
					createDataCellAlignLeft(wb," ANSWER ",cellCount,row4);
					int cell2 = cellCount+1;
					createDataCellAlignLeft(wb," COMMENT ",cell2,row4);
					createDataCellAlignLeft(wb,"",cell2,row2);
					createDataCellAlignLeft(wb,"",cell2,row1);
					createDataCellAlignLeft(wb,"",cell2,row3);
					workingSheet.addMergedRegion(new CellRangeAddress(row2.getRowNum(), row2.getRowNum(),cellCount, cell2));
					workingSheet.autoSizeColumn(cellCount);	
					if(null == row1.getCell(cellCount)){
						createDataCellAlignLeft(wb,"",cellCount,row1);
						createDataCellAlignLeft(wb,"",cellCount,row3);
					}
					cellCount = cellCount+2;					
					
				}
				if(userList.size()>0&&cellCount>2) {
				workingSheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(),count, cellCount-1));
				}
			
				
			}
		}
		if(cellCount>2){
			workingSheet.addMergedRegion(new CellRangeAddress(row3.getRowNum(), row3.getRowNum(),1, cellCount-1));
			}
	}
	
	private void createDataCellAlignLeft(XSSFWorkbook wb, String value, int cellCount, Row row) {
		Cell cell = row.createCell(cellCount++);
		cell.setCellStyle(centreAlignDataCellStyle(wb));
		cell.setCellValue(value);
	}
	
	
	private XSSFCellStyle centreAlignDataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();
		defaultFont.setBold(true);
		style.setFont(defaultFont);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
	
	private void createDataCell(XSSFWorkbook wb, String value, int cellCount, Row row) {
		Cell cell = row.createCell(cellCount++);
		cell.setCellStyle(dataCellStyle(wb));
		cell.setCellValue(value);
	}
	
	
	private XSSFCellStyle dataCellStyle(final XSSFWorkbook wb) {
		XSSFCellStyle style;
		style = wb.createCellStyle();
		XSSFFont defaultFont = wb.createFont();
		defaultFont.setBold(true);
		style.setFont(defaultFont);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}
}
