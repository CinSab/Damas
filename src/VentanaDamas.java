import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VentanaDamas extends JPanel implements MouseListener {
    public static int width = 720;
    public static int height = 720;
    public static final int tamanoDelcuadrado = width / 8;
    public static final int cantDeCuadradosPorfila = width / tamanoDelcuadrado;
    public static int[][] tablero = new int[8][8];
    public static int[][] piezas = new int[8][8];
    public static final int cuadradoVacio = 0, PEONROJO = 1, DAMAROJA = 2, PEONBLANCO = 3, DAMABLANCA = 4;
    public boolean siguesuturno = true;
    public int jugadorActual = PEONROJO;
    public boolean enpartida = false;
    public static int[][] jugadasDisponibles = new int[8][8];
    public int filaGuardada;
    public int columGuardada;
    public boolean salto = false;
    static BufferedImage imagenDelacorona = null;
    public boolean cargarpartida = false;
    public static JFrame frame;

    public VentanaDamas(boolean cargarpartida) {
        this.cargarpartida = cargarpartida;
        try {
            imagenDelacorona = ImageIO.read(new File("images/crown.png"));
        } catch (IOException o) {
            o.printStackTrace();
        }
        ventana(width, height, this);
        if (cargarpartida) {
            GuardarCargar.cargarPartida();
            piezas = GuardarCargar.matriz;
            siguesuturno = GuardarCargar.sigueturno;
        } else {
            iniciarTablero();
        }
        repaint();
    }

    public boolean perder() {
        return ChequeoDeperder(0, 0, 0, 0);
    }

    public boolean ChequeoDeperder(int col, int fila, int red, int white) { //recursive practice
        if (piezas[col][fila] == PEONROJO || piezas[col][fila] == DAMAROJA)
            red += 1;
        if (piezas[col][fila] == PEONBLANCO || piezas[col][fila] == DAMABLANCA)
            white += 1;
        if (col == cantDeCuadradosPorfila - 1 && fila == cantDeCuadradosPorfila - 1) {
            return (red == 0 || white == 0);
        }
        if (col == cantDeCuadradosPorfila - 1) {
            fila += 1;
            col = -1;
        }
        return ChequeoDeperder(col + 1, fila, red, white);
    }
    public static class AbrirMenu implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            Main.menu.setVisible(true);
        }
    }

    public void ventana(int width, int height, VentanaDamas game) {
        frame = new JFrame();
        frame.setSize(width, height);
        frame.setIconImage(imagenDelacorona);
        frame.setBackground(Color.cyan);
        frame.setLocationRelativeTo(null);
        frame.pack();
        JMenuBar barra;
        JMenu menu;
        JMenuItem guardar, volverAlMenu;
        setVisible(true);
        barra = new JMenuBar();
        frame.setJMenuBar(barra);
        menu = new JMenu("Opciones");
        barra.add(menu);
        guardar = new JMenuItem("Guardar partida");
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarCargar.guardarPartida(piezas, siguesuturno);
            }
        });
        menu.add(guardar);
        volverAlMenu = new JMenuItem("Volver Al Menu");
        volverAlMenu.addActionListener(new AbrirMenu());
        menu.add(volverAlMenu);
        Insets insets = frame.getInsets();
        int frameLeftBorder = insets.left;
        int frameRightBorder = insets.right;
        int frameTopBorder = insets.top;
        int frameBottomBorder = insets.bottom;
        frame.setPreferredSize(new Dimension(width + frameLeftBorder + frameRightBorder, height + frameBottomBorder + frameTopBorder));
        frame.setMaximumSize(new Dimension(width + frameLeftBorder + frameRightBorder, height + frameBottomBorder + frameTopBorder));
        frame.setMinimumSize(new Dimension(width + frameLeftBorder + frameRightBorder, height + frameBottomBorder + frameTopBorder));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(this);
        frame.requestFocus();
        frame.setVisible(true);
        frame.add(game);

    }

    public void iniciarTablero() {
        for (int col = 0; col < (cantDeCuadradosPorfila); col += 2) {
            piezas[col][5] = PEONROJO;
            piezas[col][7] = PEONROJO;
        }
        for (int col = 1; col < (cantDeCuadradosPorfila); col += 2)
            piezas[col][6] = PEONROJO;
        for (int col = 1; col < (cantDeCuadradosPorfila); col += 2) {
            piezas[col][0] = PEONBLANCO;
            piezas[col][2] = PEONBLANCO;
        }
        for (int col = 0; col < (cantDeCuadradosPorfila); col += 2)
            piezas[col][1] = PEONBLANCO;
    }

    public static void crearPieza(int colum, int fila, Graphics g, Color color) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(color);
        g.fillOval((colum * tamanoDelcuadrado) + 2, (fila * tamanoDelcuadrado) + 2, tamanoDelcuadrado - 4, tamanoDelcuadrado - 4);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        for (int fila = 0; fila < cantDeCuadradosPorfila; fila++) {
            for (int colum = 0; colum < cantDeCuadradosPorfila; colum++) {
                if ((fila % 2 == 0 && colum % 2 == 0) || (fila % 2 != 0 && colum % 2 != 0)) {
                    tablero[colum][fila] = 0;
                    g.setColor(Color.gray);
                    g.fillRect(colum * tamanoDelcuadrado, fila * tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                } else {
                    tablero[colum][fila] = 1;
                    g.setColor(Color.darkGray);
                    g.fillRect(colum * tamanoDelcuadrado, fila * tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                if (chequearPieza(colum, fila)) {
                    g.setColor(Color.darkGray.darker());
                    g.fillRect(colum * tamanoDelcuadrado, fila * tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                if (jugadasDisponibles[colum][fila] == 1) {
                    g.setColor(Color.CYAN.darker());
                    g.fillRect(colum * tamanoDelcuadrado, fila * tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                if (piezas[colum][fila] == PEONBLANCO)
                    crearPieza(colum, fila, g, Color.white);
                else if (piezas[colum][fila] == DAMABLANCA) {
                    crearPieza(colum, fila, g, Color.white);
                    g.drawImage(imagenDelacorona, (colum * tamanoDelcuadrado) + 6, (fila * tamanoDelcuadrado) + 6, tamanoDelcuadrado - 12, tamanoDelcuadrado - 12, null);
                } else if (piezas[colum][fila] == PEONROJO)
                    crearPieza(colum, fila, g, Color.red);
                else if (piezas[colum][fila] == DAMAROJA) {
                    crearPieza(colum, fila, g, Color.red);
                    g.drawImage(imagenDelacorona, (colum * tamanoDelcuadrado) + 6, (fila * tamanoDelcuadrado) + 6, tamanoDelcuadrado - 12, tamanoDelcuadrado - 12, null);
                } else {
                    piezas[colum][fila] = cuadradoVacio; // modificacion reciente este else no va en realidad
                }
            }
        }
        if (perder())
            mensajeAlperder(g);
    }

    public void mensajeAlperder(Graphics g) {
        String msg = "perdiste";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, width / 2);
    }

    public void reinciarJugada() {
        columGuardada = 0;
        filaGuardada = 0;
        enpartida = false;
        salto = false;
        for (int row = 0; row < cantDeCuadradosPorfila; row++) {
            for (int col = 0; col < cantDeCuadradosPorfila; col++) {
                jugadasDisponibles[col][row] = 0;
            }
        }
        repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent evt) {
        int colum = (evt.getX() - 8) / tamanoDelcuadrado;
        int fila = (evt.getY() - 30) / tamanoDelcuadrado;
        if (!enpartida && piezas[colum][fila] != 0 || enpartida && chequearPieza(colum, fila)) {
            reinciarJugada();
            columGuardada = colum;
            filaGuardada = fila;
            getJugadasDisponibles(columGuardada, filaGuardada);
        } else if (enpartida && jugadasDisponibles[colum][fila] == 1) {
            hacerMovimiento(colum, fila, columGuardada, filaGuardada);
        } else if (enpartida && jugadasDisponibles[colum][fila] == 0) {
            reinciarJugada();
        }
    }


    public void cambiarTurno() {
        if (jugadorActual == PEONROJO)
            jugadorActual = PEONBLANCO;
        else jugadorActual = PEONROJO;
    }

    public void hacerMovimiento(int colum, int fila, int columGuardada, int filaGuaradada) {
        int x = piezas[columGuardada][filaGuaradada];
        piezas[colum][fila] = x;
        piezas[columGuardada][filaGuaradada] = cuadradoVacio;
        verificarDama(colum, fila);
        if (salto) {
            quitarPieza(colum, fila, columGuardada, filaGuaradada);
            siguesuturno = false;
        }
        reinciarJugada();
        if (siguesuturno) {
            reinciarJugada();
            cambiarTurno();
        }
        siguesuturno = true;
    }

    public void verificarDama(int colum, int fila) {
        if (piezas[colum][fila] == PEONROJO && fila == 0)
            piezas[colum][fila] = DAMAROJA;
        else if (piezas[colum][fila] == PEONBLANCO && fila == cantDeCuadradosPorfila - 1)
            piezas[colum][fila] = DAMABLANCA;
    }

    public void quitarPieza(int colum, int fila, int columGuardada, int filaGuardada) {
        int piezaFila = -1;
        int piezaColum = -1;
        if (colum > columGuardada && fila > filaGuardada) {
            piezaFila = fila - 1;
            piezaColum = colum - 1;
        }
        if (colum > columGuardada && fila < filaGuardada) {
            piezaFila = fila + 1;
            piezaColum = colum - 1;
        }
        if (colum < columGuardada && fila > filaGuardada) {
            piezaFila = fila - 1;
            piezaColum = colum + 1;
        }
        if (colum < columGuardada && fila < filaGuardada) {
            piezaFila = fila + 1;
            piezaColum = colum + 1;
        }
        piezas[piezaColum][piezaFila] = cuadradoVacio;
        repaint();
    }

    public void getJugadasDisponibles(int colum, int fila) {
        enpartida = true;
        if ((chequearPieza(colum, fila))) {
            if (piezas[colum][fila] == PEONROJO) {
                irHaciaArriba(colum, fila);
                repaint();
            }
            if (piezas[colum][fila] == PEONBLANCO) {
                irHaciaAbajo(colum, fila);
                repaint();
            }
            if (piezas[colum][fila] == DAMAROJA || piezas[colum][fila] == DAMABLANCA) {
                irHaciaArriba(colum, fila);
                irHaciaAbajo(colum, fila);
                repaint();
            }
            repaint();
        }
        repaint();
    }

    public void irHaciaArriba(int colum, int fila) {
        int filaDearriba = fila - 1;
        if (colum == 0 && fila != 0) {
            for (int i = colum; i < colum + 2; i++) {
                if (piezas[colum][fila] != 0 && piezas[i][filaDearriba] != 0) {
                    if (puedeSaltar(colum, fila, i, filaDearriba)) {
                        int saltarColum = getPosiciondeSalto(colum, fila, i, filaDearriba)[0];
                        int saltarFila = getPosiciondeSalto(colum, fila, i, filaDearriba)[1];
                        jugadasDisponibles[saltarColum][saltarFila] = 1;
                    }
                } else if (tablero[i][filaDearriba] == 1 && piezas[i][filaDearriba] == 0)
                    jugadasDisponibles[i][filaDearriba] = 1;
            }
        } else if (colum == (cantDeCuadradosPorfila - 1) && fila != 0) {
            if (piezas[colum][fila] != 0 && piezas[colum - 1][filaDearriba] != 0) {
                if (puedeSaltar(colum, fila, colum - 1, filaDearriba)) {
                    int saltarColum = getPosiciondeSalto(colum, fila, colum - 1, filaDearriba)[0];
                    int saltarFila = getPosiciondeSalto(colum, fila, colum - 1, filaDearriba)[1];
                    jugadasDisponibles[saltarColum][saltarFila] = 1;
                }
            } else if (tablero[colum - 1][filaDearriba] == 1 && piezas[colum - 1][filaDearriba] == 0)
                jugadasDisponibles[colum - 1][filaDearriba] = 1;
        } else if (colum != cantDeCuadradosPorfila - 1 && colum != 0 && fila != 0) {
            for (int i = colum - 1; i <= colum + 1; i++) {
                if (piezas[colum][fila] != 0 && piezas[i][filaDearriba] != 0) {
                    if (puedeSaltar(colum, fila, i, filaDearriba)) {
                        int saltarColum = getPosiciondeSalto(colum, fila, i, filaDearriba)[0];
                        int saltarFila = getPosiciondeSalto(colum, fila, i, filaDearriba)[1];
                        jugadasDisponibles[saltarColum][saltarFila] = 1;
                    }
                } else if (tablero[i][filaDearriba] == 1 && piezas[i][filaDearriba] == 0)
                    jugadasDisponibles[i][filaDearriba] = 1;
            }
        }
    }

    public void irHaciaAbajo(int colum, int fila) {
        int filadeAbajo = fila + 1;
        if (colum == 0 && fila != cantDeCuadradosPorfila - 1) {
            if (piezas[colum][fila] != 0 && piezas[colum + 1][filadeAbajo] != 0) {
                if (puedeSaltar(colum, fila, colum + 1, filadeAbajo)) {
                    int saltarColum = getPosiciondeSalto(colum, fila, colum + 1, filadeAbajo)[0];
                    int saltarFila = getPosiciondeSalto(colum, fila, colum + 1, filadeAbajo)[1];
                    jugadasDisponibles[saltarColum][saltarFila] = 1;
                }
            } else if (tablero[colum + 1][filadeAbajo] == 1 && piezas[colum + 1][filadeAbajo] == 0)
                jugadasDisponibles[colum + 1][filadeAbajo] = 1;
        } else if (colum == cantDeCuadradosPorfila - 1 && fila != cantDeCuadradosPorfila - 1) {
            if (piezas[colum][fila] != 0 && piezas[colum - 1][filadeAbajo] != 0) {
                if (puedeSaltar(colum, fila, colum - 1, filadeAbajo)) {
                    int saltarColum = getPosiciondeSalto(colum, fila, colum - 1, filadeAbajo)[0];
                    int saltarFila = getPosiciondeSalto(colum, fila, colum - 1, filadeAbajo)[1];
                    jugadasDisponibles[saltarColum][saltarFila] = 1;
                }
            } else if (tablero[colum - 1][filadeAbajo] == 1 && piezas[colum - 1][filadeAbajo] == 0)
                jugadasDisponibles[colum - 1][filadeAbajo] = 1;
        } else if (colum != cantDeCuadradosPorfila - 1 && colum != 0 && fila != cantDeCuadradosPorfila - 1) {
            for (int i = colum - 1; i <= colum + 1; i++) {
                if (piezas[colum][fila] != 0 && piezas[i][filadeAbajo] != 0) {
                    if (puedeSaltar(colum, fila, i, filadeAbajo)) {
                        int saltarColum = getPosiciondeSalto(colum, fila, i, filadeAbajo)[0];
                        int saltarFila = getPosiciondeSalto(colum, fila, i, filadeAbajo)[1];
                        jugadasDisponibles[saltarColum][saltarFila] = 1;
                    }
                } else if (tablero[i][filadeAbajo] == 1 && piezas[i][filadeAbajo] == 0)
                    jugadasDisponibles[i][filadeAbajo] = 1;
            }
        }
    }

    public boolean chequearPieza(int col, int row) {
        if (jugadorActual == PEONROJO && (piezas[col][row] == PEONROJO || piezas[col][row] == DAMAROJA))
            return true;
        if (jugadorActual == PEONBLANCO && (piezas[col][row] == PEONBLANCO || piezas[col][row] == DAMABLANCA))
            return true;
        else
            return false;
    }

    public boolean posicionDisponible(int colum, int fila) {
        return !(fila < 0 || fila >= cantDeCuadradosPorfila || colum < 0 || colum >= cantDeCuadradosPorfila);

    }

    public boolean puedeSaltar(int colum, int fila, int columDelaPiezaEnemiga, int filaDelaPiezaEnemiga) {
        if (((piezas[colum][fila] == PEONBLANCO || piezas[colum][fila] == DAMABLANCA) && (piezas[columDelaPiezaEnemiga][filaDelaPiezaEnemiga] == PEONROJO || piezas[columDelaPiezaEnemiga][filaDelaPiezaEnemiga] == DAMAROJA)) || (piezas[colum][fila] == PEONROJO || piezas[colum][fila] == DAMAROJA) && (piezas[columDelaPiezaEnemiga][filaDelaPiezaEnemiga] == PEONBLANCO || piezas[columDelaPiezaEnemiga][filaDelaPiezaEnemiga] == DAMABLANCA)) {

            if (columDelaPiezaEnemiga == 0 || columDelaPiezaEnemiga == cantDeCuadradosPorfila - 1 || filaDelaPiezaEnemiga == 0 || filaDelaPiezaEnemiga == cantDeCuadradosPorfila - 1) {
                return false;
            }
            int[] posiciondeSalto = getPosiciondeSalto(colum, fila, columDelaPiezaEnemiga, filaDelaPiezaEnemiga);
            if (!posicionDisponible(posiciondeSalto[0], posiciondeSalto[1])) {
                return false;
            }
            if (piezas[posiciondeSalto[0]][posiciondeSalto[1]] == 0) {
                salto = true;
                return true;
            }
        }
        return false;
    }

    public int[] getPosiciondeSalto(int colum, int fila, int columDelaPiezaEnemiga, int filaDelaPiezaEnemiga) {
        if (colum > columDelaPiezaEnemiga && fila > filaDelaPiezaEnemiga && piezas[colum - 2][fila - 2] == 0)
            return new int[]{colum - 2, fila - 2};
        else if (colum > columDelaPiezaEnemiga && fila < filaDelaPiezaEnemiga && piezas[colum - 2][fila + 2] == 0)
            return new int[]{colum - 2, fila + 2};
        else if (colum < columDelaPiezaEnemiga && fila > filaDelaPiezaEnemiga && piezas[colum + 2][fila - 2] == 0)
            return new int[]{colum + 2, fila - 2};
        else
            return new int[]{colum + 2, fila + 2};
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}