import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Cerrar implements ActionListener {

    static boolean apretado = false;

    @Override
    public void actionPerformed(ActionEvent e) {

        VentanaDamas ventana = new VentanaDamas();
        apretado = true;
        if (apretado) {
            Main.jugar();
        }
    }
}
