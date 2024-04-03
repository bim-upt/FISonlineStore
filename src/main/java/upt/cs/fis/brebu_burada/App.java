package upt.cs.fis.brebu_burada;
import javax.swing.*;
import java.awt.Font;


class GUI{
    public GUI(){
        JFrame f;  
        f=new JFrame();
        JLabel l = new JLabel("Hello, World!");
        l.setBounds(500,0,1000,1000);
        l.setFont(new Font("Serif", Font.PLAIN, 100));
        f.add(l);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setLayout(null); 
        f.setVisible(true);
    };
}

public class App 
{
    public static void main( String[] args )
    {
        new GUI();
    }
}
