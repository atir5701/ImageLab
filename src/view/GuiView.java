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
  private JButton previewButtonLevel;
  private JButton applyButtonLevel;
  private JButton cancelButtonLevel;
  private JButton splitButtonComp;
  private JButton applyButtonComp;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private String imageName;
  private String commandName;
  private JPanel topPanel;
  private JPanel operationPanel;
  private JPanel imagePanel;
  private JPanel splitImagePanel;
  private JPanel histogramPanel;
  private JDialog splitFrame;
  private JDialog leveladjFrame;
  private JTextField textField;
  private JTextField bField;
  private JTextField mField;
  private JTextField wField;
  private JTextField pField;
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
    previewButtonLevel = new JButton("Preview");
    applyButtonLevel = new JButton("Apply");
    cancelButtonLevel = new JButton("Cancel");
    splitButton = new JButton("Split");
    applyButton = new JButton("Apply");
    cancelButton = new JButton("Cancel");
    splitButtonComp = new JButton("Compress");
    applyButtonComp = new JButton("Apply");


    imageLabel = new JLabel();
    histogramLabel = new JLabel();
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
    this.operationPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);

    Dimension buttonSize = new Dimension(150, 40);
    JButton[] buttons = {blurButton, sepiaButton, sharpenButton, colorCorrectionButton, redButton, greenButton, blueButton,
            valueButton, lumaButton, intensityButton, hflipButton, vflipButton, compressButton, adjButton};

    for (JButton button : buttons) {
      setButtonProperties(button, buttonSize);
      addButton(button, gbc);
    }

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
    this.compressButton.addActionListener(e -> features.applyCompress(this.imageName));
    this.intensityButton.addActionListener(e -> features.applyIntensity(this.imageName));
    this.hflipButton.addActionListener(e -> features.applyHorizontalFlip(this.imageName));
    this.vflipButton.addActionListener(e -> features.applyVerticalFlip(this.imageName));
    this.colorCorrectionButton.addActionListener(e -> features.applyColorCorrect(this.imageName));
    this.adjButton.addActionListener(e -> features.applyLevelAdjust(this.imageName));

    this.splitButton.addActionListener(e -> features.applySplit(this.textField.getText(),
            this.commandName, this.imageName));
    this.applyButton.addActionListener(e -> features.commandGenerator(this.commandName,
            this.imageName));
    this.applyButton.addActionListener(e -> {
      JOptionPane.showMessageDialog(this, "Filter applied successfully.",
              "Filter Applied", JOptionPane.INFORMATION_MESSAGE);
      splitFrame.dispose();
    });
    this.cancelButton.addActionListener(e -> splitFrame.dispose());

    this.splitButtonComp.addActionListener(e -> features.doCompress(this.textField.getText(),
            this.imageName));
    this.applyButtonComp.addActionListener(e -> features.commandGenerator("compress " + this.textField.getText()
            , this.imageName));
    this.applyButtonComp.addActionListener(e -> {
      JOptionPane.showMessageDialog(this, "Filter applied successfully.",
              "Filter Applied", JOptionPane.INFORMATION_MESSAGE);
      splitFrame.dispose();
    });

    this.cancelButtonLevel.addActionListener(e -> leveladjFrame.dispose());
    this.previewButtonLevel.addActionListener(e -> features.getLevelAdjust(this.imageName,
            this.bField.getText(), this.mField.getText(), this.wField.getText(),
            this.pField.getText()));
    this.applyButtonLevel.addActionListener(e -> features.commandGenerator("levels-adjust " +
            this.bField.getText() + " " + this.mField.getText() + " " + this.wField.getText(), this.imageName));
    this.applyButtonLevel.addActionListener(e -> {
      JOptionPane.showMessageDialog(this, "Filter applied successfully.",
              "Filter Applied", JOptionPane.INFORMATION_MESSAGE);
      leveladjFrame.dispose();
    });


  }

  @Override
  public void splitPreview(String commandName) {
    this.commandName = commandName;
    splitFrame = new JDialog(this, "Preview " + commandName, true);
    splitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    splitFrame.setResizable(true);
    int height = (int) (this.getHeight() * 0.75);
    int width = (int) (this.getWidth() * 0.75);
    splitFrame.setSize(width, height);
    JLabel label = new JLabel("Enter a Percentage: ");
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    inputPanel.add(label);
    textField = new JTextField("50", 5);
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
  public void LevelAdjust() {
    this.leveladjFrame = new JDialog(this, "Levels Adjust Preview", true);
    this.leveladjFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    int height = (int) (this.getHeight() * 0.9);
    int width = (int) (this.getWidth() * 0.9);
    this.leveladjFrame.setSize(width, height);
    this.leveladjFrame.setLayout(new BorderLayout());
    this.bField = new JTextField(5);
    this.mField = new JTextField(5);
    this.wField = new JTextField(5);
    this.pField = new JTextField("100", 5);
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    inputPanel.add(new JLabel("Enter Black Value : "));
    inputPanel.add(this.bField);
    inputPanel.add(new JLabel("Enter Mid Value : "));
    inputPanel.add(this.mField);
    inputPanel.add(new JLabel("Enter White Value : "));
    inputPanel.add(this.wField);
    inputPanel.add(new JLabel("Percentage: "));
    inputPanel.add(this.pField);
    centerPanel.add(inputPanel);
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(this.previewButtonLevel);
    buttonPanel.add(this.applyButtonLevel);
    buttonPanel.add(this.cancelButtonLevel);
    centerPanel.add(buttonPanel);
    this.leveladjFrame.add(centerPanel, BorderLayout.CENTER);
    this.splitImagePanel = new JPanel();
    this.splitImagePanel.setLayout(new BorderLayout());
    JLabel placeholderLabel = new JLabel("Enter value and then press preview to get the preview of the operation.", JLabel.CENTER);
    this.splitImagePanel.add(placeholderLabel, BorderLayout.CENTER);
    this.splitImagePanel.setPreferredSize(new Dimension(500, 500));
    this.leveladjFrame.add(this.splitImagePanel, BorderLayout.SOUTH);
    this.leveladjFrame.setResizable(true);
    this.leveladjFrame.setLocationRelativeTo(this);
    this.leveladjFrame.pack();
    this.leveladjFrame.setVisible(true);
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
    select.setAcceptAllFileFilterUsed(false);
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
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setDialogTitle("Save Image As");
    fileChooser.setSelectedFile(new File(this.imageName));
    for (String format : formats) {
      fileChooser.addChoosableFileFilter(new FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.isDirectory() || f.getName().toLowerCase().endsWith("." + format.toLowerCase());
        }
        @Override
        public String getDescription() {
          return format + " (*." + format.toLowerCase() + ")";
        }
      });
      if (format.equals("JPEG")) {
        fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[fileChooser.getChoosableFileFilters().length - 1]);
      }
    }
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      String selectedFormat = fileChooser.getFileFilter().getDescription().split(" ")[0].toLowerCase();
      if (!file.getName().toLowerCase().endsWith("." + selectedFormat)) {
        file = new File(file.getAbsolutePath() + "." + selectedFormat);
      }
      return file;
    } else {
      return null;
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
    JOptionPane.showMessageDialog(this,
            "Provide valid value of percentage.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showNullValueError() {
    JOptionPane.showMessageDialog(leveladjFrame,
            "All values (B, M, and W) must be provided and all must be numeric value.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showInvalidRangeError() {
    JOptionPane.showMessageDialog(leveladjFrame,
            "All values (B, M, and W) must be between 0 to 255",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showOrderError() {
    JOptionPane.showMessageDialog(leveladjFrame,
            "Value of B,M and W must be in ascending order",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void compressImage() {
    splitFrame = new JDialog(this, "Compression", true);
    splitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    splitFrame.setResizable(true);
    int height = (int) (this.getHeight() * 0.75);
    int width = (int) (this.getWidth() * 0.75);
    splitFrame.setSize(width, height);
    JLabel label = new JLabel("Enter a Percentage: ");
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    inputPanel.add(label);
    textField = new JTextField("50", 5);
    inputPanel.add(this.textField);
    inputPanel.add(this.splitButtonComp);
    inputPanel.add(this.applyButtonComp);
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

