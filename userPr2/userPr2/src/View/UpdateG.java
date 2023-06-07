package View;

import Helper.Helper;
import Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateG extends JFrame {

    private Patika patika;
    private JPanel wrapper;
    private JTextField fld_update;
    private JButton btn_update;
    private JLabel lbl_update;


    public UpdateG(Patika patika){
        this.patika=patika;
        add(wrapper);
        setSize(500,250);
        setLocation(Helper.pozition("x",getSize()),Helper.pozition("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Güncelle");
        setVisible(true);

        fld_update.setText(patika.getPatika_name());


        btn_update.addActionListener(e -> {

            if (fld_update.getText().isEmpty()) {
                Helper.showMessage("eksik");
            } else {

                if (Patika.update(patika.getPatika_id(), fld_update.getText())) {
                    Helper.showMessage("eksiksiz");

                }
                dispose();

            }

        });
    }
}
