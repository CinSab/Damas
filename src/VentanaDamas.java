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
        GridLayout gl = new GridLayout(2,1,5,5);
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

    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.opengl", "true");
        VentanaDamas ventana = new VentanaDamas();
        TableroPanel tablero = new TableroPanel();
        ventana.add(tablero);
        ventana.setVisible(true);
    }
}
