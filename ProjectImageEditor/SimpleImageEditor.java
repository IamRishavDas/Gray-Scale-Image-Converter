import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SimpleImageEditor {
    private JFrame frame;
    private JPanel panel;
    private JButton openButton;
    private JButton saveButton;
    private JButton grayscaleButton;
    private BufferedImage image;

    public SimpleImageEditor() {
        frame = new JFrame("Thinking about Name for my image editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        openButton = new JButton("Open");
        saveButton = new JButton("Save");
        grayscaleButton = new JButton("Grayscale");

        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Image");
                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File fileToOpen = fileChooser.getSelectedFile();
                        image = ImageIO.read(fileToOpen);
                        displayImage(image);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File fileToSave = fileChooser.getSelectedFile();
                        ImageIO.write(image, "png", fileToSave);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        grayscaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (image != null) {
                    applyGrayscale();
                }
            }
        });

        panel.add(openButton);
        panel.add(saveButton);
        panel.add(grayscaleButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void displayImage(BufferedImage img) {
        JLabel label = new JLabel(new ImageIcon(img));
        panel.add(label);
        frame.revalidate();
    }

    private void applyGrayscale() {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color gray = new Color(avg, avg, avg);
                image.setRGB(x, y, gray.getRGB());
            }
        }
        displayImage(image);
    }

    public static void main(String[] args) {
        new SimpleImageEditor();
    }
}