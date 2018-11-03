package pl.project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class grafa extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

    private JMenuBar jmb;
    private JMenu file, rotate;
    private JMenuItem open, rotation;
    private BufferedImage image;
    private AffineTransform at;
    private float scale = 1;
    private boolean isRotate = false, isScale = false, isTranslate = false, isPaintOnce = false, isPaintAble = false;
    private int Xmin, Ymin;
    private Dimension baseDimension, dims;
    private int curX, curY;
    private Timer timer;
    private int m = 0;

    public grafa(){

        setSize(1000,700);
        setLayout(null);


        jmb = new JMenuBar();
        jmb.setBounds(0,0,1000,40);
        add(jmb);

        file = new JMenu("Open");
        jmb.add(file);

        open = new JMenuItem("Open");
        file.add(open);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser f = new JFileChooser();

                FileNameExtensionFilter fnef = new FileNameExtensionFilter("Png","jpg", "png");

                f.setFileFilter(fnef);

                if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

                    File file = f.getSelectedFile();

                    try {

                        image = ImageIO.read(file);

                        isPaintOnce = true;
                        isPaintAble = true;

                        baseDimension = getSize();

                        repaint();


                    }catch (IOException e3){
                        e3.printStackTrace();
                    }
                }

            }
        });

        rotate = new JMenu("Rotate");
        jmb.add(rotate);

        rotation = new JMenuItem("Rotate");
        rotate.add(rotation);
        rotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                isTranslate = false;
                isScale = false;
                isRotate = true;

                if (m >= 360)
                    m = 0;
                else
                m += 90;

                repaint();
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                isTranslate = false;
                isScale = true;
                if (scale < 1){
                    scale = 1;
                }else {
                    double delta = 0.05f * e.getPreciseWheelRotation();
                    scale += delta;
                    revalidate();
                    repaint();
                }

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();

                curX = p.x;

                curY = p.y;

                scale = 1;

                isTranslate = true;

                Xmin = e.getX();
                Ymin = e.getY();


                    timer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ea) {

                            if (timer.isRunning()) {
                                repaint();
                            }

                        }
                    });

                    timer.start();
                }


            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {

                isTranslate = true;
                //timer.start();

                Xmin = e.getX();
                Ymin = e.getY();


                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                baseDimension.setSize(dims.getSize());
                timer.stop();

                repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void paint(Graphics g) {

        super.paint(g);

        if (isPaintAble) {

            AffineTransform transform = new AffineTransform();

            dims = getSize();


            if (isPaintOnce) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(image, 1, 41, this);
                g2d.dispose();
                isPaintOnce = false;
            }

            if ((baseDimension.getWidth() != dims.getWidth()) || (baseDimension.getHeight() != dims.getHeight())) {
                g.drawImage(image, (int) dims.getWidth()-((int)baseDimension.getWidth()-curX), (int) dims.getHeight()-((int)baseDimension.getHeight()-curY), this);
                g.dispose();
            }

            if (isScale) {
                at = new AffineTransform();
                transform.setTransform(at);
                isScale = false;
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.scale(scale, scale);
                g2d.drawImage(image, Xmin, Ymin, this);
                g2d.dispose();
            }


            if (isTranslate) {
                Graphics2D g2d = (Graphics2D) g.create();
                at = AffineTransform.getTranslateInstance(Xmin,Ymin);
                at.translate(Xmin, Ymin);
                g2d.drawImage(image, Xmin, Ymin, this);
                g2d.dispose();
            }

            if (isRotate) {
                Graphics2D g2d = (Graphics2D) g.create();

                AffineTransform at = AffineTransform.getTranslateInstance(Xmin,Ymin);
                at.rotate(Math.toRadians(m), image.getWidth()/2, image.getHeight()/2);

                g2d.drawImage(image,at,null);

                g2d.dispose();
                isRotate = false;
            }

        }

    }


}
