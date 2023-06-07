package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {

        private int id;
        private String name;
        private String userName;
        private String password;

        public User (){

        }

        public User(int id, String name, String userName, String password) {
            this.id = id;
            this.name = name;
            this.userName = userName;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        public static ArrayList<User> getList(){
            ArrayList<User>userList=new ArrayList<>();
            String query="select *from userrr";
            User obj;
            try {
                Statement st= DBConnector.getDBConnet().createStatement();
                ResultSet rs=st.executeQuery(query);
                while ( rs.next()){
                    obj=new User();
                    obj.setId(rs.getInt("user_id"));
                    obj.setName(rs.getString("name"));
                    obj.setUserName(rs.getString("uname"));
                    obj.setPassword(rs.getString("pass"));
                    userList.add(obj);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return userList;
        }



        public static boolean add(int id,String name,String username,String password){
            String query="INSERT INTO userrr (user_id,name,uname,pass) VALUES (?,?,?,?)";
            User dublicateUser= fetch(username);
            if (dublicateUser!=null){
                Helper.showMessage("Varolan bir kullanıcı adı girdiniz!");
                return false;
            }
            try {
                PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
                pr.setInt(1,id);
                pr.setString(2,name);
                pr.setString(3,username);
                pr.setString(4,password);

                return pr.executeUpdate() !=-1;

            }catch (Exception e){
                System.out.println(e.getMessage());
            }


            return true;
        }

        public static User fetch(String userName){
            User obj=null;
            String query="SELECT * FROM userrr WHERE uname=?";
            try {
                PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
                pr.setString(1,userName);
                ResultSet rs=pr.executeQuery();
                if(rs.next()){
                    obj=new User();
                    obj.setId(rs.getInt("user_id"));
                    obj.setName(rs.getString("name"));
                    obj.setUserName(rs.getString("uname"));
                    obj.setPassword(rs.getString("pass"));
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
            return obj;
        }

    public static User fetch(int id){
        User obj=null;
        String query="SELECT * FROM userrr WHERE user_id=?";
        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                obj=new User();
                obj.setId(rs.getInt("user_id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static User fetch(String userName,String password){
        User obj=null;
        String query="SELECT * FROM userrr WHERE uname=? AND pass=?";
        try {
            PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
            pr.setString(1,userName);
            pr.setString(2,password);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                obj=new Operator();
                obj.setId(rs.getInt("user_id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }



        public static boolean delete(int id){
            String query="DELETE FROM userrr WHERE user_id=?";
            ArrayList<Lesson> lessonList=Lesson.getList();
            for (Lesson obj:lessonList){
                if (obj.getEducator().getId()==0){
                    Lesson.delete(id);
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

        public static boolean update(int id,String name,String uname,String password){

            String query="UPDATE userrr SET name=? , uname=?,pass=? WHERE user_id=?";
            User dublicateUser= fetch(uname);
            if (dublicateUser!=null && fetch(uname).getId()!=id){
                Helper.showMessage("Varolan bir kullanıcı adı girdiniz!");
                return false;
            }

            try {
                PreparedStatement pr=DBConnector.getDBConnet().prepareStatement(query);
                pr.setString(1,name);
                pr.setString(2,uname);
                pr.setString(3,password);
                pr.setInt(4,id);


                return pr.executeUpdate() !=-1;

            }catch (Exception e){
                System.out.println(e.getMessage());
            }


            return true;
        }


    public static ArrayList<User> getSearch(String query){
        ArrayList<User>userList=new ArrayList<>();
        User obj;
        try {
            Statement st= DBConnector.getDBConnet().createStatement();
            ResultSet rs=st.executeQuery(query);
            while ( rs.next()){
                obj=new User();
                obj.setId(rs.getInt("user_id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static String query(String name,String uname){
            String query="SELECT * FROM userrr WHERE uname ILIKE '%{{uname}}%' AND name ILIKE '%{{name}}%'";
            query=query.replace("{{uname}}",uname);
            query=query.replace("{{name}}",name);
            return query;

    }



}
