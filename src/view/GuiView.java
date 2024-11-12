package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

public class GuiView extends JFrame implements IView {
  private JToggleButton loadButton;
  private JToggleButton saveButton;
  private JButton blurButton;
  private JButton sepiaButton;
  private JButton sharpenButton;
  private JButton colorCorrectionButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton valueButton;
  private JButton intensityButton;
  private JButton lumaButton;
  private JButton hflipButton;
  private JButton vflipButton;
  private JButton compressButton;
  private JButton splitButton;
  private JButton applyButton;
  private JButton adjButton;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private String imageName;
  private String commandName;
  private JPanel topPanel;
  private JPanel operationPanel;
  private JPanel imagePanel;
  private JPanel splitimagePanel;
  private JPanel histogramPanel;
  private JFrame splitFrame;
  private JTextField textField;
  private Font font;

  public GuiView(String caption) {
    this.setTitle(caption);
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.initializeComponents();

    this.setLayout(new BorderLayout());

    JLabel headingLabel = new JLabel("Image Processing Application", JLabel.CENTER);
    headingLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    headingLabel.setPreferredSize(new Dimension(800, 50));

    this.add(headingLabel, BorderLayout.NORTH);

    JPanel spacePanel = new JPanel();
    spacePanel.setPreferredSize(new Dimension(800, 20));
    this.add(spacePanel, BorderLayout.CENTER);

    this.createTopPanel();
    this.createOperationalPanel();
    this.createImagePanel();
    this.createHistogramPanel();

    JScrollPane operationScrollPane = new JScrollPane(this.operationPanel);
    operationScrollPane.setPreferredSize(new Dimension(180, 500));
    operationScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout());
    leftPanel.add(this.topPanel, BorderLayout.NORTH);
    leftPanel.add(operationScrollPane, BorderLayout.CENTER);
    this.add(leftPanel, BorderLayout.WEST);


    JPanel rightPanel = new JPanel(new BorderLayout());
    JPanel imageAndHistogramPanel = new JPanel();
    imageAndHistogramPanel.setLayout(new GridLayout(2, 1, 0, 10));
    imageAndHistogramPanel.add(this.imagePanel);
    imageAndHistogramPanel.add(this.histogramPanel);
    imageAndHistogramPanel.setPreferredSize(new Dimension(400, 700));
    rightPanel.add(imageAndHistogramPanel, BorderLayout.CENTER);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, histogramPanel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerLocation(0.5);

