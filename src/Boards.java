import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Boards extends JPanel implements ActionListener {
    int B_WIDTH = 400;
    int B_HEIGHT = 400;

    int DOT_SIZE = 10;
    int MAX_DOTS = 1600;

    int DOTS;

    int x[] = new int[MAX_DOTS];
    int y[] = new int [MAX_DOTS];

    int apple_x;
    int apple_y;

    Image head, body, apple;

    Timer timer;

    int DELAY = 300;

    boolean directionLeft = true;
    boolean directionRight = false;

    boolean directionUp = false;

    boolean directionDown = false;


    Boards(){

        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        //set board height width
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);

        initGame();
        loadImages();
    }

    //Initialize Game
    public void initGame(){
        DOTS = 3;
        x[0] = 50;
        y[0] = 50;
        for(int i=0;i<DOTS;i++){
            x[i] = x[0]+DOT_SIZE*i;
            y[i] = y[0];
        }

        locateApple();

        timer = new Timer(DELAY,this);
        timer.start();

    }

    //load the images from resource folder
    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("src/resource/dot.png");
        body = bodyIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resource/apple.png");
        apple = appleIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resource/head.png");
        head = headIcon.getImage();
    }

    //draw images at snake and apple location
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    //draw image
    public void doDrawing(Graphics g){
        g.drawImage(apple, apple_x, apple_y, this);

        for(int i=0;i<DOTS;i++){
            if(i==0){
                g.drawImage(head, x[0], y[0], this);
            }
            else{

                g.drawImage(body, x[i], y[i], this);
            }
        }
    }

    //randomize the location of apple
    public void locateApple(){

        apple_x = (int) (Math.random() * 39) * 10;
        apple_y = (int) (Math.random() * 39) * 10;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        move();
        repaint();
    }

    private void move() {

        for(int i=DOTS-1;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(directionLeft){
            x[0]-=DOT_SIZE;
        }

        if(directionRight){
            x[0]+=DOT_SIZE;
        }

        if(directionDown){
            y[0]+=DOT_SIZE;
        }

        if(directionUp){
            y[0]-=DOT_SIZE;
        }
    }

    //control using key

    private class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();

            if(key== KeyEvent.VK_LEFT && !directionRight){
                directionLeft = true;
                directionDown = false;
                directionUp = false;
            }


            if(key == KeyEvent.VK_RIGHT && !directionLeft){
                directionRight = true;
                directionDown = false;
                directionUp = false;
            }


            if(key == KeyEvent.VK_UP && !directionDown){
                directionLeft = false;
                directionRight = false;
                directionUp = true;
            }

            if(key == KeyEvent.VK_DOWN && !directionUp){
                directionLeft = false;
                directionRight = false;
                directionDown = true;

            }

        }
    }


}
