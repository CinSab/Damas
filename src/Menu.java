
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Menu extends JFrame {

    public Menu() throws IOException {
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        Container cp = getContentPane();
        JPanel menu = new JPanel();
        GridLayout gl = new GridLayout(4, 1, 5, 5);
        JPanel jugar = new JPanel();
        JPanel cargarpartida = new JPanel();
        JPanel verreglas = new JPanel();
        JPanel salir = new JPanel();
        jugar.setLayout(new FlowLayout());
        cargarpartida.setLayout(new FlowLayout());
        verreglas.setLayout(new FlowLayout());
        salir.setLayout(new FlowLayout());
        JButton jugar2 = new JButton("jugar");
        jugar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaDamas ventana = null;
                try {
                    ventana = new VentanaDamas();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                TableroPanel tablero = null;
                try {
                    tablero = new TableroPanel();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                ventana.add(tablero);
                ventana.setVisible(true);
            }

        });
        jugar.add(jugar2);
        cargarpartida.add(new JButton("Cargar Partida"));
        verreglas.add(new JButton("Ver reglas"));
        salir.add(new JButton("salir"));
        getContentPane().add(jugar, BorderLayout.CENTER);
        getContentPane().add(cargarpartida, BorderLayout.CENTER);
        getContentPane().add(verreglas, BorderLayout.CENTER);
        getContentPane().add(salir, BorderLayout.CENTER);

        menu.setLayout(gl);
        menu.add(jugar);
        menu.add(cargarpartida);
        menu.add(verreglas);
        menu.add(salir);
        setResizable(false);
        getContentPane().add(menu, BorderLayout.CENTER);
        cp.add(menu);
    }

    public void opcion(int boton) {
        System.out.println(boton);
    }

    public void pintarmenu(Graphics g) {

    }
}
