package com.abysov;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class XlsData {
    /**
     * В классе XlsData содержится метод xlsImport для создания двумерного массива, содержащего
     * данные для проверки, создания сетевых папок и дальнейшего перемещения в них фотографий
     */
    //Метод для загрузки данных в массив
    protected String [][] xlsImport(String filePath, String sheetName) {
        //Создаются объекты для работы с .xls файлом
        POIFSFileSystem fileSystem;
        HSSFWorkbook xlsBaseFile;
        HSSFSheet xlsSheet = null;
        //Подключение к .xls книге
        try {

            fileSystem = new POIFSFileSystem(new FileInputStream(new File(new URI(filePath))));
            xlsBaseFile = new HSSFWorkbook(fileSystem);
            xlsSheet = xlsBaseFile.getSheet(sheetName);
        }
        catch (IOException e){
            System.out.println("Не обнаружен валидный файл base.xls");
            System.exit(0);
        }
        catch (URISyntaxException e){
            System.out.println("Не найден сетевой путь .xls файла");
            System.exit(0);
        }

        //Создаются массивы, для записи параметров из каждой ячейки таблицы
        String[] photoParametersEmployee = new String[xlsSheet.getLastRowNum()];
        String[] photoParametersBU = new String[xlsSheet.getLastRowNum()];
        String[] photoParametersRegion = new String[xlsSheet.getLastRowNum()];
        String[] photoParametersPosition = new String[xlsSheet.getLastRowNum()];
        String[][] photoParameters = new String [4][];
        //Заполнение массива данными
        for (int i = 1; i <= xlsSheet.getLastRowNum(); i++) {
            HSSFRow xlsRow = xlsSheet.getRow(i);
            HSSFCell xlsCellEmployee = xlsRow.getCell(0);
            photoParametersEmployee[i - 1] = xlsCellEmployee.getStringCellValue();
            HSSFCell xlsCellBU = xlsRow.getCell(1);
            photoParametersBU[i - 1] = xlsCellBU.getStringCellValue();
            HSSFCell xlsRegion = xlsRow.getCell(2);
            photoParametersRegion[i - 1] = xlsRegion.getStringCellValue();
            HSSFCell xlsPosition = xlsRow.getCell(3);
            photoParametersPosition[i - 1] = xlsPosition.getStringCellValue();
        }
        photoParameters[0] = photoParametersEmployee;
        photoParameters[1] = photoParametersBU;
        photoParameters[2] = photoParametersRegion;
        photoParameters[3] = photoParametersPosition;

        return photoParameters;
    }
}
