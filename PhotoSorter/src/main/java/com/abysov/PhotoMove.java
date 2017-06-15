package com.abysov;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

class PhotoMove {
    /**
     * В классе PhotoMove содержатся методы pathCreate и fileMove, которые обеспечивают
     * проверку существования сетевой папки и непосредственное перемещение фотографии соответственно.
     */
    private String xlsBaseFilePath;  //Путь к файлу base.xls
    private String xlsBaseInfoSheet;               //Название листа с данными
    private String outputFolderPath;          //Путь к общей папке синхронизации
    private String inputFolderPath;           //Путь к корневой папке, куда будут перемещаться фотографии
    private String fileSeparator;                     //Разделитель для составления пути
    private String subPath;//Вставка в путь к сетевой папке

    public void setXlsBaseFilePath(String xlsBaseFilePath) {
        this.xlsBaseFilePath = xlsBaseFilePath;
    }

    public void setXlsBaseInfoSheet(String xlsBaseInfoSheet) {
        this.xlsBaseInfoSheet = xlsBaseInfoSheet;
    }

    public void setOutputFolderPath(String outputFolderPath) {
        this.outputFolderPath = outputFolderPath;
    }

    public void setInputFolderPath(String inputFolderPath) {
        this.inputFolderPath = inputFolderPath;
    }

    public void setFileSeparator(String fileSeparator) {
        this.fileSeparator = fileSeparator;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    public void pathCreate() {   //Метод проверяет существования пути к файлу

        XlsData xlsDataObj = new XlsData();         //Создается новый объект класса XlsData для импорта данных из фвйла base.xls
        String[][] xlsData = xlsDataObj.xlsImport(xlsBaseFilePath, xlsBaseInfoSheet);
        String checkFolderPath;
        String checkStopper = "checkStopper";       //Эта переменная позволяет делать только 1 проверку для всех фотографий пользователя
        File photoList = new File(outputFolderPath);
        String[] listPhotos = photoList.list();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < listPhotos.length; i++) {
            if (!listPhotos[i].contains(checkStopper)) {//Проверка существования папки пропускается, если уже была проверена хотя бы одна фотография по пользователю
                for (int j = 0; j < xlsData[0].length; j++) {
                    if (listPhotos[i].contains(xlsData[0][j])) {//Проверяется существование сетевой папки, если ее нет, создается новая
                        checkFolderPath = stringBuilder.append(inputFolderPath)
                                .append(fileSeparator).append(xlsData[1][j])
                                .append(fileSeparator).append(xlsData[2][j])
                                .append(fileSeparator).append(subPath)
                                .append(fileSeparator).append(xlsData[0][j]).toString();
                        stringBuilder = new StringBuilder();
                        checkStopper = xlsData[0][j];
                        try {
                            File folder = new File(checkFolderPath);
                            boolean bool = false;
                            bool = folder.mkdirs();
                            System.out.println("Создан путь - " + checkFolderPath);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    public void fileMove() throws IOException {           //Метод перемещает фотографию по указанному пути (копирование + удаление)

        File photoList = new File(outputFolderPath);
        String[] listPhotos = photoList.list();
        StringBuilder stringBuilder = new StringBuilder();
        String folderPath;
        XlsData xlsObj = new XlsData();
        String [][] xlsData = xlsObj.xlsImport(xlsBaseFilePath, xlsBaseInfoSheet);

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        for (int i = 0; i < listPhotos.length; i++) {
            for (int j = 0; j < xlsData[0].length; j++) {
                if (listPhotos[i].contains(xlsData[0][j])){
                    //Чтение и копирование файлов
                    try {
                        folderPath = stringBuilder.append(outputFolderPath)
                                                  .append(fileSeparator)
                                                  .append(listPhotos[i]).toString();
                        sourceChannel = new FileInputStream(folderPath).getChannel();
                        stringBuilder = new StringBuilder();

                        folderPath = stringBuilder.append(inputFolderPath)
                                                  .append(fileSeparator).append(xlsData[1][j])
                                                  .append(fileSeparator).append(xlsData[2][j])
                                                  .append(fileSeparator).append(subPath)
                                                  .append(fileSeparator).append(xlsData[0][j])
                                                  .append(fileSeparator).append(listPhotos[i]).toString();

                        destChannel = new FileOutputStream(folderPath).getChannel();
                        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
                        stringBuilder = new StringBuilder();
                    }
                    catch (IOException e){
                        System.out.println("Невозможно скопировать файл " + listPhotos[i]);
                    }
                    finally{
                        sourceChannel.close();
                        destChannel.close();
                    }
                    //Удаление исходного файла
                    try {
                        Path path = FileSystems.getDefault().getPath(stringBuilder.append(outputFolderPath)
                                .append(fileSeparator)
                                .append(listPhotos[i]).toString());
                        stringBuilder = new StringBuilder();
                        Files.delete(path);
                        System.out.println("Перемещен файл " + listPhotos[i]);
                        break;
                    }
                    catch (IOException e){
                        System.out.println("Невозможно удалить файл " + listPhotos[i]);
                    }
                }
            }
        }
        System.out.println("Перемещение файлов завершено.");
    }
}