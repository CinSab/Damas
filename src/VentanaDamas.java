import javax.swing.*;
import java.awt.*;
import java.io.IOException;
// wey
public class VentanaDamas  extends JFrame {

    public VentanaDamas() throws IOException  {
        super("Damas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        TableroPanel tablero = new TableroPanel();
        JPanel opciones = new JPanel();
        GridLayout grind = new GridLayout(2,1,5,5);
        JPanel izquierda = new JPanel();
        izquierda.setLayout( new FlowLayout());
        izquierda.add(new JButton("volver atras"));
        opciones.add(izquierda);
        getContentPane().add(izquierda,BorderLayout.NORTH);
        opciones.add(tablero);
        cp.add(opciones);
        pack();
        setResizable(false);
    }
}
