
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JFrame {

    public Menu() throws IOException {
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setBackground(Color.BLACK);

        Container cp = getContentPane();
        JPanel menu = new JPanel();
        GridLayout gl = new GridLayout(4, 1, 0, 0);

        JPanel jugar = new JPanel();
        jugar.setBackground(Color.DARK_GRAY);
        JPanel cargarpartida = new JPanel();
        cargarpartida.setBackground(Color.gray);
        JPanel verreglas = new JPanel();
        verreglas.setBackground(Color.DARK_GRAY);
        JPanel salir = new JPanel();
        salir.setBackground(Color.gray);

        jugar.setLayout(new FlowLayout());
        cargarpartida.setLayout(new FlowLayout());
        verreglas.setLayout(new FlowLayout());
        salir.setLayout(new FlowLayout());
        JButton jugar2 = new JButton("jugar");
        jugar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaDamas ventana = null;
                Damas damas = new Damas();
                ventana = new VentanaDamas(damas);
            }

        });
        JButton cargarpartida1 = new JButton("Cargar Partida");
        JButton reglas=new JButton("Ver reglas");
        JButton salida = new JButton("salir");
        jugar.add(jugar2);
        cargarpartida.add(cargarpartida1);
        verreglas.add(reglas);
        salir.add(salida);

        menu.setLayout(gl);
        menu.add(jugar);
        menu.add(cargarpartida);
        menu.add(verreglas);
        menu.add(salir);
        menu.setBackground(Color.BLACK);
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
