import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VentanaDamas extends JFrame {
    public static int width = 720;
    public static int height =720;
    static BufferedImage crownImage = null;
    public VentanaDamas(Damas damas){
        super("Damas");
        try {
            crownImage = ImageIO.read(new File("images/crown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(width, height);
        setIconImage(crownImage);
        setBackground(Color.cyan);
        setLocationRelativeTo(null);
        pack();
        Insets insets = getInsets();
        int frameLeftBorder = insets.left;
        int frameRightBorder = insets.right;
        int frameTopBorder = insets.top;
        int frameBottomBorder = insets.bottom;
        setPreferredSize(new Dimension(width + frameLeftBorder + frameRightBorder, height + frameBottomBorder + frameTopBorder));
        setMaximumSize(new Dimension(width + frameLeftBorder + frameRightBorder, height + frameBottomBorder + frameTopBorder));
        setMinimumSize(new Dimension(width + frameLeftBorder + frameRightBorder, height + frameBottomBorder + frameTopBorder));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(damas);
        requestFocus();
        setVisible(true);
        add(damas);
    }
}
