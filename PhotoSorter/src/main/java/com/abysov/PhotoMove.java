package com.abysov;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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

    protected void setXlsBaseFilePath(String xlsBaseFilePath) {
        this.xlsBaseFilePath = xlsBaseFilePath;
    }

    protected void setXlsBaseInfoSheet(String xlsBaseInfoSheet) {
        this.xlsBaseInfoSheet = xlsBaseInfoSheet;
    }

    protected void setOutputFolderPath(String outputFolderPath) {
        this.outputFolderPath = outputFolderPath;
    }

    protected void setInputFolderPath(String inputFolderPath) {
        this.inputFolderPath = inputFolderPath;
    }

    protected void setFileSeparator(String fileSeparator) {
        this.fileSeparator = fileSeparator;
    }

    protected void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    protected void pathCreate() {//Метод проверяет существования пути к файлу

        XlsData xlsDataObj = new XlsData();//Создается новый объект класса XlsData для импорта данных из фвйла base.xls
        String[][] xlsData = xlsDataObj.xlsImport(xlsBaseFilePath, xlsBaseInfoSheet);
        String checkFolderPath;
        String checkStopper = "checkStopper";//Эта переменная позволяет делать только 1 проверку для всех фотографий пользователя
        String[] listPhotos = null;

        try {
            File FileToRead = new File(new URI(outputFolderPath));
            listPhotos = FileToRead.list();
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }

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
                            checkFolderPath = checkFolderPath.replaceAll(" ","%20");
                            File folder = new File(new URI(checkFolderPath));
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

    protected void fileMove() throws IOException{//Метод перемещает фотографию по указанному пути (копирование + удаление)

        String[] listPhotos = null;
        File fileToRead;

        try {
            fileToRead = new File(new URI(outputFolderPath));
            listPhotos = fileToRead.list();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        String folderPath;
        XlsData xlsObj = new XlsData();
        String[][] xlsData = xlsObj.xlsImport(xlsBaseFilePath, xlsBaseInfoSheet);

        for (int i = 0; i < listPhotos.length; i++) {
            for (int j = 0; j < xlsData[0].length; j++) {
                if (listPhotos[i].contains(xlsData[0][j])) {
                    //Чтение и копирование файлов

                    //folderPath = stringBuilder.append(outputFolderPath)
                    folderPath = stringBuilder.append("\\\\192.168.208.35\\upload")
                            .append("\\")
                            .append(listPhotos[i]).toString();

                    File sourceFile = new File(folderPath);

                    stringBuilder = new StringBuilder();

                    //folderPath = stringBuilder.append(inputFolderPath)
                    folderPath = stringBuilder.append("\\\\192.168.11.122\\Report\\Hermes")
                            .append("\\").append(xlsData[1][j])
                            .append("\\").append(xlsData[2][j])
                            .append("\\").append(subPath)
                            .append("\\").append(xlsData[0][j])
                            .append("\\").append(listPhotos[i]).toString();

                    FileInputStream inputStream = null;
                    FileOutputStream outputStream = null;

                    File destFile = new File(folderPath);


                    try {

                        inputStream = new FileInputStream (sourceFile);
                        outputStream = new FileOutputStream(destFile);

                        byte buffer [] = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }

                    catch (IOException e){
                        System.out.println("Невозможно скопировать файл " + listPhotos[i]);
                        System.exit(0);
                    }
                    finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }
                    }
                    stringBuilder = new StringBuilder();


                    //Удаление исходного файла
                    try {
                        //outputFolderPath = outputFolderPath.substring(5);
                        Path path = FileSystems.getDefault().getPath(stringBuilder.append("\\\\192.168.208.35\\upload")
                                .append("\\")
                                .append(listPhotos[i]).toString());
                        stringBuilder = new StringBuilder();
                        Files.delete(path);
                        System.out.println("Перемещен файл " + listPhotos[i]);
                        break;
                    } catch (IOException e) {
                        System.out.println("Невозможно удалить файл " + listPhotos[i]);
                        System.exit(0);
                    }
                }
            }
        }
        System.out.println("Перемещение файлов завершено.");
    }
}