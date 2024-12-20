package org.example.backend.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExcelReader {

  private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

  /**
   * 读取Excel文件中的数据，并将每行数据转换为指定类型的对象
   *
   * @param fileUrl Excel文件的路径
   * @param clazz   指定类型T的Class对象，用于创建实体对象
   * @return 包含转换后的对象的列表
   */
  public <T> List<T> readExcel(String fileUrl, Class<T> clazz) {
    List<T> entityList = new ArrayList<>();
    try (FileInputStream inputStream = new FileInputStream(fileUrl);
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

  /**
   * 将单元格的值分配给实体类中的字段
   * 此方法根据单元格的类型，将单元格中的值转换为适当的数据类型，并将其设置到给定实体类的指定字段中
   *
   * @param cell 单元格，包含要分配给字段的值
   * @param field 实体类中的字段，将从单元格中获取的值设置到该字段
   * @param entity 实体对象，其字段将被设置
   * @throws IllegalAccessException 如果字段设置操作不合法时抛出此异常
   */
  private <T> void assignCellValueToField(Cell cell, Field field, T entity) throws IllegalAccessException {
    switch (cell.getCellType()) {
      case STRING -> {
        if (field.getType() == String.class) {
          field.set(entity, cell.getStringCellValue());
        } else if (field.getType() == Date.class) {
          try {
            // 尝试解析字符串为日期
            field.set(entity, parseDateStringToDate(cell.getStringCellValue()));
          } catch (ParseException e) {
            logger.warn("Failed to parse date string: {}", cell.getStringCellValue(), e);
            setDefaultForNullField(field, entity);
          }
        }
      }
      case NUMERIC -> {
        if (DateUtil.isCellDateFormatted(cell)) {
          if (field.getType() == Date.class) {
            field.set(entity, cell.getDateCellValue());
          } else if (field.getType() == String.class) {
            field.set(entity, new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()));
          }
        } else {
          double numericValue = cell.getNumericCellValue();
          if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(entity, (int) numericValue);
          } else if (field.getType() == double.class || field.getType() == Double.class) {
            field.set(entity, numericValue);
          } else if (field.getType() == String.class) {
            field.set(entity, String.valueOf(numericValue));
          }
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

  private Date parseDateStringToDate(String dateString) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return dateFormat.parse(dateString);
    } catch (ParseException e) {
      // 尝试另一种格式
      dateFormat.applyPattern("MM/dd/yyyy");
      return dateFormat.parse(dateString);
    }
  }


  /**
   * 为实体类中的null字段设置默认值
   * 此方法通过反射检查给定实体类中的字段，并根据字段类型设置默认值
   * 主要解决了在实体类初始化或数据加载过程中，对于未赋值的字段给予合理默认值的问题
   *
   * @param field 要检查并可能设置默认值的字段对象
   * @param entity 实体类实例，通过此参数可以访问和设置字段值
   * @throws IllegalAccessException 如果字段不可访问或设置失败
   */
  private <T> void setDefaultForNullField(Field field, T entity) throws IllegalAccessException {
    if (field.getType() == String.class) {
      field.set(entity, null);
    } else if (field.getType() == int.class || field.getType() == Integer.class) {
      field.set(entity, null);
    } else if (field.getType() == double.class || field.getType() == Double.class) {
      field.set(entity, null);
    } else if (field.getType() == Date.class) {
      field.set(entity, null);
    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
      field.set(entity, false);
    }
  }
}
