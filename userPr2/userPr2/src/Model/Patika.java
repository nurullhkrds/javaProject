package Model;

import Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int patika_id;
    private String patika_name;


    public Patika(){


    }

    public Patika(int patika_id, String patika_name) {
        this.patika_id = patika_id;
        this.patika_name = patika_name;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getPatika_name() {
        return patika_name;
    }

    public void setPatika_name(String patika_name) {
        this.patika_name = patika_name;
    }


    public static ArrayList<Patika> getList(){
        ArrayList<Patika>lessonList=new ArrayList<>();
        String query="select *from patika";
        Patika obj;
        try {
            Statement st= DBConnector.getDBConnet().createStatement();
            ResultSet rs=st.executeQuery(query);
            while ( rs.next()){
                obj=new Patika();
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setPatika_name(rs.getString("patika_name"));
                lessonList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonList;
    }


    public static boolean add(int id,String name){
        String query="INSERT INTO patika (patika_id,patika_name) VALUES (?,?)";
        ArrayList<Lesson> lessonList=new ArrayList<>();
        for (Lesson obj:lessonList){
            if (id==obj.getLesson_id()){
                Lesson.delete(id);
            }
        }

        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setInt(1,id);
            pr.setString(2,name);
            return pr.executeUpdate() !=-1;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return true;
    }


    public static boolean update(int id,String name){

        String query="UPDATE patika SET patika_name=? WHERE patika_id=?";

        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);
            return pr.executeUpdate() !=-1;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return true;
    }

    public static Patika fetch(int id){
        Patika obj=null;
        String query="SELECT * FROM patika WHERE patika_id=?";
        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                obj=new Patika();
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setPatika_name(rs.getString("patika_name"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id){
        String query="DELETE FROM patika WHERE patika_id=?";

        ArrayList<Lesson> lessonList=Lesson.getList();
        for (Lesson obj:lessonList){
            if (obj.getPatika().getPatika_id()==id){
                Lesson.delete(obj.getLesson_id());
            }
        }

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
