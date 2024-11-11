package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

public class GuiView extends JFrame implements IView {
  private final JToggleButton loadButton;
  private final JToggleButton saveButton;
  private final JRadioButton blurButton;
  private final JButton sepiaButton;
  private final JButton sharpenButton;
  private final JButton colorCorrectionButton;
  private final JButton redButton;
  private final JButton greenButton;
  private final JButton blueButton;
  private final JButton valueButton;
  private final JButton intensityButton;
  private final JButton lumaButton;
  private final JButton hflipButton;
  private final JButton vflipButton;
  private final JLabel imageLabel;
  private final JLabel histogramLabel;
  private String imageName;
  private final JPanel topPanel;
  private final JPanel operationPanel;
  private final JPanel imagePanel;
  private final JPanel histogramPanel;
  private final JPanel mainPanel;


  public GuiView(String caption) {
    this.setTitle(caption);
//    this.setSize(20, 20);

    Font font = new Font("SansSerif", Font.BOLD, 14);

    loadButton = new JToggleButton("Load Image");
    saveButton = new JToggleButton("Save Image");
    blurButton = new JRadioButton("Blur");
    sepiaButton = new JButton("Sepia");
    sharpenButton = new JButton("Sharpen");
    redButton = new JButton("Red-component");
    greenButton = new JButton("Green-component");
    blueButton = new JButton("Blue-component");
    valueButton = new JButton("Value-component");
    lumaButton = new JButton("Luma-component");
    intensityButton = new JButton("Intensity-component");
    hflipButton = new JButton("Horizontal-Flip");
    vflipButton = new JButton("Vertical-Flip");
    colorCorrectionButton = new JButton("Color Correction");
    imageLabel = new JLabel();      // For displaying the image
    histogramLabel = new JLabel();  // For displaying the histogram


    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    JScrollPane mainScroll = new JScrollPane(mainPanel);
    this.add(mainScroll, BorderLayout.CENTER);


    topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    topPanel.add(loadButton);
    topPanel.add(saveButton);
    TitledBorder b = BorderFactory.createTitledBorder("Load and Save Operations");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    topPanel.setBorder(b);
    mainPanel.add(topPanel);

    operationPanel = new JPanel();
    operationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    operationPanel.add(blurButton);
    operationPanel.add(sepiaButton);
    operationPanel.add(sharpenButton);
    operationPanel.add(colorCorrectionButton);
    operationPanel.add(redButton);
    operationPanel.add(greenButton);
    operationPanel.add(blueButton);
    operationPanel.add(valueButton);
    operationPanel.add(lumaButton);
    operationPanel.add(intensityButton);
    operationPanel.add(hflipButton);
    operationPanel.add(vflipButton);
    b = BorderFactory.createTitledBorder("Operations");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    operationPanel.setBorder(b);
    mainPanel.add(operationPanel);

    imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout());
    imagePanel.add(imageLabel, BorderLayout.CENTER);
    b = BorderFactory.createTitledBorder("Resultant Image");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    imagePanel.setBorder(b);
    mainPanel.add(imagePanel);
    initialImagePanel();


    histogramPanel = new JPanel();
    histogramPanel.setLayout(new BorderLayout());
    histogramPanel.add(histogramLabel, BorderLayout.CENTER);
    b = BorderFactory.createTitledBorder("Histogram of Resultant Image");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    histogramPanel.setBorder(b);
    mainPanel.add(histogramPanel);

    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  private void initialImagePanel(){
    JLabel label = new JLabel("Load an Image for using the application.",
            SwingConstants.CENTER);
    JScrollPane scroll = new JScrollPane(label);
    scroll.setPreferredSize(new Dimension(200, 200));
    imagePanel.add(scroll);
  }

  @Override
  public void addFeature(Features features) {
    loadButton.addActionListener(e -> features.loadImage());
    blurButton.addActionListener(e -> features.applyBlur(this.imageName));
    sepiaButton.addActionListener(e -> features.applySepia(this.imageName));
    sharpenButton.addActionListener(e -> features.applySharp(this.imageName));
    redButton.addActionListener(e -> features.applyRed(this.imageName));
    greenButton.addActionListener(e -> features.applyGreen(this.imageName));
    blueButton.addActionListener(e -> features.applyBlue(this.imageName));
    valueButton.addActionListener(e -> features.applyValue(this.imageName));
    lumaButton.addActionListener(e -> features.applyLuma(this.imageName));
    intensityButton.addActionListener(e -> features.applyIntensity(this.imageName));
    hflipButton.addActionListener(e->features.applyHorizontalFlip(this.imageName));
    vflipButton.addActionListener(e->features.applyVerticalFlip(this.imageName));
    colorCorrectionButton.addActionListener(e->features.applyColorCorrect(this.imageName));
  }

  @Override
  public File getFilePath() {
    File f = null;
    JFileChooser select = new JFileChooser(".");
    select.setDialogTitle("Select an Image");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "File Extensions","jpg", "jpeg", "png", "ppm"
    );
    select.setFileFilter(filter);
    int result = select.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File temp = select.getSelectedFile();
      String path = temp.getAbsolutePath();
      f = new File(path);
    }
    return f;
  }

  @Override
  public boolean checkImage() {

    if (this.imageName == null) {
      return true;
    }
    int value = JOptionPane.showConfirmDialog(
            this, "Are Sure you want to " +
                    "overwrite the current image",
            "Overwriting the loaded Image ",
            JOptionPane.YES_NO_OPTION
    );
    return value == JOptionPane.YES_OPTION;

  }

  @Override
  public void showImage(String name, BufferedImage image) {
    this.imageName = name;
    JLabel img = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(img);
    scrollPane.setPreferredSize(new Dimension(300,300));
    this.imagePanel.removeAll();
    this.imagePanel.add(scrollPane);
    this.imagePanel.revalidate();
    this.imagePanel.repaint();
  }

  @Override
  public void showImageNotPresent() {
    JOptionPane.showMessageDialog(this,
            "No image loaded! Please load an image before applying Operations.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showHistogram(BufferedImage image) {
    JLabel histogram = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(histogram);
    scrollPane.setPreferredSize(new Dimension(270,270));
    this.histogramPanel.removeAll();
    this.histogramPanel.add(scrollPane);
    this.histogramPanel.revalidate();
    this.histogramPanel.repaint();
  }


}
