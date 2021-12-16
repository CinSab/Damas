import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class Menu extends JFrame {
    public JButton jugar2;
    public JButton cargarpartida1;
    public Menu() throws IOException {
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setBackground(Color.BLACK);
        Container cp = getContentPane();
        JPanel menu = new JPanel();
        GridLayout gl = new GridLayout(4, 1, 0, 0);

        JPanel jugar = new JPanel();
        jugar.setBackground(Color.PINK);

        JPanel cargarpartida = new JPanel();
        cargarpartida.setBackground(Color.PINK);

        JPanel verreglas = new JPanel();
        verreglas.setBackground(Color.PINK);

        JPanel salir = new JPanel();
        salir.setBackground(Color.PINK);

        jugar.setLayout(new FlowLayout());
        cargarpartida.setLayout(new FlowLayout());
        verreglas.setLayout(new FlowLayout());
        salir.setLayout(new FlowLayout());

        jugar2 = new JButton("jugar");
        cargarpartida1 = new JButton("Cargar Partida");
        JButton reglas = new JButton("Ver reglas");
        reglas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"  Objetivo del juego\n" +
                        "El juego de las damas consta de 24 peones divididos en 12 blancos y 12 negros y un tablero de 64 casillas (8×8) coloreadas alternativamente blancas y negras.\n" +
                        "La finalidad del juego es la captura o bloqueo de todas las piezas contrarias, de forma que no les sea posible realizar movimiento.\n" +
                        "              Cómo jugar a las damas\n" +
                        "Cada jugador controla las piezas de un color situadas al comienzo a cada lado del tablero. Empieza el juego las blancas.los movimientos se hacen alternativamente,\n " +
                        "uno por jugador, en diagonal, una sola casilla y en sentido de avance, o sea, hacia el campo del oponente.Si un jugador\n " +
                        "consigue llevar una de sus fichas al lado contrario del tablero cambiará dicho peón por una dama o reina (dos fichas del mismo\n" +
                        " color una encima de otra).La dama o reina se mueve también en diagonal, pero puede hacerlo hacia delante y hacia atrás. Según las opciones de mesa puede avanzar\n" +
                        "una casilla como el peón o recorrer cualquier número de casillas mientras estén libres. Nunca podrá saltar por encima de sus propias piezas o dos piezas contiguas.\n" +
                        "               Capturar fichas del contrario\n" +
                        "Una pieza puede capturar otra si puede saltar por encima de ella siempre en dirección de ataque y en diagonal y caer en la casilla inmediatamente detrás de aquella.\n" +
                        "Además, las damas pueden capturar en cualquier dirección. Si pueden mover más de una casilla , pueden capturar cualquier ficha que esté en la misma diagonal siempre\n " +
                        "y cuando se cumplan las reglas anteriores. La captura es obligatoria. Si una o más piezas están en situación de realizar capturas, será obligatorio realizar tal captura,\n" +
                        " no pudiendo optar por mover otra pieza.Una vez realizada una captura, tanto un peón como una dama deben volver a capturar si es posible según las reglas anteriores y \n" +
                        "en el mismo turno del jugador, y así sucesivamente.\n" +
                        "Final de la partida\n" +
                        "Finaliza la partida cuando un jugador abandona, se queda sin fichas o estas no tienen posibilidad de movimiento (bloqueo o ahogada).");
            }
        });
        JButton salida = new JButton("salir");
        salida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
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
}