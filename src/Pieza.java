import java.util.Arrays;

public class Pieza {
    enum Tipo {
        PEON,
        DAMA
    }

    Tipo tipo;
    boolean color; // true blanco, false negro

    int fila;
    int col;
    boolean turno;
    boolean dama;

    final String[] EMOJIS_BLANCAS = new String[] {"⚪", "◎"};
    final String[] EMOJIS_NEGRAS = new String[] {"⚫", "◉"};

    String dameEmoji() {
        String[] piezas = color ? EMOJIS_BLANCAS : EMOJIS_NEGRAS;
        return piezas[tipo.ordinal()];
    }

    public Pieza(Tipo tipo, boolean color, int col, int fila) {
        this.tipo = tipo;
        this.color = color;
        this.fila = fila;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Pieza{" +
                "tipo=" + tipo +
                ", color=" + color +
                ", fila=" + fila +
                ", col=" + col +
                '}';
    }
}
