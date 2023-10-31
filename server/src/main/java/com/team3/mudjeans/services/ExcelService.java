package com.team3.mudjeans.services;

import com.team3.mudjeans.utils.FileResourcesUtils;
import com.team3.mudjeans.entity.Jean;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    public Map<String, List<Jean>> handleFileData(File file) {

        try {
            Workbook book = WorkbookFactory.create(file);
            Sheet sheet = book.getSheetAt(0);
            DataFormatter d = new DataFormatter();

            List<Jean> list = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) continue;
                if (row.getRowNum() == 0) continue;
                if (row.getCell(0).getStringCellValue().equals("Total")) break;
                Jean jean = new Jean(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), parseWithDefault(d.formatCellValue(row.getCell(16))), parseWithDefault(d.formatCellValue(row.getCell(17))), parseWithDefault(d.formatCellValue(row.getCell(15))) + parseWithDefault(d.formatCellValue(row.getCell(14))) + parseWithDefault(d.formatCellValue(row.getCell(13))));
                list.add(jean);
            }
            return list.stream().collect(Collectors.groupingBy(Jean::getDescription));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int parseWithDefault(String toParseFrom) {
        return parseWithDefault(toParseFrom, 0);
    }

    private static int parseWithDefault(String toParseFrom, int defaultIntValue) {
        return toParseFrom.matches("-?\\d+") ? Integer.parseInt(toParseFrom) : defaultIntValue;
    }
}
