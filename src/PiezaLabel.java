import javax.swing.*;
import java.awt.*;
// como
public class PiezaLabel extends JLabel {
    Pieza pieza;

    public PiezaLabel(Pieza pieza) {
        super(pieza.dameEmoji());
        this.pieza = pieza;
        Font font = getFont();
        setFont(new Font(font.getName(), font.getStyle(), 50));
        setHorizontalAlignment(SwingConstants.CENTER);
        // setBorder(BorderFactory.createLineBorder(Color.BLUE)); //borde para debuggear
    }
}
