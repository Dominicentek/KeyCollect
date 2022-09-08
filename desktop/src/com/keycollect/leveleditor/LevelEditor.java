package com.keycollect.leveleditor;

import com.keycollect.utils.ByteArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;

public class LevelEditor {
    public static int selected = 0;
    public static int[][] tilemap = new int[24][16];
    public static int spawnX = 0;
    public static int spawnY = 0;
    public static int mouseX = 0;
    public static int mouseY = 0;
    public static boolean eraseMode = false;
    public static void main(String[] args) throws Exception {
        BufferedImage ground = ImageIO.read(new File("assets/assets/images/ground.png"));
        BufferedImage spike = ImageIO.read(new File("assets/assets/images/spike.png"));
        BufferedImage key = ImageIO.read(new File("assets/assets/images/key.png"));
        BufferedImage exit = ImageIO.read(new File("assets/assets/images/exit.png"));
        JFrame frame = new JFrame("Key Collect Level Editor");
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().setPreferredSize(new Dimension(778, 559));
        frame.pack();
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.add(new JPanel() {
            public void paint(Graphics g) {
                for (int i = 0; i < 4; i++) {
                    g.setColor(new Color(selected == i ? 0x7F7F7F : 0x000000));
                    g.fillRect(3 + i * 37, 3, 36, 36);
                }
                g.drawImage(ground, 5, 5, 32, 32, this);
                g.drawImage(spike, 5 + 37, 5, 32, 32, this);
                g.drawImage(key, 5 + 37 * 2, 5, 32, 32, this);
                g.drawImage(exit, 5 + 37 * 3, 5, 32, 32, this);
                g.setColor(new Color(0, 0, 0));
                g.fillRect(5, 42, 768, 512);
                for (int x = 0; x < 24; x++) {
                    for (int y = 0; y < 16; y++) {
                        if (spawnX == x && spawnY == y) {
                            g.setColor(new Color(127, 127, 127));
                            g.fillRect(5 + x * 32, 42 + y * 32, 32, 32);
                        }
                        if (tilemap[x][y] == 1) g.drawImage(ground, 5 + x * 32, 42 + y * 32, 32, 32, this);
                        if (tilemap[x][y] == 2) g.drawImage(spike, 5 + x * 32, 42 + y * 32, 32, 32, this);
                        if (tilemap[x][y] == 3) g.drawImage(key, 5 + x * 32, 42 + y * 32, 32, 32, this);
                        if (tilemap[x][y] == 4) g.drawImage(exit, 5 + x * 32, 42 + y * 32, 32, 32, this);
                    }
                }
                g.setColor(new Color(0, 0, 0));
                g.drawRect(517 + 128, 5, 128, 32);
                g.drawString("Export", 517 + 128 + 64 - g.getFontMetrics().stringWidth("Export") / 2, 26);
                g.drawRect(517 - 5, 5, 128, 32);
                g.drawString("Import", 517 - 5 + 64 - g.getFontMetrics().stringWidth("Import") / 2, 26);
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = (mouseX - 5) / 32;
                int y = (mouseY - 42) / 32;
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (new Rectangle(5, 5, 32, 32).contains(new Point(mouseX, mouseY))) {
                        selected = 0;
                    }
                    if (new Rectangle(5 + 37, 5, 32, 32).contains(new Point(mouseX, mouseY))) {
                        selected = 1;
                    }
                    if (new Rectangle(5 + 37 * 2, 5, 32, 32).contains(new Point(mouseX, mouseY))) {
                        selected = 2;
                    }
                    if (new Rectangle(5 + 37 * 3, 5, 32, 32).contains(new Point(mouseX, mouseY))) {
                        selected = 3;
                    }
                    if (new Rectangle(517 + 128, 5, 128, 32).contains(new Point(mouseX, mouseY))) {
                        try {
                            ByteArray array = new ByteArray(24 * 16 + 2);
                            for (int Y = 0; Y < 16; Y++) {
                                for (int X = 0; X < 24; X++) {
                                    array.writeByte(tilemap[X][Y]);
                                }
                            }
                            array.writeByte(spawnX);
                            array.writeByte(spawnY);
                            OutputStream out = new FileOutputStream("level.lvl");
                            out.write(array.array());
                            out.close();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (new Rectangle(517 - 5, 5, 128, 32).contains(new Point(mouseX, mouseY))) {
                        try {
                            InputStream in = new FileInputStream("level.lvl");
                            byte[] data = new byte[in.available()];
                            in.read(data);
                            in.close();
                            ByteArray array = new ByteArray(data);
                            for (int Y = 0; Y < 16; Y++) {
                                for (int X = 0; X < 24; X++) {
                                    tilemap[X][Y] = array.readUnsignedByte();
                                }
                            }
                            spawnX = array.readUnsignedByte();
                            spawnY = array.readUnsignedByte();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (x < 0 || x >= 24 || y < 0 || y >= 24 || mouseY < 42) return;
                if (e.getButton() == MouseEvent.BUTTON1) {
                    eraseMode = tilemap[x][y] != 0;
                    tilemap[x][y] = eraseMode ? 0 : (selected + 1);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    spawnX = x;
                    spawnY = y;
                }
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getXOnScreen() - frame.getContentPane().getLocationOnScreen().x;
                mouseY = e.getYOnScreen() - frame.getContentPane().getLocationOnScreen().y;
                int x = (mouseX - 5) / 32;
                int y = (mouseY - 42) / 32;
                if (x < 0 || x >= 24 || y < 0 || y >= 24 || mouseY < 42) {
                    return;
                }
                tilemap[x][y] = eraseMode ? 0 : (selected + 1);
            }
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getXOnScreen() - frame.getContentPane().getLocationOnScreen().x;
                mouseY = e.getYOnScreen() - frame.getContentPane().getLocationOnScreen().y;
            }
        });
        frame.setVisible(true);
        while (true) {
            frame.repaint();
            Thread.sleep(10);
        }
    }
}
