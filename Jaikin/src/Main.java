import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Click to Draw Circle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);

            DrawPanel drawPanel = new DrawPanel();
            frame.add(drawPanel);

            frame.setFocusable(true);
            frame.setVisible(true);
        });
    }
}

class DrawPanel extends JPanel {
    private final int radius = 5;
    private final List<Point> points = new ArrayList<>();
    private List<Point> chaikinPoints = new ArrayList<>();
    private boolean startDrawing = false;

    private int Curr_Iteration = 0;
    private Timer timer;

    public DrawPanel() {
        setBackground(Color.BLACK);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!startDrawing) {
                    points.add(e.getPoint());
                    repaint();
                    requestFocusInWindow();
                }
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
                    Curr_Iteration = 0;
                    timer.stop();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !startDrawing && points.size() > 1) {
                    System.out.println("ENTER PRESSED!");
                    startDrawing = true;
                    Curr_Iteration = 0;
                    chaikinPoints = new ArrayList<>(points);
                    startAnimation();
                }
            }
        });

        timer = new Timer(1000, e -> {
            if (Curr_Iteration < 7) {
                chaikinPoints = chaikin(chaikinPoints);
                Curr_Iteration++;
                repaint();
            } else {
                timer.stop();
                Curr_Iteration = 0;
                System.out.println("Chaikin animation completed.");
            }
        });
    }

    public void startAnimation() {
        if (chaikinPoints.size() >= 2) {
            System.err.println("Animation started!");
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        for (Point p : points) {
            g.drawOval(p.x - radius, p.y - radius, radius, radius);
        }

        if (startDrawing) {
            if (chaikinPoints.size() > 2) {
                // g.setColor(Color.RED);
                for (int i = 0; i < chaikinPoints.size() - 1; i++) {
                    Point start = chaikinPoints.get(i);
                    Point end = chaikinPoints.get(i + 1);
                    g.setColor(Color.YELLOW);
                    g.drawLine(start.x, start.y, end.x, end.y);
                }
            } else if (chaikinPoints.size() == 2) {
                    g.setColor(Color.YELLOW);
                    g.drawLine(chaikinPoints.get(0).x - radius, chaikinPoints.get(0).y - radius, chaikinPoints.get(1).x - radius, chaikinPoints.get(1).y - radius);
            }
        } 
    }

    public List<Point> chaikin(List<Point> inputPoints) {
        List<Point> newPoints = new ArrayList<>();
        if (inputPoints.size() < 2) {
            return newPoints;
        } else if (inputPoints.size() == 2) {
            return inputPoints;
        }
        try {
            // Add first point (to keep curve start)
            newPoints.add(inputPoints.get(0));
            for (int i = 0; i < inputPoints.size() - 1; i++) {
                Point start = inputPoints.get(i);
                Point end = inputPoints.get(i + 1);

                int x0 = (int) (0.75 * start.x + 0.25 * end.x);
                int y0 = (int) (0.75 * start.y + 0.25 * end.y);

                int x1 = (int) (0.25 * start.x + 0.75 * end.x);
                int y1 = (int) (0.25 * start.y + 0.75 * end.y);

                newPoints.add(new Point(x0, y0));
                newPoints.add(new Point(x1, y1));
            }
            // Add last point (to keep curve end)
            newPoints.add(inputPoints.get(inputPoints.size() - 1));
        } catch (Exception e) {
            System.err.println("Error in chaikin: " + e);
        }
        System.err.println("Chaikin points = " + newPoints.size());
        return newPoints;
    }
}


// import java.awt.*;
// import java.awt.event.*;
// import java.util.ArrayList;
// import java.util.List;
// import javax.swing.*;
// public class Main {

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             JFrame frame = new JFrame("Click to Draw Circle");
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//             frame.setSize(700, 700);

//             DrawPanel drawPanel = new DrawPanel();
//             frame.add(drawPanel);

//             frame.setFocusable(true);
//             frame.setVisible(true);
//         });
//     }
// }




