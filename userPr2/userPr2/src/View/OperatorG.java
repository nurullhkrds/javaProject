package View;

import Helper.Helper;
import Helper.Item;
import Model.Lesson;
import Model.Operator;
import Model.Patika;
import Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorG extends JFrame {


    private JPanel wrapper;
    private JTabbedPane tbd_lessons;
    private JPanel tbd_user;
    private JButton btn_out;
    private JLabel add_password;
    private JTable table_user;
    private JScrollPane scrol_pane;
    private JTextField addName;
    private JLabel adddName;
    private JTextField add_username;
    private JTextField add_pass;
    private JLabel addd_username;
    private JLabel addd_password;
    private JButton btn_add;
    private JTextField field_id;
    private JButton btn_delete;
    private JLabel lbl_id;
    private JTextField search_fld_name;
    private JTextField search_field_uname;
    private JButton search_btn;
    private JLabel lbl_name;
    private JLabel lbl_uname;
    private JTable patika_table;
    private JPanel pnl_patika;
    private JTextField jbl_patika;
    private JButton add_patika;
    private JLabel lbl_patika;
    private JTable les_tablo;
    private JTextField les_fld_name;
    private JTextField les_fld_lang;
    private JComboBox les_combo_patika;
    private JComboBox les_combo_user;
    private JButton btn_les;
    private JPanel les_panel;
    private JScrollPane scrl_les;
    private JPanel les_pnl_add;
    private JLabel les_lbl_name;
    private JLabel les_lbl_lang;
    private JLabel les_lbl_patika;
    private JLabel les_lbl_egitmen;
    private JButton delete_les;
    private JTextField sel_delete_fld;
    private final Operator operator;
    private DefaultTableModel model_tablo_user;
    private Object[] row_user_table;

    private DefaultTableModel model_tablo_patika;
    private Object[] row_patika_table;
    private int id;
    private int patika_id;
    private JPopupMenu selectRightLesson;

    private DefaultTableModel model_tablo_lesson;
    private Object[] row_lesson_table;
    private int lesson_id;
    public OperatorG(Operator operator){
            this.operator=operator;
            setContentPane(wrapper);
            setSize(1000,500);
            setLocation(Helper.pozition("x",getSize()),Helper.pozition("y",getSize()));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle("Patika.Dev");
            setVisible(true);





        //Model USER BAŞLANGICI
            model_tablo_user=new DefaultTableModel(); //Tablolar modellerle çalışır.Önce model oluşturulur.
            Object[] col_user_table={"ID" , "Ad-Soyad" ,"Kullanıcı Adı", "Şifre"}; // Modellerin içine ise nesne atılır.
            model_tablo_user.setColumnIdentifiers(col_user_table); // oluşturulan nesne modelin içine atıldı.

            row_user_table=new Object[col_user_table.length]; // satırlar oluşturuldu.



            loadTableUser(); // tabloyu çağırma(bu fonksiyonda veri tabanından verileri modele aktarıyor.)

            table_user.setModel(model_tablo_user); // modeli tablonun içine atarak görüntülemiş oluruz.
            table_user.getTableHeader().setReorderingAllowed(false); //Sütunların yer değişmesini engeller.

            // tablodaki değeri seçmek için bu fonksiyonları giriyoruz.
            table_user.getSelectionModel().addListSelectionListener(e->{
                try {
                    //ValueAt fonksiyonu bize tabloda direk istediğimiz kolon ve stırı verecek.Burada sadece id alınır.
                    String selectedId= table_user.getValueAt(table_user.getSelectedRow(),0).toString();
                    field_id.setText(selectedId);
                }
                catch (Exception exception){
                    exception.getMessage();
                }
            });

            table_user.getModel().addTableModelListener(e->{
                if (e.getType()== TableModelEvent.UPDATE){
                    int iid= Integer.parseInt(table_user.getValueAt(table_user.getSelectedRow(),0).toString());
                    String name=table_user.getValueAt(table_user.getSelectedRow(),1).toString();
                    String username=table_user.getValueAt(table_user.getSelectedRow(),2).toString();
                    String password=table_user.getValueAt(table_user.getSelectedRow(),3).toString();
                    if (User.update(iid,name,username,password)){
                        Helper.showMessage("eksiksiz");
                    }
                    loadTableUser();
                    loadTableLesson();
                    comboLoadPatika();
                    comboLoadUser();


                }
            });
            //## userTable model işlemleri burada biter.



        selectRightLesson=new JPopupMenu();
        JMenuItem updateMenu=new JMenuItem("Güncelle");
        JMenuItem deleteMenu=new JMenuItem("Sil");
        selectRightLesson.add(updateMenu);
        selectRightLesson.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int updatePatikaId= Integer.parseInt(patika_table.getValueAt(patika_table.getSelectedRow(),0).toString());
            UpdateG updadePatika=new UpdateG(Patika.fetch(updatePatikaId));

            updadePatika.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadTablePatika();
                    loadTableLesson();
                    comboLoadPatika();
                    comboLoadUser();
                }
            });

        });
        deleteMenu.addActionListener(e -> {
            int updatePatikaId= Integer.parseInt(patika_table.getValueAt(patika_table.getSelectedRow(),0).toString());

            if (Helper.confirm("eminmisin")){
                if (Patika.delete(updatePatikaId)){
                    patika_id--;
                    Helper.showMessage("eksiksiz");
                    loadTablePatika();
                    comboLoadPatika();
                    comboLoadUser();
                    loadTableLesson();

                }
                else{
                        Helper.showMessage("eksik");

                }


            }

        });



        // MODEL PATİKA BAŞLANGICI
        model_tablo_patika=new DefaultTableModel(); //Tablolar modellerle çalışır.Önce model oluşturulur.
        Object[] col_patika_table={"ID" , "Ders-Adı"}; // Modellerin içine ise nesne atılır.
        model_tablo_patika.setColumnIdentifiers(col_patika_table);

        row_patika_table=new Object[col_patika_table.length]; // satırlar oluşturuldu.

        loadTablePatika();
        patika_table.setModel(model_tablo_patika);
        patika_table.setComponentPopupMenu(selectRightLesson);
        patika_table.getTableHeader().setReorderingAllowed(false);
        patika_table.getColumnModel().getColumn(0).setMaxWidth(100);


        patika_table.addMouseListener(new MouseAdapter() { //mausu dinler MausAdapter sınıfını newler.
            @Override
            public void mousePressed(MouseEvent e) {     //mausePressed metodu mause basmak anlamında method.
                Point point=e.getPoint();                  // Pointe, tıklanılan yerin x y koordinatlarını atar.
                int selectedRow=patika_table.rowAtPoint(point);  // tablodaki hangi satıra tıklanılmışsa onun id'sini değşkene atar.
                patika_table.setRowSelectionInterval(selectedRow,selectedRow); //tabloda seçilen satırı verir.

            }
        });

        // ##MODEL PATİKA BİTİŞİ



        //MODEL LESSON BAŞLANGICI

        model_tablo_lesson=new DefaultTableModel();
        Object [] col_lesson_table={"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        model_tablo_lesson.setColumnIdentifiers(col_lesson_table);

        row_lesson_table=new Object[col_lesson_table.length];

        loadTableLesson();
        les_tablo.setModel(model_tablo_lesson);
        les_tablo.getColumnModel().getColumn(0).setMaxWidth(75);
        les_tablo.getTableHeader().setReorderingAllowed(false);
        comboLoadPatika();
        comboLoadUser();


        les_tablo.getSelectionModel().addListSelectionListener(a->{
            try {
                //ValueAt fonksiyonu bize tabloda direk istediğimiz kolon ve stırı verecek.Burada sadece id alınır.
                String selectedId= les_tablo.getValueAt(les_tablo.getSelectedRow(),0).toString();
                sel_delete_fld.setText(selectedId);
            }
            catch (Exception exception){
                exception.getMessage();
            }

        });






        // ## MODEL LESSON BİTİŞİ





        btn_add.addActionListener(e -> {

            if(addName.getText().isEmpty() || add_pass.getText().isEmpty() || add_username.getText().isEmpty()){
                Helper.showMessage("eksik");
            }
            else {
                id++;
                String name=addName.getText();
                String userName=add_username.getText();
                String pass=add_pass.getText();
                boolean user_addFunctionValue=User.add(id,name,userName,pass);
                if (user_addFunctionValue){
                    Helper.showMessage("eksiksiz");

                    loadTableUser(); // tabloyu çağırma
                    comboLoadPatika();
                    comboLoadUser();
                    add_pass.setText(null);
                    add_username.setText(null);
                    addName.setText(null);

                }

            }
        });
        btn_delete.addActionListener(e -> {
            if (field_id.getText().isEmpty()){
                Helper.showMessage("eksik");
            }
            else{

                if(Helper.confirm("eminmisin")){
                    int delete_id= Integer.parseInt(field_id.getText());

                    if (User.delete(delete_id)){
                        Helper.showMessage("eksiksiz");
                        loadTableUser();
                        comboLoadPatika();
                        loadTableLesson();
                        comboLoadUser();
                        id--;
                    }
                    else {
                        Helper.showMessage("Girdiğiniz id bulunmamaktadır!");
                    }
                }


            }


        });
        search_btn.addActionListener(e -> {
            String name=search_fld_name.getText();
            String uname=search_field_uname.getText();
            String query=User.query(name,uname);
            ArrayList<User> searchList=User.getSearch(query);
            loadTableUser(searchList);

        });
        add_patika.addActionListener(e -> {

            String name=jbl_patika.getText();

            if (jbl_patika.getText().isEmpty()){
                Helper.showMessage("eksik");
            }
            else{
                if(Patika.add(id,name)){
                    loadTablePatika();
                    comboLoadPatika();
                    comboLoadUser();
                    Helper.showMessage("eksiksiz");
                    patika_id++;
                }

            }

        });
        btn_les.addActionListener(e -> {
            Item patika= (Item)les_combo_patika.getSelectedItem(); // combodaki değeri Item nesnesi olarak alırız
            Item user= (Item)les_combo_user.getSelectedItem(); // combodaki değeri Item nesnesi olarak alırız


            if (les_fld_name.getText().isEmpty()||les_fld_lang.getText().isEmpty()){
                Helper.showMessage("eksik");
            }
            else{
                //Item olarak aldıklarımız key değeri direk bize id verir.
                lesson_id++;
                if (Lesson.add(lesson_id,user.getKey(),patika.getKey(),les_fld_name.getText(),les_fld_lang.getText())){
                    Helper.showMessage("eksiksiz");
                    loadTableLesson();
                    Helper.showMessage("eksiksiz");
                    les_fld_lang.setText(null);
                    les_fld_name.setText(null);

                }
            }


        });
        delete_les.addActionListener(e -> {

            if (sel_delete_fld.getText().isEmpty()){
                Helper.showMessage("eksik");
            }
            else{
                int id= Integer.parseInt(sel_delete_fld.getText());
                if (Lesson.delete(id)){
                    loadTableLesson();
                    Helper.showMessage("eksiksiz");
                    lesson_id--;
                }
            }


        });
        btn_out.addActionListener(e -> {
            dispose();
            LoginGUII login=new LoginGUII();

        });
    }

    private void loadTableLesson() {
        DefaultTableModel clearModel = (DefaultTableModel) les_tablo.getModel();
        clearModel.setRowCount(0);
        for (Lesson obj : Lesson.getList()) {
            int i = 0;
            row_lesson_table[i++] = obj.getLesson_id();
            row_lesson_table[i++] = obj.getName();
            row_lesson_table[i++]= obj.getLang();
            row_lesson_table[i++] = obj.getPatika().getPatika_name();
            row_lesson_table[i++]=obj.getEducator().getName();
            model_tablo_lesson.addRow(row_lesson_table);
            lesson_id=obj.getLesson_id();
        }

    }


    public void loadTableUser() {

        // Tabloyu boşaltır ve daha sonra ekler.
        DefaultTableModel clearModel = (DefaultTableModel) table_user.getModel();
        clearModel.setRowCount(0);
        for (User obj : User.getList()) {
            int i = 0;
            row_user_table[i++] = obj.getId();
            row_user_table[i++] = obj.getName();
            row_user_table[i++] = obj.getUserName();
            row_user_table[i++] = obj.getPassword();
            model_tablo_user.addRow(row_user_table);
            id = obj.getId();
        }
    }

    public void loadTableUser(ArrayList<User> list){

        // Tabloyu boşaltır ve daha sonra ekler.
        DefaultTableModel clearModel= (DefaultTableModel) table_user.getModel();
        clearModel.setRowCount(0);
        for(User obj:list){
            int i=0;
            row_user_table[i++]=obj.getId();
            row_user_table[i++]=obj.getName();
            row_user_table[i++]=obj.getUserName();
            row_user_table[i++]=obj.getPassword();
            model_tablo_user.addRow(row_user_table);
            id=obj.getId();
        }

    }

    public void loadTablePatika(){
        DefaultTableModel clearModel= (DefaultTableModel) patika_table.getModel();
        clearModel.setRowCount(0);
        for (Patika obj: Patika.getList()){
            int i=0;
            row_patika_table[i++]=obj.getPatika_id();
            row_patika_table[i++]=obj.getPatika_name();
            model_tablo_patika.addRow(row_patika_table);
            patika_id=obj.getPatika_id();
        }
    }


    public void comboLoadPatika(){
        les_combo_patika.removeAllItems();
        for(Patika obj:Patika.getList()){
            les_combo_patika.addItem(new Item(obj.getPatika_id(),obj.getPatika_name()));

        }
    }
    public void comboLoadUser(){
        les_combo_user.removeAllItems();
        for(User obj:User.getList()){
            les_combo_user.addItem(new Item(obj.getId(),obj.getName()));

        }
    }




}

