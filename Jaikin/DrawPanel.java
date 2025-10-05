package Jaikin;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class DrawPanel extends JPanel {
    private final int radius = 6;
    private final List<Point> points = new ArrayList<>();
    private List<Point> chaikinPoints = new ArrayList<>();
    private boolean startDrawing = false;

    private int curr_Iteration = 0;
    private Timer timer;

    private Point cursorPosition = null;

    public DrawPanel() {
        setBackground(Color.WHITE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!startDrawing && e.getButton() == MouseEvent.BUTTON1) {
                    points.add(e.getPoint());
                    repaint();
                    requestFocusInWindow();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursorPosition = e.getPoint();
                repaint();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("Canvas cleared!");
                    points.clear();
                    chaikinPoints.clear();
                    startDrawing = false;
                    curr_Iteration = 0;
                    timer.stop();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !startDrawing && points.size() > 1) {
                    System.out.println("ENTER PRESSED!");
                    startDrawing = true;
                    curr_Iteration = 0;
                    chaikinPoints = new ArrayList<>(points);
                    startAnimation();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        timer = new Timer(700, e -> {
            System.out.println("points 2 = " + points.toString());
            if (curr_Iteration < 7) {
                chaikinPoints = Chaikin.chaikinAlgo(chaikinPoints);
                curr_Iteration++;
                repaint();
            } else {
                System.out.println("points 2 = " + points.toString());
                curr_Iteration = 0;
                chaikinPoints = new ArrayList<>(points);
                System.out.println("Chaikin animation completed.");
                repaint();
            }
        });
    }

    public void startAnimation() {
        if (chaikinPoints.size() > 2) {
            System.err.println("Animation started!");
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLUE);
        for (Point p : points) {
            g.drawOval(p.x - radius, p.y - radius, radius * 2, radius * 2);
        }

        if (startDrawing) {
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(3));
            if (chaikinPoints.size() > 2) {
                for (int i = 0; i < chaikinPoints.size() - 1; i++) {
                    Point start = chaikinPoints.get(i);
                    Point end = chaikinPoints.get(i + 1);
                    g2d.drawLine(start.x, start.y, end.x, end.y);
                }
            } else if (chaikinPoints.size() == 2) {
                g2d.drawLine(chaikinPoints.get(0).x , chaikinPoints.get(0).y ,
                        chaikinPoints.get(1).x , chaikinPoints.get(1).y );
            }
        }

        g.setColor(new Color(240, 240, 240, 220));
        g.fillRect(10, 10, 250, 60);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Points: " + points.size(), 20, 30);
        g.drawString("Animation Step: " + curr_Iteration + "/7", 20, 50);

        if (cursorPosition != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g.setColor(new Color(240, 240, 240, 220));
            g.fillRect(panelWidth - 180, panelHeight - 50, 170, 40);
            g.setColor(Color.BLACK);
            g.drawString("Cursor: (" + cursorPosition.x + ", " + cursorPosition.y + ")", panelWidth - 170,
                    panelHeight - 25);
        }

        int panelHeight = getHeight();
        g.setColor(new Color(240, 240, 240, 220));
        g.fillRect(10, panelHeight - 110, 280, 100);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Controls:", 20, panelHeight - 90);
        g.drawString("• Click to add points", 20, panelHeight - 70);
        g.drawString("• ENTER to start animation", 20, panelHeight - 50);
        g.drawString("• SPACE to clear canvas", 20, panelHeight - 30);
        g.drawString("• ESC to exit", 20, panelHeight - 10);
    }
    
}