package com.krymlov.lab1.export;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.ItemEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ItemDataExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ItemEntity> listItems;

    public ItemDataExcelExporter(List<ItemEntity> listItems) {
        this.listItems = listItems;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Items");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "NAME", style);
        createCell(row, 2, "INFO", style);
        createCell(row, 3, "PRICE", style);
        createCell(row, 4, "BRAND", style);
        createCell(row, 5, "CATEGORY", style);
        createCell(row, 6, "SELLER", style);


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (ItemEntity item : listItems) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, item.getId(), style);
            createCell(row, columnCount++, item.getName(), style);
            createCell(row, columnCount++, item.getInfo(), style);
            createCell(row, columnCount++, item.getPrice(), style);
            createCell(row, columnCount++, item.getBrand().getName(), style);
            createCell(row, columnCount++, item.getCategory().getName(), style);
            createCell(row, columnCount++, item.getSeller().getName(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