// class DrawPanel extends JPanel {
//     private final int radius = 5;
//     private final List<Point> points = new ArrayList<>();
//     private List<Point> chaikinPoints = new ArrayList<>();
//     private boolean startDrawing = false;

//     private int Curr_Iteration = 0;
//     private Timer timer;


//     public DrawPanel() {
//         setBackground(Color.BLACK);
//         this.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 points.add(e.getPoint());
//                 repaint(); // Triggers paintComponent
//                 if (startDrawing) {
//                     requestFocusInWindow(false);
//                 }
//                 requestFocusInWindow(true);
//             }

//         });
//         // Key listener to clear canvas on Space key
//         this.addKeyListener(new KeyAdapter() {
//             @Override
//             public void keyPressed(KeyEvent e) {
//                 if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                     System.out.println("Canvas cleared!");
//                     points.clear(); 
//                     chaikinPoints.clear();
//                     startDrawing = false; // Clear all circles
//                     repaint();       // Redraw panel (now empty)
//                 } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                     System.out.println("ENTER PRESSEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED!");
//                     chaikinPoints = new ArrayList<>(points);
//                     startDrawing = true;
//                     startAnimation();
//                     repaint();
//                 }
//             }
//         });
//         timer = new Timer(1000, e -> {
//             if (Curr_Iteration < 7) {
//                 chaikinPoints = chaikin(chaikinPoints);
//                 Curr_Iteration++;
//                 repaint();
//             } else {
//                 timer.stop(); // Stop after 7 iterations
//                 System.out.println("Chaikin animation completed.");
//             }
//         });
//     }
//     public void startAnimation() {
//         System.err.println("Animation started !!!");
//         timer.start();
//     }
//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g); // Clear panel
//         g.setColor(Color.YELLOW);
//         for (Point p : points) {
//             g.drawOval(p.x, p.y, radius, radius);
//         }
//         this.chaikinPoints = chaikin(points);
//         if (startDrawing && chaikinPoints.size() >= 2 ) {
//             for (int i = 0; i < chaikinPoints.size() - 1; i++) {
//                 Point start = chaikinPoints.get(i);
//                 Point end = chaikinPoints.get(i + 1);
//                 g.setColor(Color.RED);
//                 g.drawOval(start.x - radius, start.y - radius, radius, radius);
//                 g.drawOval(end.x- radius , end.y - radius, radius, radius);
//                 g.setColor(Color.CYAN);
//                 g.drawLine(start.x , start.y, end.x, end.y);
//             }
//         }
        
//     }

//     public List<Point> chaikin(List<Point> InputPoints) {
//         List<Point> newPoints = new ArrayList<>();
//         if (InputPoints.size() < 2) {
//             return newPoints;
//         }
//         try {
//             newPoints.add(InputPoints.get(0));
//             for (int i=0 ; i < InputPoints.size() - 1 ; i++) {
//                 Point start = InputPoints.get(i);
//                 Point end = InputPoints.get(i + 1);
//                 //P1
//                  // First new point (closer to start)
//                 int x0 = (int) (0.75 * start.x + 0.25 * end.x);
//                 int y0 = (int) (0.75 * start.y + 0.25 * end.y);

//                 // Second new point (closer to end)
//                 int x1 = (int) (0.25 * start.x + 0.75 * end.x);
//                 int y1 = (int) (0.25 * start.y + 0.75 * end.y);

//                 newPoints.add(new Point(x0, y0));
//                 newPoints.add(new Point(x1, y1));
//             }
//         } catch (Exception e) {
//             System.err.println("Error in chaikin: " + e.toString());
//         }
//         System.err.println("chaikin points = " + newPoints.toString());
//         return newPoints;
//     }
// }


// // class Line {
// //     public final Point p1;
// //     public final Point p2;

// //     public Line(Point start,Point end ) {
// //         this.p1 = start;
// //         this.p2 = end;
// //     }
// // }