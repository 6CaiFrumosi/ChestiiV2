package com.some_game;
import javax.swing.JFrame;
public class Window extends JFrame{
    static private int height;
    static private int width;
    public Window()
    {
        setTitle("Some Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //1110, 1110
        width=1216;
        height=608;
        setContentPane(GamePanel.getInstance(width, height));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static int getW(){return width;}
    public static int getH(){return height;}
}
