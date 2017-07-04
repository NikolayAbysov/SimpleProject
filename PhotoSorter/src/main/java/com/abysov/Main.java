/**
Приложение предназначено для разбора и сортировки фотографий сотрудников из папки синхронизации
 по сетевым папкам сотрудников.

 Для корректной работы приложения необходим файл base.xls, со следующей структурой:

 - лист, содержащий информацию. Имеет название "base"
 - колонка "Employee", содержащая логин сотрудника
 - колонка "BU", содержащая бизнес-юнит сотрудниика
 - колонка "Region", содержащая регион сотрудника
 - колонка "STAT", содержащую должность сотрудника

 На основании этих колонок формируется путь к сетевой папке.

 Компоненты приложения:

 - JConsole - обрабатывает консольный вывод для дальнейшей передачи данных в окно StatusFrame
 - StatusFrame - окно с выводом консольного содержимого
 - PhotoMove - создает пути для перемещения и перемещает фотографии
 - XlsData - импортирует данные из .xls файла для создания путей и перемещения фотографий

 */

package com.abysov;

import java.io.IOException;
import java.net.URISyntaxException;

class Main {

    public static void main(String[] args) throws IOException {

        StatusFrame statusFrame = new StatusFrame();
        statusFrame.setLocationRelativeTo(null);
        statusFrame.setResizable(false);
        statusFrame.setVisible(true);

        AppSettings appSettings = new AppSettings();

        appSettings.settingsLoad();
        System.out.println("Настройки загружены...");

        PhotoMove move = new PhotoMove();
        move.setXlsBaseFilePath(appSettings.getXlsBaseFilePath());
        move.setXlsBaseInfoSheet(appSettings.getXlsBaseInfoSheet());
        move.setOutputFolderPath(appSettings.getOutputFolderPath());
        move.setInputFolderPath(appSettings.getInputFolderPath());
        move.setFileSeparator(appSettings.getFileSeparator());
        move.setSubPath(appSettings.getSubPath());

        move.pathCreate();
        move.fileMove();

        System.exit(0);
    }
}
