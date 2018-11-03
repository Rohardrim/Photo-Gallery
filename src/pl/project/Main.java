package pl.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main{

    public static void createGUI(){

        JFrame jf = new JFrame();

        grafa g = new grafa();

        jf.add(g);
        jf.setSize(g.getSize());
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

    }


    public static void main(String[] args){

           SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {

                   createGUI();

               }
           });

    }
}
