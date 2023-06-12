import javax.swing.*;

public class SnakeGame extends JFrame {
    Boards boards;
    SnakeGame(){
        boards = new Boards();
        add(boards);
        pack();
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();

    }
}