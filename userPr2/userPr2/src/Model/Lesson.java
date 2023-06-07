package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Lesson {
    private int lesson_id;
    private int userr_id;
    private int patikaa_id;
    private String name;
    private String lang;
    private Patika patika;
    private User educator;


    public Lesson(int lesson_id, int userr_id, int patikaa_id, String name, String lang) {
        this.lesson_id = lesson_id;
        this.userr_id = userr_id;
        this.patikaa_id = patikaa_id;
        this.name = name;
        this.lang = lang;
        this.patika=Patika.fetch(patikaa_id);
        this.educator=User.fetch(userr_id);
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public int getUserr_id() {
        return userr_id;
    }

    public void setUserr_id(int userr_id) {
        this.userr_id = userr_id;
    }

    public int getPatikaa_id() {
        return patikaa_id;
    }

    public void setPatikaa_id(int patikaa_id) {
        this.patikaa_id = patikaa_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Lesson> getList(){
        ArrayList<Lesson>lessonList=new ArrayList<>();
        String query="select * from lesson";
        try {

            Statement st= DBConnector.getDBConnet().createStatement();
            ResultSet rs=st.executeQuery(query);
            while ( rs.next()){
                int id=(rs.getInt("lesson_id"));
                int user_id=(rs.getInt("userr_id"));
                int patika_id=(rs.getInt("patikaa_id"));
                String name=(rs.getString("name"));
                String lang=(rs.getString("lang"));
                Lesson obj=new Lesson(id,user_id,patika_id,name,lang);
                lessonList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonList;
    }


    public static boolean add(int lesson_id,int userr_id,int patikaa_id,String name,String lang){
        String query="INSERT INTO lesson (lesson_id,userr_id,patikaa_id,name,lang) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setInt(1,lesson_id);
            pr.setInt(2,userr_id);
            pr.setInt(3,patikaa_id);
            pr.setString(4,name);
            pr.setString(5,lang);

            return pr.executeUpdate() !=-1;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return true;
    }



    public static boolean delete(int id){
        String query="DELETE FROM lesson WHERE lesson_id=?";

        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() ==1;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return true;

    }





}
