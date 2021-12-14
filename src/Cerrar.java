import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
