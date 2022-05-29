package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        StringBuilder log = new StringBuilder("Лог установки программы: ");

        if (new File("D://Birdie/Java/Other/Games/src").mkdir()) {
            log.append("создан католог /src; ");
        }
        if (new File("D://Birdie/Java/Other/Games/res").mkdir()) {
            log.append("создан католог /res; ");
        }
        if (new File("D://Birdie/Java/Other/Games/savegames").mkdir()) {
            log.append("создан католог /savegames; ");
        }
        if (new File("D://Birdie/Java/Other/Games/temp").mkdir()) {
            log.append("создан католог /temp; ");
        }
        if (new File("D://Birdie/Java/Other/Games/src/main").mkdir()) {
            log.append("создан католог /scr/main; ");
        }
        if (new File("D://Birdie/Java/Other/Games/src/test").mkdir()) {
            log.append("создан католог /scr/test; ");
        }
        if (new File("D://Birdie/Java/Other/Games/res/drawables").mkdir()) {
            log.append("создан католог /res/drawables; ");
        }
        if (new File("D://Birdie/Java/Other/Games/res/vectors").mkdir()) {
            log.append("создан католог /res/vectors; ");
        }
        if (new File("D://Birdie/Java/Other/Games/res/icons").mkdir()) {
            log.append("создан католог /res/icons; ");
        }

        File temp = new File("D://Birdie/Java/Other/Games/temp", "temp.txt");

        try {
            if (new File("D://Birdie/Java/Other/Games/src/main", "Main.java").createNewFile()) {
                log.append("создан файл Main.java в каталоге /scr/main; ");
            }
            if (new File("D://Birdie/Java/Other/Games/src/main", "Utils.java").createNewFile()) {
                log.append("создан файл Utils.java в каталоге /scr/main; ");
            }
            if (temp.createNewFile()) {
                log.append("создан файл temp.txt в каталоге /temp; ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(log.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        GameProgress gameProgress1 = new GameProgress(9, 8, 6, 72.2);
        GameProgress gameProgress2 = new GameProgress(10, 2, 7, 29.9);
        GameProgress gameProgress3 = new GameProgress(2, 6, 9, 50.98);

        File save1 = new File("D://Birdie/Java/Other/Games/savegames/gameProgress1.dat");
        File save2 = new File("D://Birdie/Java/Other/Games/savegames/gameProgress2.dat");
        File save3 = new File("D://Birdie/Java/Other/Games/savegames/gameProgress3.dat");

        saveGame(save1.getPath(), gameProgress1);
        saveGame(save2.getPath(), gameProgress2);
        saveGame(save3.getPath(), gameProgress3);

        List<String> filesPathList = Arrays.asList(save1.getPath(),save2.getPath(),save3.getPath());
        int count = 1;
        zipFiles("D://Birdie/Java/Other/Games/savegames/zip.zip", filesPathList, count);

        save1.delete();
        save2.delete();
        save3.delete();
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (ObjectOutputStream objectoutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectoutputStream.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> filesPathList, int count) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String file : filesPathList) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry("gameProgress" + count + ".dat");
                    count++;
                    zipOutputStream.putNextEntry(entry);
                    byte[] bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
                    zipOutputStream.write(bytes);
                    zipOutputStream.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}