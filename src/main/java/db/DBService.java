package db;

import com.mysql.jdbc.Driver;
import org.json.JSONArray;
import org.json.JSONObject;
import parsing.Config;
import parsing.Inventory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class DBService {
    private Connection connection;

    public DBService() {
        this.connection = getMySQLConnection();
    }

    private Connection getMySQLConnection() //Подключение к БД
    {
        Config cfg = new Config();
        cfg.readconfig();
        StringBuilder url = new StringBuilder();
        url.
                append("jdbc:mysql://").
                append(cfg.getDbHost() + ":").
                append(cfg.getDbPort() + "/").
                append(cfg.getDbName() + "?").
                append("user=" + cfg.getDbUser() + "&").
                append("password=" + cfg.getDbPass());

        //System.out.println(url);

        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            Connection connection = DriverManager.getConnection(url.toString());
            System.out.println("Выполнено cоединение с БД!");
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }

    private void closeConnection() throws SQLException //Закрытие соединения с БД
    {
        connection.close();
    }

    //addParsedDataToDB() - Запускается только 1 раз, при первом заполнении БД в качестве параметра передаётся массив Inventory
    public void addParsedDataToDB(Inventory[] inventory) throws SQLException //Добавление распаршенных данных в БД
    {
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy");

        String sqlstr = "INSERT INTO `maintable` (`id`,`name`,`mol`,`inumber`,`cost`,`count`, `status`, `date`) VALUES (NULL,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        try {
            for (int i = 0; i < inventory.length; i++) {
                preparedStatement = connection.prepareStatement(sqlstr);

                preparedStatement.setString(1, inventory[i].getName());
                preparedStatement.setString(2, inventory[i].getMol());
                preparedStatement.setString(3, inventory[i].getInumber());
                preparedStatement.setString(4, inventory[i].getCost());
                preparedStatement.setString(5, inventory[i].getCount());
                preparedStatement.setString(6, "EmptyStatus");
                preparedStatement.setString(7, formatForDateNow.format(date)); //11-01-2019
                preparedStatement.execute();

                System.out.println(i + " Добавлено в БД: " + inventory[i].getName());
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            exp.getMessage();
        } finally {
            closeConnection();
        }
    }

    /*
     *   addDataToDB() - метод обновления записи в БД
     *  addDataToDB(mol, name, inumber, status, Integer.parseInt(date))
     */
    private int id;
    public void addDataToDB(String mol, String name, String inumber, String status, int date) throws SQLException //Добавление данных в Б
    {
        String sqlselect = "SELECT * FROM `maintable` WHERE `name`='" + name + "' AND `inumber`='" + inumber + "' AND `date`=" + date + "";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlselect);

        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }

        String sqlupdate = "UPDATE `maintable` SET `mol`=?, `status`=? WHERE `id`=" + id + "";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlupdate);
            preparedStatement.setString(1, mol);
            preparedStatement.setString(2, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     *   readValues() - функция возвращает все данные из БД в виде строки, в формате JSON
     */
    public String readValues() throws SQLException //Выборка всех данных из БД
    {
        String jData;
        JSONObject jObject;
        JSONObject jObjectData = new JSONObject();
        JSONArray jArray = new JSONArray();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `maintable`");

        while (resultSet.next()) {
            String mol = resultSet.getString("mol");
            String name = resultSet.getString("name");
            String number = resultSet.getString("inumber");
            String cost = resultSet.getString("cost");
            String count = resultSet.getString("count");
            String status = resultSet.getString("status");
            int date = resultSet.getInt("date");

            jObject = new JSONObject();
            jObject.put("mol", mol);
            jObject.put("name", name);
            jObject.put("number", number);
            jObject.put("cost", cost);
            jObject.put("count", count);
            jObject.put("status", status);
            jObject.put("date", date);
            jArray.put(jObject);
        }
        //jData = jArray.toString().substring(1, jArray.toString().length() - 1);
        jData = String.valueOf(jObjectData.put("Inventory", jArray));
        System.out.println(jData);
        return jData;
    }

    /*
       getDataForDate() - Функция возвращает данные за определённый год в виде строки, в формате JSON
       getDataForDate(Integer.parseInt(date)) - в качестве параметра передаётся год
     */
    public String getDataForDate(int inDate) //Выборка всех данных по соответствующей дате
    {
        String jDate;
        JSONObject jDateObject;
        JSONObject jObjectData = new JSONObject();
        JSONArray jDateArray = new JSONArray();
        try {
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("SELECT * FROM `maintable` WHERE `date`='" + inDate + "'");

            while (rs.next()) {
                String mol = rs.getString("mol");
                String name = rs.getString("name");
                String number = rs.getString("inumber");
                String cost = rs.getString("cost");
                String count = rs.getString("count");
                String status = rs.getString("status");
                int date = rs.getInt("date");

                jDateObject = new JSONObject();

                jDateObject.put("mol", mol);
                jDateObject.put("name", name);
                jDateObject.put("number", number);
                jDateObject.put("cost", cost);
                jDateObject.put("count", count);
                jDateObject.put("status", status);
                jDateObject.put("date", date);

                jDateArray.put(jDateObject);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        //jDate = jDateArray.toString().substring(1, jDateArray.toString().length() - 1);
        jDate = String.valueOf(jObjectData.put("Inventory", jDateArray));
        System.out.println(jDate);
        return jDate;
    }

     /*
        getDate() - Функция возвращает в строку все имеющиеся даты в виде JSON
     */
    public String getDate() //Выборка всех имеющихся дат
    {
        String jDate;
        JSONObject jDateObject;
        JSONObject jObjectData = new JSONObject();
        JSONArray jDateArray = new JSONArray();
        try {
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("SELECT `date` FROM `maintable` GROUP BY `date`");

            while (rs.next()) {
                String date = rs.getString("date");

                jDateObject = new JSONObject();
                jDateObject.put("date", date);
                jDateArray.put(jDateObject);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        //jDate = jDateArray.toString().substring(1, jDateArray.toString().length() - 1);
        jDate = String.valueOf(jObjectData.put("Inventory", jDateArray));
        System.out.println(jDate);
        return jDate;
    }

    public String getDataForMOL(String mol) //Выборка всех данных по МОЛ
    {
        String jData;
        JSONObject jDataObject;
        JSONObject jObjectData = new JSONObject();
        JSONArray jDataArray = new JSONArray();
        try {
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("SELECT * FROM `maintable` WHERE `mol`='"+mol+"'");

            while (rs.next()) {
                String name = rs.getString("name");
                String number = rs.getString("inumber");
                String cost = rs.getString("cost");
                String count = rs.getString("count");
                String status = rs.getString("status");
                int date = rs.getInt("date");

                jDataObject = new JSONObject();

                jDataObject.put("name", name);
                jDataObject.put("number", number);
                jDataObject.put("cost", cost);
                jDataObject.put("count", count);
                jDataObject.put("status", status);
                jDataObject.put("date", date);

                jDataArray.put(jDataObject);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        //jDate = jDateArray.toString().substring(1, jDateArray.toString().length() - 1);
        jData = String.valueOf(jObjectData.put("Inventory", jDataArray));
        System.out.println(jData);
        return jData;
    }


    public String getMOL() //Выборка всех имеющихся дат
    {
        String jMOL;
        JSONObject jMOLObject;
        JSONObject jObjectData = new JSONObject();
        JSONArray jMOLArray = new JSONArray();
        try {
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("SELECT `mol` FROM `maintable` GROUP BY `mol`");

            while (rs.next()) {
                String mol = rs.getString("mol");

                jMOLObject = new JSONObject();
                jMOLObject.put("mol", mol);
                jMOLArray.put(jMOLObject);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        //jDate = jDateArray.toString().substring(1, jDateArray.toString().length() - 1);
        jMOL = String.valueOf(jObjectData.put("Inventory", jMOLArray));
        System.out.println(jMOL);
        return jMOL;
    }

    //нереализованные функции
    public String getCrypt(String keyWord) throws Exception {
        Cipher cryptCipher = Cipher.getInstance("AES");

        SecretKeySpec key = new SecretKeySpec("44EncryptedPhrase".getBytes(), "AES");

        cryptCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] crypthedPasswordBytes = cryptCipher.doFinal(keyWord.getBytes());
        System.out.print("Phrase encrypt: ");
        for (int i = 0; i < crypthedPasswordBytes.length; i++) {
            System.out.print(crypthedPasswordBytes[i]);
        }

        System.out.println(crypthedPasswordBytes);

        return crypthedPasswordBytes.toString();
    }
    public boolean getEncrypt(byte [] crypthedPasswordBytes) throws Exception {
        SecretKeySpec key = new SecretKeySpec("44EncryptedPhrase".getBytes(), "AES");

        Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedPassword = decryptCipher.doFinal(crypthedPasswordBytes);
        System.out.print("Phrase decrypt: ");
        for (int i = 0; i < decryptedPassword.length; i++) {
            System.out.print((char) decryptedPassword[i]);
        }

        String checkPhrase = decryptedPassword.toString();

        if (checkPhrase.equals("takeIt")) {
            return true;
        } else {
            return false;
        }
    }
}