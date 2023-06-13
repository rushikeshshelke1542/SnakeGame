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

    int[] x = new int[MAX_DOTS];
    int[] y = new int [MAX_DOTS];

    int apple_x;
    int apple_y;

    Image head, body, apple;

    Timer timer;

    int DELAY = 150;

    boolean directionLeft = true;
    boolean directionRight = false;

    boolean directionUp = false;

    boolean directionDown = false;

    boolean inGame = true;


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
       if(inGame){
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
       else{

           gameOver(g);
           timer.stop();
       }
    }

    //randomize the location of apple
    public void locateApple(){

        apple_x = (int) (Math.random() * 39) * 10;
        apple_y = (int) (Math.random() * 39) * 10;
    }

    public void checkCollision(){

        for(int i=1;i<DOTS;i++){
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;

            }
        }

        if(x[0]<0){
            inGame = false;
        }

        if(x[0]>=B_WIDTH){
            inGame = false;
        }

        if(y[0]<0){
            inGame = false;
        }

        if(y[0]>=B_HEIGHT){
            inGame = false;
        }


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
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

    //make snake eat apple
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            DOTS++;
            locateApple();
        }
    }

    //game over message and score

    public void gameOver(Graphics g){

        String msg = "Game Over";
        int score = (DOTS-3) * 100;
        String scoremsg = "Score: "+ score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH-fontMetrics.stringWidth(msg))/2,B_HEIGHT/4);
        g.drawString(scoremsg, (B_WIDTH-fontMetrics.stringWidth(scoremsg))/2,3*(B_HEIGHT/4));


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
