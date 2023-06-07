package View;

import Helper.Helper;
import Model.Operator;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUII extends JFrame{
    private JPanel wrapper;
    private JPanel top_w;
    private JPanel bottom_w;
    private JTextField fld_username;
    private JPasswordField fld_pass;
    private JButton btn_login;

    private JLabel lbl_img;
    private JLabel lbl_userLogin;
    private JLabel lbl_userName;
    private JLabel lbl_pass;
    private JButton btn_loginnn;


    public LoginGUII(){
        setContentPane(wrapper);
        setSize(420,420);
        setLocation(Helper.pozition("x", getSize()),Helper.pozition("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("LOGİN");
        setVisible(true);



        btn_loginnn.addActionListener(e -> {


            if (fld_username.getText().isEmpty()||fld_pass.getText().isEmpty()){
                Helper.showMessage("eksik");
            }
            else {
                User u=User.fetch(fld_username.getText(),fld_pass.getText());
                if (u==null){
                    Helper.showMessage("wrong");

                }
                else {
                    Helper.showMessage("Giriş Başarılı");
                    OperatorG op=new OperatorG((Operator) u);
                    dispose();

                }


            }

        });
    }
}
