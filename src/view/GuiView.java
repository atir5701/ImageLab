package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.filechooser.FileFilter;

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
  private JButton cancelButton;
  private JButton adjButton;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private String imageName;
  private String commandName;
  private JPanel topPanel;
  private JPanel operationPanel;
  private JPanel imagePanel;
  private JPanel splitImagePanel;
  private JPanel histogramPanel;
  private JFrame splitFrame;
  private JTextField textField;
  private Font font;

  public GuiView(String caption) {
    this.setTitle(caption);
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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


    this.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        int choice = JOptionPane.showConfirmDialog(
                GuiView.this,
                "Are you sure you want to exit the application?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
          dispose(); // Close the main application window
        }
      }
    });

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
    cancelButton = new JButton("Cancel");
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
    this.loadButton.addActionListener(e -> features.loadImage());
    this.saveButton.addActionListener(e -> features.saveImage(this.imageName));
    this.blurButton.addActionListener(e -> features.applyBlur(this.imageName));
    this.sepiaButton.addActionListener(e -> features.applySepia(this.imageName));
    this.sharpenButton.addActionListener(e -> features.applySharp(this.imageName));
    this.redButton.addActionListener(e -> features.applyRed(this.imageName));
    this.greenButton.addActionListener(e -> features.applyGreen(this.imageName));
    this.blueButton.addActionListener(e -> features.applyBlue(this.imageName));
    this.valueButton.addActionListener(e -> features.applyValue(this.imageName));
    this.lumaButton.addActionListener(e -> features.applyLuma(this.imageName));
    this.intensityButton.addActionListener(e -> features.applyIntensity(this.imageName));
    this.hflipButton.addActionListener(e -> features.applyHorizontalFlip(this.imageName));
    this.vflipButton.addActionListener(e -> features.applyVerticalFlip(this.imageName));
    this.colorCorrectionButton.addActionListener(e -> features.applyColorCorrect(this.imageName));
    this.splitButton.addActionListener(e -> features.applySplit(this.textField.getText(),
            this.commandName, this.imageName));
    this.applyButton.addActionListener(e -> features.commandGenerator(this.commandName,
            this.imageName));
    this.applyButton.addActionListener(e -> {
      JOptionPane.showMessageDialog(splitFrame, "Filter applied successfully.",
              "Filter Applied", JOptionPane.INFORMATION_MESSAGE);
      splitFrame.dispose();
    });
    this.cancelButton.addActionListener(e -> splitFrame.dispose());
  }

  @Override
  public void splitPreview(String commandName) {
    this.commandName = commandName;
    splitFrame = new JFrame("Split Preview");
    splitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    splitFrame.setResizable(true);
    int height = (int) (this.getHeight() * 0.75);
    int width = (int) (this.getWidth() * 0.75);
    splitFrame.setSize(width, height);
    JLabel label = new JLabel("Enter a Percentage: ");
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    inputPanel.add(label);
    inputPanel.add(this.textField);
    inputPanel.add(this.splitButton);
    inputPanel.add(this.applyButton);
    inputPanel.add(this.cancelButton);
    splitFrame.setLayout(new BorderLayout());
    splitFrame.add(inputPanel, BorderLayout.NORTH);
    splitImagePanel = new JPanel();
    splitImagePanel.setLayout(new BorderLayout());
    JLabel placeholderLabel = new JLabel("Enter value and then press split " +
            "to get the preview of the operation.", JLabel.CENTER);
    splitImagePanel.add(placeholderLabel, BorderLayout.CENTER);
    splitImagePanel.setPreferredSize(new Dimension(500, 500));

    splitFrame.add(splitImagePanel, BorderLayout.CENTER);

    splitFrame.setLocationRelativeTo(this);
    splitFrame.pack();
    splitFrame.setVisible(true);
  }

  @Override
  public void showSplitImage(BufferedImage image) {
    JLabel imgLabel = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(imgLabel);
    scrollPane.setPreferredSize(new Dimension(500, 500));
    splitImagePanel.removeAll();
    splitImagePanel.add(scrollPane, BorderLayout.CENTER);
    splitImagePanel.revalidate();
    splitImagePanel.repaint();
  }

  @Override
  public void showSaveSuccess() {
    JOptionPane.showMessageDialog(
            this,
            "Image saved successful!",
            "Save Success",
            JOptionPane.INFORMATION_MESSAGE
    );
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
  public File getSaveFilePath() {
    String[] formats = {"PNG", "JPEG", "JPG", "PPM"};
    JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.setDialogTitle("Save Image As");
    fileChooser.setSelectedFile(new File(this.imageName));
    FileFilter defaultFilter = null;
    for (String format : formats) {
      FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.isDirectory() || f.getName().toLowerCase().endsWith("." + format.toLowerCase());
        }
        @Override
        public String getDescription() {
          return format + " (*." + format.toLowerCase() + ")";
        }
      };
      fileChooser.addChoosableFileFilter(filter);
      if (format.equals("JPG")) {
        defaultFilter = filter;
      }
    }
    if (defaultFilter != null) {
      fileChooser.setFileFilter(defaultFilter);
    }
    while (true) {
      int result = fileChooser.showSaveDialog(null);
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String selectedFormat = fileChooser.getFileFilter().
                getDescription().split(" ")[0].toLowerCase();
        if (selectedFormat.equals("all")) {
          JOptionPane.showMessageDialog(null, "Please select" +
                          " a valid image format (PNG, JPEG, JPG, PPM).",
                  "Invalid Format", JOptionPane.ERROR_MESSAGE);
          continue;
        }
        String fileName = file.getName().toLowerCase();
        if (!fileName.endsWith("." + selectedFormat)) {
          file = new File(file.getAbsolutePath() + "." + selectedFormat);
        }
        return file;
      } else {
        return null;
      }
    }
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
