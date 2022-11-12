package com.dg.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author TheFool
 */
public class PoiUtil {
    public static void downLoadExcel(List<String> columnNames, List<String> keyList, List<?> objList, String fileName, HttpServletResponse response) {
        if (objList.size() > 1000000){
            List<List<?>> lists = fixedGrouping(objList, 1000000);
            for (int i = 0; i < lists.size(); i++) {
                downLoadExcelManySheet(columnNames,keyList,lists,fileName,0,response);
            }
        }else {
            downLoadExcelSingleSheet(columnNames,keyList,objList,fileName,response);
        }
    }

    private static void downLoadExcelManySheet(List<String> columnNames, List<String> keyList, List<List<?>> objList, String fileName, int num, HttpServletResponse response) {
        Workbook wb = null;
        //判断文件类型 03或是07
        if (isExcel2007(fileName)) {
            wb = new SXSSFWorkbook();
        }
        if (isExcel2003(fileName)) {
            wb = new HSSFWorkbook();
        }
        //创建sheet
        for (int a = 0; a < objList.size(); a++) {
            List<?> objects = objList.get(a);
            Sheet sheet = wb.createSheet("sheet" + a);
            //创建第一行，存放key
            Row row = sheet.createRow(0);
            for (int i = 0; i < keyList.size(); i++) {
                row.createCell(i).setCellValue(keyList.get(i));
            }
            //先创建object空对象
            Map<String, Object> project = null;
            for (int i = 0; i < objects.size(); i++) {
                Row row1 = sheet.createRow(i + 1);
                for (int j = 0; j < columnNames.size(); j++) {
                    //创建obj实例
                    project = (Map<String, Object>) objects.get(i);
                    row1.createCell(j).setCellValue((String) project.get(columnNames.get(j)));
                }
            }
        }
        //将文件响应到电脑
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downLoadExcelSingleSheet(List<String> columnNames, List<String> keyList, List<?> objList, String fileName,HttpServletResponse response) {
        Workbook wb = null;
        //判断文件类型 03或是07
        if (isExcel2007(fileName)) {
            wb = new SXSSFWorkbook();
        }
        if (isExcel2003(fileName)) {
            wb = new HSSFWorkbook();
        }
        //创建sheet
        Sheet sheet = wb.createSheet();

        //创建第一行，存放key
        Row row = sheet.createRow(0);
        for (int i = 0; i < keyList.size(); i++) {
            row.createCell(i).setCellValue(keyList.get(i));
        }
        //先创建object空对象
        Map<String, Object> project = null;
        for (int i = 0; i < objList.size(); i++) {
            Row row1 = sheet.createRow(i+1);
            for (int j = 0; j < columnNames.size(); j++) {
                //创建obj实例
                project = (Map<String, Object>) objList.get(i);
                row1.createCell(j).setCellValue(project.get(columnNames.get(i)) + "");
            }
        }
        setSizeColumn(sheet,columnNames.size());
        //将文件响应到电脑
        try {

            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 判断是否是03的excel:xls
    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // 判断是否是07的excel:xlsx
    private static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    private static void setSizeColumn(Sheet sheet, int columnLength) {
        for (int columnNum = 0; columnNum <= columnLength; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum()+1; rowNum++) {
                Row currentRow; // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow =  sheet.createRow(rowNum);
                } else {
                    currentRow =  sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }

                }
            }
//            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    private static List<List<?>> fixedGrouping(List<?> source, Integer n) {
        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<?>> result = new ArrayList<>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<?> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<?> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }
}
