package parsing;
import java.io.*;

public class Config {
    private String dbHost;
    private String dbPort;
    private String dbUser;
    private String dbPass;
    private String dbName;
    private String dbParsing;

    public String getDbHost() {
        return dbHost;
    }
    private void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }
    public String getDbPort() {
        return dbPort;
    }
    private void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }
    public String getDbUser() {
        return dbUser;
    }
    private void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
    public String getDbPass() {
        return dbPass;
    }
    private void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }
    public String getDbName() {
        return dbName;
    }
    private void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public String getDbParsing() {
        return dbParsing;
    }
    private void setDbParsing(String dbParsing) {
        this.dbParsing = dbParsing;
    }

    @Override
    public String toString() {
        return "parsing.Config{" +
                "dbHost='" + dbHost + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPass='" + dbPass + '\'' +
                ", dbName='" + dbName + '\'' +
                ", dbParsing='" + dbParsing + '\'' +
                '}';
    }
/*
   readconfig() - читает файл конфига, выбирает и записывает данные в переменные класса Config
 */
    public void readconfig() //Чтение файла конфига
    {
        String[] arrayConfig = new String[6];

        try (BufferedReader br = new BufferedReader(new FileReader("src/Config2.txt"))) {
            if (br.ready()) {
                int i = 0;
                String tmpconfig;
                while ((tmpconfig = br.readLine()) != null) {
                    arrayConfig[i] = tmpconfig;
                    i++;
                }
                setDbHost(arrayConfig[0].replaceAll("dbServer=", ""));
                setDbPort(arrayConfig[1].replaceAll("dbPort=", ""));
                setDbName(arrayConfig[2].replaceAll("dbName=", ""));
                setDbUser(arrayConfig[3].replaceAll("dbLogin=", ""));
                setDbPass(arrayConfig[4].replaceAll("dbPassword=", ""));
                setDbParsing(arrayConfig[5].replaceAll("dbParsing=", ""));

                br.close();
            }
            else {
                System.out.println("Config file empty!");
                br.close();
            }
        } catch (IOException ex) {
           System.out.println(ex.getMessage());
        }
    }
}
