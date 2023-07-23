package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class PongGame extends JFrame implements KeyListener {
    int paddleSpeed = 15;
    int ballSpeed = 5;
    int paddleY1 = 250;
    int paddleY2 = 250;
    int ballX = 350;
    int ballY = 250;
    int ballXDirection = -ballSpeed;
    int ballYDirection = -ballSpeed;

    int player1Score = 0;
    int player2Score = 0;

    private final Set<Integer> pressedKeys = new HashSet<>();

    public PongGame() {
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        Image img = createImage(getWidth(), getHeight());
        Graphics gfx = img.getGraphics();
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, 800, 600);

        // Draw the scores
        gfx.setColor(Color.WHITE);
        gfx.setFont(new Font("Arial", Font.PLAIN, 50));
        gfx.drawString(Integer.toString(player1Score), 100, 100); // Lower the y coordinate
        gfx.drawString(Integer.toString(player2Score), 650, 100); // Lower the y coordinate

        gfx.fillRect(10, paddleY1, 20, 100); // paddle 1
        gfx.fillRect(770, paddleY2, 20, 100); // paddle 2

        gfx.fillOval(ballX, ballY, 20, 20); // ball

        g.drawImage(img, 0, 0, this);
    }

    public void update() {
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            paddleY1 -= paddleSpeed;
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            paddleY1 += paddleSpeed;
        }
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            paddleY2 -= paddleSpeed;
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            paddleY2 += paddleSpeed;
        }

        ballX += ballXDirection;
        ballY += ballYDirection;

        if (ballX < 30 && ballY > paddleY1 && ballY < paddleY1 + 100) {
            ballXDirection = ballSpeed;
        } else if (ballX > 750 && ballY > paddleY2 && ballY < paddleY2 + 100) {
            ballXDirection = -ballSpeed;
        } else if (ballX < 0) {
            player2Score++; // Increase score for player 2
            resetBall();
        } else if (ballX > 800) {
            player1Score++; // Increase score for player 1
            resetBall();
        }

        if (ballY < 0 || ballY > 580) {
            ballYDirection *= -1;
        }

        repaint();
    }

    public void resetBall() {
        ballX = 350;
        ballY = 250;
        ballXDirection = -ballSpeed;
        ballYDirection = -ballSpeed;
    }

    public void run() {
        while (true) {
            update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    public static void main(String[] args) {
        PongGame game = new PongGame();
        game.run();
    }
}