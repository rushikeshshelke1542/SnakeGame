import javax.swing.*;
import java.awt.*;

public class Boards extends JPanel {
    int B_WIDTH = 400;
    int B_HEIGHT = 400;
    Boards(){

        //set board height width
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
    }
}
