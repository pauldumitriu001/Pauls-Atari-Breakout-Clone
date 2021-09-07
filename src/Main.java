import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Game game = new Game(4, 7);

//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //makes frame cover the whole screen
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Pauls Atari Clone");    //title of window
        frame.setResizable(false);  //allow user to resize the frame, default false
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //ends the program when user closes the window
        frame.add(game);
        frame.setVisible(true); //allows the user to see the window, ALWAYS HAS TO BE LAST IN THE CLASS FOR COMPONENTS TO BE ADDED

    }
}
