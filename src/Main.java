import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends Cerrar {
    private static Menu menu;

    public static void main(String[] args) throws IOException {
        menu = new Menu();
        menu.setVisible(true);
        menu.jugar2.addActionListener(new Cerrar());
    }

    public static void jugar() {
        menu.setVisible(false);
    }
}
