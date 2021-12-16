import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Menu menu;

    public static class AbrirventanaDamas implements ActionListener {
        boolean cargarpartida;
        static boolean apretado = false;

        public AbrirventanaDamas(boolean cargarpartida) {
            this.cargarpartida = cargarpartida;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new VentanaDamas(cargarpartida);
            apretado = true;
            if (apretado) {
                menu.setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        menu = new Menu();
        menu.setVisible(true);
        menu.jugar2.addActionListener(new AbrirventanaDamas(false));
        menu.cargarpartida1.addActionListener(new AbrirventanaDamas(true));
    }
}
