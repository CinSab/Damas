import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class VentanaDamas extends JPanel implements ActionListener, MouseListener {
    public static int width = 720; // Ancho de la ventana
    public static int height= 720; //Altura de la ventana
    public static final int tamanoDelcuadrado = width/8; //8 Casilleros para el tablero de damas
    public static final int cantDeCuadradosPorfila = width/ tamanoDelcuadrado;
    public static int[][] tablero = new int[cantDeCuadradosPorfila][cantDeCuadradosPorfila]; //almacena el diseño del tablero de 8x8
    public static int[][] piezas = new int[cantDeCuadradosPorfila][cantDeCuadradosPorfila]; //almacena datos de pieza en el tablero de8x8
    public static final int cuadradoVacio = 0, PEONROJO = 1, DAMAROJA = 2, PEONBLANCO = 3, DAMABLANCA = 4; //valores para gamedata
    public boolean juegoenprogreso = true;
    public int jugadorActual = PEONBLANCO;
    public boolean enpartida = false; //¿Hay un procesamiento de función de movimiento?
    public static int[][] jugadasDisponibles = new int[cantDeCuadradosPorfila][cantDeCuadradosPorfila]; //almacena juegos disponibles en un 8x8
    public int filaGuardada;
    public int columGuardada;//fila y columna almacenada
    public boolean salto = false;
    static BufferedImage imagenDelacorona = null;
    public VentanaDamas(){
        ventana(width, height, this);
        iniciarTablero();
        repaint(); // This is included in the JVM. Runs paint.
    }

    public boolean perder(){ //emboltorio para juego perdido(game over)
        return ChequeoDeperder(0, 0, 0, 0);
    }

    public boolean ChequeoDeperder(int col, int fila, int red, int white){ //recursive practice
        if(piezas[col][fila] == PEONROJO || piezas[col][fila] == DAMAROJA)
            red += 1;
        if(piezas[col][fila] == PEONBLANCO || piezas[col][fila] == DAMABLANCA)
            white += 1;
        if(col == cantDeCuadradosPorfila-1 && fila == cantDeCuadradosPorfila-1){
            if(red == 0 || white == 0)
                return true;
            else return false;
        }
        if(col == cantDeCuadradosPorfila-1){
            fila += 1;
            col = -1;
        }
        return ChequeoDeperder(col+1, fila, red, white);
    }

    public void ventana(int width, int height, VentanaDamas game){ //draw the frame and add exit functionality
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setIconImage(imagenDelacorona);
        frame.setBackground(Color.cyan);
        frame.setLocationRelativeTo(null);
        frame.pack();
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

    public void iniciarTablero(){
        //UPDATE THE STARTING POSITIONS
        for(int col=0; col < (cantDeCuadradosPorfila); col+=2){
            piezas[col][5] = PEONROJO;
            piezas[col][7] = PEONROJO;
        }
        for(int col=1; col < (cantDeCuadradosPorfila); col+=2)
            piezas[col][6] = PEONROJO;
        for(int col=1; col < (cantDeCuadradosPorfila); col+=2){
            piezas[col][0] = PEONBLANCO;
            piezas[col][2] = PEONBLANCO;
        }
        for(int col=0; col < (cantDeCuadradosPorfila); col+=2)
            piezas[col][1] = PEONBLANCO;
    }

    public static void crearPieza(int colum, int fila, Graphics g, Color color){
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(color);
        // These 2 and 4 values are arbitrary values that compensate for a smaller piece size than tileSize
        g.fillOval((colum* tamanoDelcuadrado)+2, (fila * tamanoDelcuadrado)+2, tamanoDelcuadrado -4, tamanoDelcuadrado -4);
    }

    public void paint(Graphics g){ // este metodo pinta el tablero
        //imprime el tablero y las pizas
        super.paintComponent(g);
        for(int fila = 0; fila < cantDeCuadradosPorfila; fila++){
            for(int colum = 0; colum < cantDeCuadradosPorfila; colum++){
                if((fila%2 == 0 && colum%2 == 0) || (fila%2 != 0 && colum%2 != 0)){ // This assigns the checkerboard pattern
                    tablero[colum][fila] = 0;
                    g.setColor(Color.gray);
                    g.fillRect(colum* tamanoDelcuadrado, fila* tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                else{
                    tablero[colum][fila] = 1;
                    g.setColor(Color.darkGray);
                    g.fillRect(colum* tamanoDelcuadrado, fila* tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                if(chequearPieza(colum, fila) ==  true){
                    g.setColor(Color.darkGray.darker());
                    g.fillRect(colum* tamanoDelcuadrado, fila* tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                if(jugadasDisponibles[colum][fila] == 1){
                    g.setColor(Color.CYAN.darker());
                    g.fillRect(colum* tamanoDelcuadrado, fila* tamanoDelcuadrado, tamanoDelcuadrado, tamanoDelcuadrado);
                }
                if(piezas[colum][fila] == PEONBLANCO)
                    crearPieza(colum, fila, g, Color.white);
                else if(piezas[colum][fila] == DAMABLANCA){
                    crearPieza(colum, fila, g, Color.white);
                    g.drawImage(imagenDelacorona, (colum* tamanoDelcuadrado)+6, (fila* tamanoDelcuadrado)+6, tamanoDelcuadrado -12, tamanoDelcuadrado -12, null);
                }
                else if(piezas[colum][fila] == PEONROJO)
                    crearPieza(colum, fila, g, Color.red);
                else if(piezas[colum][fila] == DAMAROJA){
                    crearPieza(colum, fila, g, Color.red);
                    g.drawImage(imagenDelacorona, (colum* tamanoDelcuadrado)+6, (fila* tamanoDelcuadrado)+6, tamanoDelcuadrado -12, tamanoDelcuadrado -12, null);
                }
            }
        }
        if(perder() == true)
            mensajeAlperder(g);
    }

    public void mensajeAlperder(Graphics g) { //Displays the game over message
        String msg = "perdiste";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, width / 2);
    }

    public void reinciarJugada(){
        columGuardada = 0;
        filaGuardada = 0;
        enpartida = false;
        salto = false;
        for(int row = 0; row < cantDeCuadradosPorfila; row++){
            for(int col = 0; col < cantDeCuadradosPorfila; col++){
                jugadasDisponibles[col][row] = 0;
            }
        }
        repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent evt) {
        int col = (evt.getX()-8) / tamanoDelcuadrado; // 8 is left frame length
        int row = (evt.getY()-30) / tamanoDelcuadrado; // 30 is top frame length
        if(enpartida == false && piezas[col][row] != 0 || enpartida == true && chequearPieza(col, row) == true){
            reinciarJugada();
            columGuardada = col;
            filaGuardada = row; // Sets the current click to instance variables to be used elsewhere
            getJugadasDisponibles(col, row);
        }
        else if(enpartida == true && jugadasDisponibles[col][row] == 1){
            hacerMovimiento(col, row, columGuardada, filaGuardada);
        }
        else if(enpartida == true && jugadasDisponibles[col][row] == 0){
            reinciarJugada();
        }
    }

    public void cambiarTurno(){
        if(jugadorActual == PEONROJO)
            jugadorActual = PEONBLANCO;
        else jugadorActual = PEONROJO;
    }

    public void hacerMovimiento(int colum, int fila, int columGuardada, int filaGuaradada){
        int x = piezas[columGuardada][filaGuaradada]; //change the piece to new tile
        piezas[colum][fila] = x;
        piezas[columGuardada][filaGuaradada] = cuadradoVacio; //change old piece location to EMPTY
        verificarDama(colum, fila);
        if(salto == true)
            quitarPieza(colum, fila, columGuardada, filaGuaradada);
        reinciarJugada();
        cambiarTurno();
    }

    public boolean esDama(int col, int fila){
        if(piezas[col][fila] == DAMAROJA || piezas[col][fila] == DAMABLANCA){
            return true;
        }
        else return false;
    }

    public int verificarPiezaEnemiga(int col, int row){
        if(piezas[col][row] == PEONROJO || piezas[col][row] == DAMAROJA)
            return PEONBLANCO;
        else
            return PEONROJO;
    }

    public void verificarSaltoExtras(int colum, int fila){
        int piezaEnemiga = verificarPiezaEnemiga(colum, fila);
        int piezaEnemigaDama = verificarPiezaEnemiga(colum, fila) + 1;
        if(piezas[colum-1][fila-1] == piezaEnemiga || piezas[colum-1][fila-1] == piezaEnemigaDama){
            jugadasDisponibles[colum-1][fila-1] = 1;
        }
        else if(piezas[colum+1][fila-1] == piezaEnemiga || piezas[colum+1][fila-1] == piezaEnemigaDama){
            jugadasDisponibles[colum+1][fila-1] = 1;
        }
        else if(piezas[colum-1][fila+1] == piezaEnemiga || piezas[colum-1][fila+1] == piezaEnemigaDama){
            jugadasDisponibles[colum-1][fila+1] = 1;
        }
        else if(piezas[colum+1][fila+1] == piezaEnemiga || piezas[colum+1][fila+1] == piezaEnemigaDama){
            jugadasDisponibles[colum+1][fila+1] = 1;
        }
        repaint();
    }

    public void verificarDama(int col, int row){
        if(piezas[col][row] == PEONROJO && row == 0)
            piezas[col][row] = DAMAROJA;
        else if(piezas[col][row] == PEONBLANCO && row == cantDeCuadradosPorfila-1)
            piezas[col][row] = DAMABLANCA;
        else return;
    }

    public void quitarPieza(int colum, int fila, int columGuardada, int filaGuardada){ //might be a better way to do this,
        // but detects position of opponent piece based on destination and original position
        int piezaFila = -1;
        int piezaColum = -1;
        if(colum > columGuardada && fila > filaGuardada){
            piezaFila = fila-1;
            piezaColum = colum-1;
        }
        if(colum > columGuardada && fila < filaGuardada){
            piezaFila = fila+1;
            piezaColum = colum-1;
        }
        if(colum < columGuardada && fila > filaGuardada){
            piezaFila = fila-1;
            piezaColum = colum+1;
        }
        if(colum < columGuardada && fila < filaGuardada){
            piezaFila = fila+1;
            piezaColum = colum+1;
        }
        piezas[piezaColum][piezaFila] = cuadradoVacio;
    }

    public void getJugadasDisponibles(int colum, int fila){
        enpartida = true;
        if((chequearPieza(colum, fila) == true)){ //checks if the piece is assigned to the current player
            if(piezas[colum][fila] == PEONROJO){  // solo va hacia el norte, verifica la fila de arriba de la suya
                irHaciaArriba(colum, fila);
            }
            if(piezas[colum][fila] == PEONBLANCO){ // only goes south, checks the row below it's own
                irHaciaAbajo(colum, fila);
            }
            if(piezas[colum][fila] == DAMAROJA || piezas[colum][fila] == DAMABLANCA){ // Goes up OR down 1 row below it's own
                irHaciaArriba(colum, fila);
                //getUp(col, row);
                irHaciaAbajo(colum, fila); // GET UP GET UP AND GET DOWN
            }
            repaint();
        }
    }

    public void irHaciaArriba(int colum, int fila){ // Get Up availability
        int filaDearriba = fila-1;
        if(colum == 0 && fila != 0){ //X=0, Y is not 0
            for(int i = colum; i < colum+2; i++){ //check to right
                if(piezas[colum][fila] != 0 && piezas[i][filaDearriba] != 0){
                    if(puedeSaltar(colum, fila, i, filaDearriba) == true){
                        int saltarColum = getPosiciondeSalto(colum, fila, i, filaDearriba)[0];
                        int saltarFila = getPosiciondeSalto(colum, fila, i, filaDearriba)[1];
                        jugadasDisponibles[saltarColum][saltarFila] = 1;
                    }
                }
                else if(tablero[i][filaDearriba] == 1 && piezas[i][filaDearriba] == 0)
                    jugadasDisponibles[i][filaDearriba] = 1;
            }
        }
        else if(colum == (cantDeCuadradosPorfila - 1) && fila != 0){ //X=max, Y is not 0
            if(piezas[colum][fila] != 0 && piezas[colum-1][filaDearriba] != 0){
                if(puedeSaltar(colum, fila, colum-1, filaDearriba) == true){
                    int saltarColum = getPosiciondeSalto(colum, fila, colum-1, filaDearriba)[0];
                    int saltarFila = getPosiciondeSalto(colum, fila, colum-1, filaDearriba)[1];
                    jugadasDisponibles[saltarColum][saltarFila] = 1;
                }
            }
            else if(tablero[colum-1][filaDearriba] == 1 && piezas[colum-1][filaDearriba] == 0)
                jugadasDisponibles[colum-1][filaDearriba] = 1;
        }
        else if(colum != cantDeCuadradosPorfila - 1 && colum != 0 && fila != 0){
            for(int i = colum-1; i <= colum+1; i++){
                if(piezas[colum][fila] != 0 && piezas[i][filaDearriba] != 0){
                    if(puedeSaltar(colum, fila, i, filaDearriba) == true){
                        int saltarColum = getPosiciondeSalto(colum, fila, i, filaDearriba)[0];
                        int saltarFila = getPosiciondeSalto(colum, fila, i, filaDearriba)[1];
                        jugadasDisponibles[saltarColum][saltarFila] = 1;
                    }
                }
                else if(tablero[i][filaDearriba] == 1 && piezas[i][filaDearriba] == 0)
                    jugadasDisponibles[i][filaDearriba] = 1;
            }
        }
    }

    public void irHaciaAbajo(int col, int row){
        int rowDown = row+1;
        if(col == 0 && row != cantDeCuadradosPorfila-1){
            if(piezas[col][row] != 0 && piezas[col+1][rowDown] != 0){
                if(puedeSaltar(col, row, col+1, rowDown) == true){
                    int jumpCol = getPosiciondeSalto(col, row, col+1, rowDown)[0];
                    int jumpRow = getPosiciondeSalto(col, row, col+1, rowDown)[1];
                    jugadasDisponibles[jumpCol][jumpRow] = 1;
                }
            }
            else if(tablero[col+1][rowDown] == 1 && piezas[col+1][rowDown] == 0)
                jugadasDisponibles[col+1][rowDown] = 1;
        }
        else if(col == cantDeCuadradosPorfila - 1 && row != cantDeCuadradosPorfila-1){
            if(piezas[col][row] != 0 && piezas[col-1][rowDown] != 0){
                if(puedeSaltar(col, row, col-1, rowDown) == true){
                    int jumpCol = getPosiciondeSalto(col, row, col-1, rowDown)[0];
                    int jumpRow = getPosiciondeSalto(col, row, col-1, rowDown)[1];
                    jugadasDisponibles[jumpCol][jumpRow] = 1;
                }
            }
            else if(tablero[col-1][rowDown] == 1 && piezas[col-1][rowDown] == 0)
                jugadasDisponibles[col-1][rowDown] = 1;
        }
        else if(col != cantDeCuadradosPorfila-1 && col != 0 && row != cantDeCuadradosPorfila-1){
            for(int i = col-1; i <= col+1; i++){
                if(piezas[col][row] != 0 && piezas[i][rowDown] != 0){
                    if(puedeSaltar(col, row, i, rowDown) == true){
                        int jumpCol = getPosiciondeSalto(col, row, i, rowDown)[0];
                        int jumpRow = getPosiciondeSalto(col, row, i, rowDown)[1];
                        jugadasDisponibles[jumpCol][jumpRow] = 1;
                    }
                }
                else if(tablero[i][rowDown] == 1 && piezas[i][rowDown] == 0)
                    jugadasDisponibles[i][rowDown] = 1;
            }
        }
    }

    public boolean chequearPieza(int col, int row){
        if(jugadorActual == PEONROJO && (piezas[col][row] == PEONROJO || piezas[col][row] == DAMAROJA)) //bottom
            return true;
        if(jugadorActual == PEONBLANCO && (piezas[col][row] == PEONBLANCO || piezas[col][row] == DAMABLANCA)) //top
            return true;
        else
            return false;
    }

    public boolean isLegalPos(int col, int row){
        if(row < 0 || row >= cantDeCuadradosPorfila || col < 0 || col >= cantDeCuadradosPorfila)
            return false;
        else return true;
    }

    public boolean puedeSaltar(int col, int row, int opponentCol, int opponentRow){
        //Steps for checking if canJump is true: determine piece within movement. Then check if its an opponent piece, then if the space behind it is empty
        //and in bounds
        // 4 conditions based on column and row relations to the other piece
        if(((piezas[col][row] == PEONBLANCO || piezas[col][row] == DAMABLANCA) && (piezas[opponentCol][opponentRow] == PEONROJO || piezas[opponentCol][opponentRow] == DAMAROJA)) || (piezas[col][row] == PEONROJO || piezas[col][row] == DAMAROJA) && (piezas[opponentCol][opponentRow] == PEONBLANCO || piezas[opponentCol][opponentRow] == DAMABLANCA)){
            //If the piece is white/red and opponent piece is opposite TODO fix this if. It's so ugly
            if(opponentCol == 0 || opponentCol == cantDeCuadradosPorfila-1 || opponentRow == 0 || opponentRow == cantDeCuadradosPorfila-1)
                return false;
            int[] toData = getPosiciondeSalto(col, row, opponentCol, opponentRow);
            if(isLegalPos(toData[0],toData[1]) == false) //check board outofbounds
                return false;
            if(piezas[toData[0]][toData[1]] == 0){
                salto = true;
                return true;
            }
        }
        return false;
    }

    public int[] getPosiciondeSalto(int col, int row, int opponentCol, int opponentRow){
        if(col > opponentCol && row > opponentRow && piezas[col-2][row-2] == 0)
            return new int[] {col-2, row-2};
        else if(col > opponentCol && row < opponentRow && piezas[col-2][row+2] == 0)
            return new int[] {col-2, row+2};
        else if(col < opponentCol && row > opponentRow && piezas[col+2][row-2] == 0)
            return new int[] {col+2, row-2};
        else
            return new int[] {col+2, row+2};
    }

    // Methods that must be included for some reason? WHY
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void actionPerformed(ActionEvent e) {}
}