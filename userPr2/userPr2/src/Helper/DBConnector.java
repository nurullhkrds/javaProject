package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private Connection connect;


    public Connection connectDB(){
        try{
            Class.forName("org.postgresql.Driver");
            this.connect= DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
            System.out.println("bağlantı başarılı");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return this.connect;
    }

    public static Connection getDBConnet(){
        DBConnector db=new DBConnector();
        return db.connectDB();
    }



}