    this.add(splitPane, BorderLayout.CENTER);
    this.pack();
    this.setVisible(true);
  }

  private void initializeComponents() {
    font = new Font("SansSerif", Font.BOLD, 14);
    loadButton = new JToggleButton("Load Image");
    saveButton = new JToggleButton("Save Image");
    blurButton = new JButton("Blur");
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
    compressButton = new JButton("Compression");
    adjButton = new JButton("Levels-Adjust");
    splitButton = new JButton("Split");
    applyButton = new JButton("Apply");
    imageLabel = new JLabel();
    histogramLabel = new JLabel();
    textField = new JTextField(10);
  }

  private void createTopPanel() {
    this.topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    this.topPanel.add(loadButton);
    this.topPanel.add(saveButton);
    TitledBorder border = BorderFactory.createTitledBorder("Load and Save");
    border.setTitleFont(font);
    border.setTitleJustification(TitledBorder.LEFT);
    this.topPanel.setBorder(border);
  }

  private void createOperationalPanel() {
    this.operationPanel = new JPanel();
    this.operationPanel.setLayout(new GridBagLayout());  // Use GridBagLayout for better control
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);

    Dimension buttonSize = new Dimension(150, 40);

    this.setButtonProperties(blurButton, buttonSize);
    this.setButtonProperties(sepiaButton, buttonSize);
    this.setButtonProperties(sharpenButton, buttonSize);
    this.setButtonProperties(colorCorrectionButton, buttonSize);
    this.setButtonProperties(redButton, buttonSize);
    this.setButtonProperties(greenButton, buttonSize);
    this.setButtonProperties(blueButton, buttonSize);
    this.setButtonProperties(valueButton, buttonSize);
    this.setButtonProperties(lumaButton, buttonSize);
    this.setButtonProperties(intensityButton, buttonSize);
    this.setButtonProperties(hflipButton, buttonSize);
    this.setButtonProperties(vflipButton, buttonSize);
    this.setButtonProperties(colorCorrectionButton, buttonSize);
    this.setButtonProperties(compressButton, buttonSize);
    this.setButtonProperties(adjButton, buttonSize);

    this.addButton(redButton, gbc);
    this.addButton(greenButton, gbc);
    this.addButton(blueButton, gbc);
    this.addButton(vflipButton, gbc);
    this.addButton(hflipButton, gbc);
    this.addButton(blurButton, gbc);
    this.addButton(sharpenButton, gbc);
    this.addButton(lumaButton, gbc);
    this.addButton(valueButton, gbc);
    this.addButton(intensityButton, gbc);
    this.addButton(sepiaButton, gbc);
    this.addButton(sepiaButton, gbc);
    this.addButton(compressButton, gbc);
    this.addButton(colorCorrectionButton, gbc);
    this.addButton(blurButton, gbc);
    this.addButton(adjButton, gbc);

    TitledBorder b = BorderFactory.createTitledBorder("Operations");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    operationPanel.setBorder(b);
  }

  private void addButton(JButton button, GridBagConstraints gbc) {
    this.operationPanel.add(button, gbc);
    gbc.gridy++;
  }

  private void createImagePanel() {
    this.imagePanel = new JPanel(new BorderLayout());
    this.imagePanel.add(this.imageLabel, BorderLayout.CENTER);
    TitledBorder b = BorderFactory.createTitledBorder("Image");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    this.imagePanel.setBorder(b);
    this.initialImagePanel();
  }

  private void createHistogramPanel() {
    this.histogramPanel = new JPanel(new BorderLayout());
    this.histogramPanel.add(this.histogramLabel, BorderLayout.CENTER);
    TitledBorder b = BorderFactory.createTitledBorder("Histogram of Image");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    this.histogramPanel.setBorder(b);
  }

  private void setButtonProperties(JButton button, Dimension size) {
    button.setPreferredSize(size); // Set uniform button size
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setVerticalAlignment(SwingConstants.CENTER);// Horizontally center text
  }

  private void initialImagePanel() {
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
    hflipButton.addActionListener(e -> features.applyHorizontalFlip(this.imageName));
    vflipButton.addActionListener(e -> features.applyVerticalFlip(this.imageName));
    colorCorrectionButton.addActionListener(e -> features.applyColorCorrect(this.imageName));
    splitButton.addActionListener(e -> features.applySplit(this.textField.getText(),this.commandName,
    this.imageName));

  }



  @Override
  public void splitPreview(String commandName) {
    this.commandName = commandName;
    splitFrame = new JFrame("Split Preview");
    splitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    splitFrame.setResizable(true);

    int height = (int) (this.getHeight() * 0.75);
    int width = (int) (this.getWidth() * 0.75);
    splitFrame.setSize(width, height);

    JLabel image = new JLabel();
    image.setPreferredSize(new Dimension(500, 500));


    JLabel label = new JLabel("Enter a Percentage: ");
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    inputPanel.add(label);
    inputPanel.add(this.textField);
    inputPanel.add(this.splitButton);
    inputPanel.add(this.applyButton);

    // Set layout and add components to the main frame
    splitFrame.setLayout(new BorderLayout());
    splitFrame.add(inputPanel, BorderLayout.NORTH);
    splitFrame.add(image, BorderLayout.CENTER);

    // Initialize splitimagePanel for displaying split images
    splitimagePanel = new JPanel();
    splitimagePanel.setLayout(new BorderLayout());
    splitFrame.add(splitimagePanel, BorderLayout.SOUTH);

    splitFrame.pack();
    splitFrame.setVisible(true);
  }

  @Override
  public void showSplitImage(String name, BufferedImage image) {
    System.out.println(name);
    this.imageName = name;

    JLabel img = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(img);
    scrollPane.setPreferredSize(new Dimension(300, 300));

    this.splitimagePanel.removeAll();
    this.splitimagePanel.add(scrollPane, BorderLayout.CENTER);
    this.splitimagePanel.revalidate();
    this.splitimagePanel.repaint();
  }


  @Override
  public File getFilePath() {
    File f = null;
    JFileChooser select = new JFileChooser(".");
    select.setDialogTitle("Select an Image");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "File Extensions", "jpg", "jpeg", "png", "ppm"
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
    scrollPane.setPreferredSize(new Dimension(300, 300));
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
  public void showNumberError() {
    JOptionPane.showMessageDialog(splitFrame,
            "Valid percentage value required.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showHistogram(BufferedImage image) {
    JLabel histogram = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(histogram);
    scrollPane.setPreferredSize(new Dimension(270, 270));
    this.histogramPanel.removeAll();
    this.histogramPanel.add(scrollPane);
    this.histogramPanel.revalidate();
    this.histogramPanel.repaint();
  }

}
