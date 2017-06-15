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
 */

package com.abysov;

import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException {

        StatusFrame statusFrame = new StatusFrame();
        statusFrame.setLocationRelativeTo(null);
        statusFrame.setResizable(false);
        statusFrame.setVisible(true);

        PhotoMove move = new PhotoMove();

        move.setXlsBaseFilePath("H:/upload/base.xls");
        move.setXlsBaseInfoSheet("base");
        move.setOutputFolderPath("H:/upload");
        move.setInputFolderPath("Z:/Hermes");
        move.setFileSeparator("/");
        move.setSubPath("Фото торговых представителей");

        move.pathCreate();
        move.fileMove();
        System.out.println("Перемещение файлов завершено.");
    }
}
