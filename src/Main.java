import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {
    private static Menu menu;
    public static class AbrirventanaDamas implements ActionListener {
        boolean cargarpartida;
        public AbrirventanaDamas(boolean cargarpartida){
            this.cargarpartida=cargarpartida;
        }
        static boolean apretado = false;
        @Override
        public void actionPerformed(ActionEvent e) {
            VentanaDamas ventana = new VentanaDamas(cargarpartida);
            apretado = true;
            if (apretado) {
                Main.jugar();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        menu = new Menu();
        menu.setVisible(true);
        menu.jugar2.addActionListener(new AbrirventanaDamas(false));
        menu.cargarpartida1.addActionListener(new AbrirventanaDamas(true));
    }

    public static void jugar() {
        menu.setVisible(false);
    }
}
