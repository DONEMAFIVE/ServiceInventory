import db.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import parsing.Config;
import parsing.Parser;
import servlets.*;

public class Run {
    public static void main(String[] args) throws Exception {

        DBService dbService = new DBService();
        Parser parser = new Parser();
        Config cfg = new Config();

        //System.out.println("Размер: "+new Parser().analyze().length);

        try {
            cfg.readconfig(); //Читаем конфиг
            if (cfg.getDbParsing().equals("YES") || cfg.getDbParsing().equals("yes")) //Если значение параметра в конфиге равно 'YES', то...
            {
                System.out.println("Parsing...");
                dbService.addParsedDataToDB(new Parser().analyze()); //Добавляем данные в БД
                parser.moveFiles(); //Перемещаем файлы в новую папку "archive"
                //todo: ТУТА НАДА МЕНЯТЬ ПАРАМЕТАР КОНФЕГА НА 'NO'
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        AddValuesServlet AddValuesServlet = new AddValuesServlet();
        GiveMeDataServlet dataServlet = new GiveMeDataServlet();
        GiveMeDate dateServlet = new GiveMeDate();
        GiveMeDataForDateServlet dataForDate = new GiveMeDataForDateServlet();
        GiveMeDataForMOL dataForMOL = new GiveMeDataForMOL();
        GiveMeMOL dataMOL = new GiveMeMOL();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(dataServlet), "/getdata");
        context.addServlet(new ServletHolder(dateServlet), "/getdate");
        context.addServlet(new ServletHolder(AddValuesServlet), "/add");
        context.addServlet(new ServletHolder(dataForDate), "/datafordate");
        context.addServlet(new ServletHolder(dataForMOL), "/dataformol");
        context.addServlet(new ServletHolder(dataMOL), "/getmol");

        Server server = new Server(2298);
        server.setHandler(context);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
