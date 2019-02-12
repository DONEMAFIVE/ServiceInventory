package parsing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<String> array = new ArrayList<>();
    private File folder = new File("input");
    /*
        scanFolder() - Функция возвращает массив файлов в папке
        String name = scanFolder()[i].getName() - присвоение i-го элемента массива в переменную
    */
    private File[] scanFolder() //функция получения списка всех файлов в папке
    {
        File[] listFiles = this.folder.listFiles();
        if (listFiles.length == 0) {
            System.out.println("CSV-файл(ы) не найден(ы)");
        }
        return listFiles;
    }
    /*
        readFiles() - Метод построчно читает содержмое файлов в папке и добавляет их в массив
    */
    private void readFiles() //Метод читает файлы
    {
        this.array = new ArrayList<>();
        try {
            for (int i = 0; i < scanFolder().length; i++) {
                FileInputStream fstream = new FileInputStream(scanFolder()[i]);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String name = scanFolder()[i].getName();
                int pos = name.lastIndexOf(".");
                if (pos > 0) {
                    name = name.substring(0, pos);
                }

                String tmp;
                while ((tmp = br.readLine()) != null)
                    this.array.add(name + ";" + tmp);
            }
        } catch (Exception ex) {
            System.err.println("error: " + ex);
        }
    }

    /*
        analyze() - Функция возвращает массив Inventory
        addParsedDataToDB(new Parser().analyze()) - в метод 'addParsedDataToDB' в качестве параметра передаём функцию, которая возвращает массив Inventory
        addParsedDataToDB() - метод добавления данных в БД
    */
    public Inventory[] analyze() //Функция анализа возвращает готовый массив данных
    {
        readFiles();

        Inventory[] inventoryArr = new Inventory[this.array.size()];

        for (int i = 0; i < this.array.size(); i++) {
            String[] tempArr = this.array.get(i).split(";");

            inventoryArr[i] = new Inventory(tempArr[0], searchName(tempArr), searchInumber(tempArr), searchCost(tempArr), searchCount(tempArr));
        }
        return inventoryArr;
    }


    /*
        moveFiles() - создаёт папку(archive)
        вызывает метод 'копирование' файлов в папку(archive)
        вызывает метод 'удаления' из папки(input)
    */
    public void moveFiles() throws IOException {
        File dir = new File("archive");
        boolean created = dir.mkdir();
        if (created) {
            System.out.println("[DEBUG]: Была создана папка: \"" + dir.getName() + "\" ");
        }
        copyDir(folder.getPath(), dir.getPath()); //От куда и куда
        deleteAllFilesFolder(folder.getPath()); //Удаление всех файлов в директории
    }

    /*
        copyDir() - Копирует файлы из одной папки в другую (archive)
        copyDir(folder.getPath(), dir.getPath()) - в параметры передаются имена папок 1) от куда 2) куда
     */
    private void copyDir(String sourceDirName, String targetSourceDir) throws IOException {
        File folder = new File(sourceDirName);
        File[] listOfFiles = folder.listFiles();

        Path destDir = Paths.get(targetSourceDir);
        if (listOfFiles != null)
            for (File file : listOfFiles)
                Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }

    /*
        deleteAllFilesFolder() - выполняет удаление файлов из папки(input)
        deleteAllFilesFolder(folder.getPath()) - в качестве параметра передается имя папки
     */
    private void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles()){
            if (myFile.isFile()) {
                System.gc(); //Cборщик мусора
                myFile.delete();
            }
        }
    }

    /*
        searchName() - Функция возвращает строку с наименованием имущества
        searchName(tempArr) - в качестве параметра передаётся массив строк
     */
    private String searchName(String[] s) //Поиск наименование имущества
    {
        String searchName;
        int result = 0;
        for (int i = 1; i < s.length; i++) //Цикл начинается с 1 потому что, первый элемент массива проходить не нужно)
        {
            if (!s[i].equals("")) {
                if (s[i].length() == 2)
                {
                    if (s[i].contains("ПК") || s[i].contains("пк")) {
                        searchName = s[i];
                        return searchName;
                    }
                }
                int check = 0;
                char[] tmp = s[i].toCharArray();
                if (tmp.length >= 2) {
                    for (char c : tmp) {
                        if (swap(c)) check++;
                        if (check >= 3) {
                            result = i;
                            break;
                        }
                    }
                    if (result == i) break;
                }
            }
        }
        searchName = s[result];
        return searchName;
    }

    private static boolean swap(char c) {
        switch (c) {
            case 'a':
                return true;
            case 'б':
                return true;
            case 'в':
                return true;
            case 'г':
                return true;
            case 'д':
                return true;
            case 'е':
                return true;
            case 'ё':
                return true;
            case 'ж':
                return true;
            case 'з':
                return true;
            case 'и':
                return true;
            case 'й':
                return true;
            case 'к':
                return true;
            case 'л':
                return true;
            case 'м':
                return true;
            case 'н':
                return true;
            case 'о':
                return true;
            case 'п':
                return true;
            case 'р':
                return true;
            case 'с':
                return true;
            case 'т':
                return true;
            case 'у':
                return true;
            case 'ф':
                return true;
            case 'х':
                return true;
            case 'ц':
                return true;
            case 'ч':
                return true;
            case 'ш':
                return true;
            case 'щ':
                return true;
            case 'ъ':
                return true;
            case 'ы':
                return true;
            case 'ь':
                return true;
            case 'э':
                return true;
            case 'ю':
                return true;
            case 'я':
                return true;
            case 'А':
                return true;
            case 'Б':
                return true;
            case 'В':
                return true;
            case 'Г':
                return true;
            case 'Д':
                return true;
            case 'Е':
                return true;
            case 'Ё':
                return true;
            case 'Ж':
                return true;
            case 'З':
                return true;
            case 'И':
                return true;
            case 'Й':
                return true;
            case 'К':
                return true;
            case 'Л':
                return true;
            case 'М':
                return true;
            case 'Н':
                return true;
            case 'О':
                return true;
            case 'П':
                return true;
            case 'Р':
                return true;
            case 'С':
                return true;
            case 'Т':
                return true;
            case 'У':
                return true;
            case 'Ф':
                return true;
            case 'Х':
                return true;
            case 'Ц':
                return true;
            case 'Ч':
                return true;
            case 'Ш':
                return true;
            case 'Щ':
                return true;
            case 'Ъ':
                return true;
            case 'Ы':
                return true;
            case 'Ь':
                return true;
            case 'Э':
                return true;
            case 'Ю':
                return true;
            case 'Я':
                return true;
        }
        return false;
    }

    /*
        searchInumber() - Функция возвращает строку с инвентарным номером
        searchInumber(tempArr) - в качестве параметра передаётся массив строк
     */
    private String searchInumber(String[] s) //Поиск инвентарного номера
    {
        String searchInumber;
        int result = 0;

        for (int i = 4; i < s.length; i++) //Цикл
        {
            if (!s[i].equals("")) {
                char[] tmp = s[i].toCharArray();
                if (tmp.length >= 2) {
                    for (char c : tmp) {
                        if (swapInumber(c)) {
                            result = i;
                            break;
                        }
                    }
                    if (result == i) break;
                }
            }
        }
        searchInumber = s[result];
        if (searchInumber.contains(",")) return "EmptyNumber";

        return searchInumber;
    }

    private boolean swapInumber(char c) {

        switch (c) {
            case '0':
                return true;
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
            case '.':
                return true;
            case 'B':
                return true;
            case 'A':
                return true;
            case '/':
                return true;
        }
        return false;
    }

    /*
        searchCost() - Функция возвращает строку со стоимостью
        searchCost(tempArr) - в качестве параметра передаётся массив строк
    */
    private String searchCost(String[] s) //Поиск стоимости
    {
        String searchCost;
        int result = 0;
        for (int i = 3; i < s.length; i++) //Цикл
        {
            if (s[i].equals("1,00"))
                return s[i];
            if (!s[i].equals("")) {
                int check = 0;
                char[] tmp = s[i].toCharArray();

                if (tmp.length >= 2) {
                    for (char c : tmp) {
                        if (Character.isDigit(c)) {
                            check++;
                        }
                        if (check >= 2) {
                            if (swapCost(c)) {
                                result = i;
                                break;
                            }
                        }
                    }
                    if (result == i) break;
                }
            }
        }
        searchCost = s[result];
        return searchCost;
    }

    private boolean swapCost(char c) {
        switch (c) {
            case ',':
                return true;
            case ' ':
                return true;
        }
        return false;
    }
    /*
        searchCount() - Функция возвращает строку с количеством
        searchCount(tempArr) - в качестве параметра передаётся массив строк
    */
    private String searchCount(String[] s) //Поиск количества
    {
        String searchCount;
        int result = 0;
        for (int i = 3; i < s.length; i++) //Цикл
        {
            if (!s[i].equals("")) {
                int check = 0;
                char[] tmp = s[i].toCharArray();

                if (tmp.length == 1) {
                    for (char c : tmp) {
                        if (Character.isDigit(c)) {
                            check++;
                        }
                        if (check >= 1) {
                            if (swapCount(c)) {
                                result = i;
                                break;
                            }
                        }
                    }
                    if (result == i) break;
                }
            }
        }
        searchCount = s[result];
        return searchCount;
    }

    private boolean swapCount(char c) {
        switch (c) {
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
        }
        return false;
    }
}
