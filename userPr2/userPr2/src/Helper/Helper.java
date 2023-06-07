package Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {


        public static int pozition(String eksen, Dimension size){
            int center;
            switch (eksen){
                case "x":
                    center=(Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                    break;
                case "y":
                    center=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                    break;
                default:
                    center=0;
            }
            return center;
        }


        public static void showMessage(String str){
            String mesaj,title;
            switch (str){
                case "eksik":
                    mesaj="Lütfen tüm alanları doldurunuz!";
                    title="Hata!";
                    break;
                case "wrong":
                    mesaj="Hatalı Kullanıcı ve Parola Girdiniz ! ";
                    title="Tekrar Deneyiniz.";
                    break;
                case "eksiksiz":
                    mesaj="İşlem Başarılı";
                    title="Sonuç";
                    break;
                case "error":
                    mesaj="Bir hata oluştu!";
                    title="HATA!";
                default:
                    mesaj=str;
                    title="Mesaj";

            }
            JOptionPane.showMessageDialog(null,mesaj,title,JOptionPane.INFORMATION_MESSAGE);
        }

        public static boolean confirm(String str){
            String msj;

            switch (str){
                case "eminmisin":
                    msj="Bu işlemi gerçekleştirmek istediğine emin misin?";
                    break;
                default:
                    msj=str;

            }

            return JOptionPane.showConfirmDialog(null,msj,"Son Kararın mı?",JOptionPane.YES_NO_OPTION)==0;

        }

}
