import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {

    private Boolean play = false;   //checks if game is being played
    private int score = 0;  //score
    private int numberOfBricks;    //number of bricks

    private final Timer timer;
    private int delay = 8;

    private int playerX = 310;  //player X position, y position is fixed at 120

    //ball spawn coordinates
    private int ballPositionX = 350;    //ball x
    private int ballPositionY = 350;    //ball y

    //change speed here
    private int ballXDir = -1;  //ball x axis velocity
    private int ballYDir = -2;  //ball y axis velocity

    private MapCreator map;
    private final int row;
    private final int column;

    public Game(int row, int column){
        playMusic();
        this.row = row;
        this.column = column;
        numberOfBricks = row * column;
        map = new MapCreator(this.row, this.column);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();
    }

    public static void playMusic(){
        try{
            Random ran = new Random();
            File file;
            if(ran.nextBoolean()){
                file = new File("res/bensound-happyrock.wav");
            }
            else{
                file= new File("res/bensound-jazzyfrenchy.wav");
            }
            if(file.exists()){
                System.out.println(file.getName());
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else{
                System.out.println("Cannot find audio file");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);    //background color is black
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        g.setColor(Color.RED);  //displaying the border
        g.fillRect(0, 0, 3, 592);   //top border
        g.fillRect(0, 0, 692, 3);   //left border
        g.fillRect(692, 0, 3, 592); //right border

        g.setColor(Color.blue); //player display
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.green);    //ball display
        g.fillOval(ballPositionX, ballPositionY, 20, 20);

        //display score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 590, 30);
        //user wins
        if(numberOfBricks <= 0){
            numberOfBricks = row * column;
            map = new MapCreator(row, column);
        }
        //user loses
        if(ballPositionY > 570){
            //stop game
            play = false;
            ballXDir = 0;
            ballYDir = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Lost, Score: " + score, 230, 300);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press any key to restart", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){
            //Ball and player interaction

            //ball hits player
            if(new Rectangle(ballPositionX, ballPositionY, 20, 30).intersects(new Rectangle(playerX,  550, 100, 8))){
                ballYDir = -ballYDir;
            }

            //ball hits brick
            for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] != 0){

                        Rectangle brick = new Rectangle(j * map.width + 50, i * map.height + 50, map.width, map.height);
                        Rectangle copy = brick;
                        Rectangle ball = new Rectangle(ballPositionX, ballPositionY, 20, 20);

                        if(ball.intersects(brick)){
                            map.brickValue(0, i, j);
                            numberOfBricks--;
                            score += 1;

                            redirectBall(brick);
                        }
                    }
                }
            }
            ballPositionX += ballXDir;
            ballPositionY += ballYDir;

            //ball hits walls
            if(ballPositionX < 0 || ballPositionX > 670){
                ballXDir = -ballXDir;
            }

            //ball hits ceiling
            if(ballPositionY < 0){
                ballYDir = -ballYDir;
            }
        }
        repaint();
    }

    /**
     * Redirect ball when hitting brick
     * @param brick
     */

    public void redirectBall(Rectangle brick){
        if(ballPositionX + 19 <= brick.x || ballPositionX + 1 >= brick.x + brick.width){
            ballXDir = -ballXDir;
        }
        else {
            ballYDir = -ballYDir;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 8){   //make sure we don't go past boundary
                playerX = 0;
            }
            else{
                Left(20);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 592){ //make sure we don't go past boundary
                playerX = 592;
            }
            else {
                Right(20);
            }
        }
        //if the game is over press any key
        else if(!play){
            restart();
        }
    }

    /**
     * Restart the game
     */
    public void restart(){
        play = true;
        ballPositionX = 250;
        ballPositionY = 350;
        ballXDir = -1;
        ballYDir = -2;

        score = 0;
        numberOfBricks = row * column;
        map = new MapCreator(row, column);

        repaint();
    }

    /**
     * Move the player to the right by n pixels
     */
    public void Right(int n){
        play = true;
        playerX += n;
    }

    /**
     * Move the player to the left by n pixels
     */
    public void Left(int n){
        play = true;
        playerX -= n;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
