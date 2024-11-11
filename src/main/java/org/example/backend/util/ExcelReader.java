package org.example.backend.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExcelReader {

  private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

  public <T> List<T> readExcel(String fileUrl, Class<T> clazz) {
    List<T> entityList = new ArrayList<>();
    try (InputStream inputStream = new URL(fileUrl).openStream();
        Workbook workbook = new XSSFWorkbook(inputStream)) {

      Sheet sheet = workbook.getSheetAt(0);

      // 遍历每一行，从第二行开始读取（跳过标题行）
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row != null) {
          T entity = clazz.getDeclaredConstructor().newInstance();
          Field[] fields = clazz.getDeclaredFields();

          for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            Cell cell = row.getCell(j);

            if (cell != null) {
              assignCellValueToField(cell, fields[j], entity);
            } else {
              setDefaultForNullField(fields[j], entity);
            }
          }
          entityList.add(entity);
        }
      }
    } catch (Exception e) {
      logger.error("Failed to read excel file: ", e);
    }
    return entityList;
  }

  private <T> void assignCellValueToField(Cell cell, Field field, T entity) throws IllegalAccessException {
    switch (cell.getCellType()) {
      case STRING -> {
        if (field.getType() == String.class) {
          field.set(entity, cell.getStringCellValue());
        }
      }
      case NUMERIC -> {
        if (DateUtil.isCellDateFormatted(cell) && field.getType() == Date.class) {
          field.set(entity, cell.getDateCellValue());
        } else if (field.getType() == int.class || field.getType() == Integer.class) {
          field.set(entity, (int) cell.getNumericCellValue());
        } else if (field.getType() == double.class || field.getType() == Double.class) {
          field.set(entity, cell.getNumericCellValue());
        }
      }
      case BOOLEAN -> {
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
          field.set(entity, cell.getBooleanCellValue());
        }
      }
      default -> logger.warn("Unsupported cell type for field {} in entity {}", field.getName(), entity.getClass().getSimpleName());
    }
  }

  private <T> void setDefaultForNullField(Field field, T entity) throws IllegalAccessException {
    if (field.getType() == String.class) {
      field.set(entity, "");
    } else if (field.getType() == int.class || field.getType() == Integer.class) {
      field.set(entity, 0);
    } else if (field.getType() == double.class || field.getType() == Double.class) {
      field.set(entity, 0.0);
    } else if (field.getType() == Date.class) {
      field.set(entity, null); // 可以根据需要设置默认日期
    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
      field.set(entity, false);
    }
  }
}
