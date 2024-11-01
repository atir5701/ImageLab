package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class ImageOperationsV2 extends ImageOperations implements OperationsV2 {

  @Override
  public boolean compressImage(String currentImageName, String newImageName, double percentage) {
    ImageModel imageOld = this.imageMap.get(currentImageName);
    ImageModel imageNew = this.getNewImageModel(imageOld);
    int height = imageOld.getHeight();
    int width = imageOld.getWidth();
    int paddingSize = this.padding(Math.max(height,width));
    double[][] red = new double[paddingSize][paddingSize];
    double[][] green = new double[paddingSize][paddingSize];
    double[][] blue = new double[paddingSize][paddingSize];
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        red[i][j] = imageOld.getPixelValue(i,j,0);
        green[i][j] = imageOld.getPixelValue(i,j,1);
        blue[i][j] = imageOld.getPixelValue(i,j,2);
      }
    }
    red=this.pipeline(red,paddingSize,percentage);
    green=this.pipeline(green,paddingSize,percentage);
    blue=this.pipeline(blue,paddingSize,percentage);

    this.combineChannel(imageNew,red,green,blue);

    this.imageMap.put(newImageName,imageNew);
    return true;
  }

  private void combineChannel(ImageModel imageNew, double[][] red, double[][] green, double[][] blue) {
    for(int i=0;i<imageNew.getHeight();i++){
      for(int j=0;j<imageNew.getWidth();j++){
        imageNew.setPixelValue(i,j,0,(int) red[i][j]);
        imageNew.setPixelValue(i,j,1,(int) green[i][j]);
        imageNew.setPixelValue(i,j,2,(int) blue[i][j]);
      }
    }
  }

  protected double[][] pipeline(double[][] matrix, int size, double percentage) {
    matrix = this.haar(matrix,size);
    matrix = this.compress(matrix,percentage);
    matrix = this.inverseHarr(matrix,size);
    return matrix;
  }

  protected double[][] inverseHarr(double[][] matrix, int size) {
    int c = 2;
    while(c<=size){
      matrix = this.inverseTransformColumn(matrix,c);
      matrix = this.inverseTransformRow(matrix,c);
      c = c*2;
    }
    return matrix;
  }

  protected double[][] inverseTransformRow(double[][] matrix, int c) {
    for(int i=0;i<matrix.length;i++){
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for(int j=0;j<c/2;j++){
        avg.add((matrix[i][j]+matrix[i][j+(c/2)])/ Math.sqrt(2.0));
        diff.add((matrix[i][j]-matrix[i][j+(c/2)]) / (Math.sqrt(2.0)));
      }
      for(int j=0;j<c;j=j+2){
        matrix[i][j] = avg.get(j/2);
        matrix[i][j+1] = diff.get(j/2);
      }
    }
    return matrix;
  }

  protected double[][] inverseTransformColumn(double[][] matrix, int c) {
    for(int i=0;i<matrix.length;i++){
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for(int j=0;j<c/2;j++){
        avg.add((matrix[j][i]+matrix[j+(c/2)][i])/ Math.sqrt(2.0));
        diff.add((matrix[j][i]-matrix[j+(c/2)][i]) / (Math.sqrt(2.0)));
      }
      for(int j=0;j<c;j=j+2){
        matrix[j][i] = avg.get(j/2);
        matrix[j+1][i] = diff.get(j/2);
      }
    }
    return matrix;
  }

  protected double[][] haar(double[][] matrix, int size) {
    int c = size;
    while(c>1){
      matrix = this.transformRow(matrix,c);
      matrix = this.transformColumn(matrix,c);
      c = c/2;
    }
    return matrix;
  }

  protected double[][] transformRow(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c; j = j + 2) {
        avg.add((matrix[i][j] + matrix[i][j + 1]) / (Math.sqrt(2.0)));
        diff.add((matrix[i][j] - matrix[i][j + 1]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < avg.size(); j++) {
        matrix[i][j] = avg.get(j);
        matrix[i][avg.size() + j] = diff.get(j);
      }
    }
    return matrix;
  }

  protected double[][] transformColumn(double[][] matrix, int c) {
    for(int i=0;i<matrix.length;i++){
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for(int j=0;j<c;j=j+2){
        avg.add((matrix[j][i]+matrix[j+1][i])/(Math.sqrt(2.0)));
        diff.add((matrix[j][i]-matrix[j+1][i])/(Math.sqrt(2.0)));
      }
      for(int j=0;j<avg.size();j++){
        matrix[j][i] = ((avg.get(j)));
        matrix[j+avg.size()][i] = (diff.get(j));
      }
    }
    return matrix;
  }

  protected double[][] compress(double[][] matrix, double percentage) {
    double threshold = Double.MAX_VALUE;
    if(percentage == 100.0){
      return applyThreshold(matrix,threshold);
    }
    List<Double> value = new ArrayList<>();
    for (double[] doubles : matrix) {
      for (int j = 0; j < matrix.length; j++) {
        double num = Math.round(doubles[j] * 1000.0) / 1000.0;
        value.add(Math.abs(num));
      }
    }
    List<Double> distinctPixel = new ArrayList<>(new HashSet<>(value));
    Collections.sort(distinctPixel);
    int index = (int) Math.round((distinctPixel.size()*(percentage/100.0)));
    threshold = distinctPixel.get(index);
    return applyThreshold(matrix,threshold);
  }

  protected double[][] applyThreshold(double[][] matrix, double threshold) {
    for(int i=0;i<matrix.length;i++){
      for(int j=0;j<matrix.length;j++){
        if(Math.abs(matrix[i][j])<threshold){
          matrix[i][j] = 0.0;
        }
      }
    }
    return matrix;
  }

  protected int padding(int value) {
    int size =1;
    while(size<value){
      size = size*2;
    }
    return size;
  }

  @Override
  public boolean histogram(String currentImageName,
                           String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = new ImageModel(256,256);

    int[][][] matrix = oldImage.getPixelMatrix();
    this.setBackground(newImage);
    int[] red = this.getFrequency(matrix,0);
    int[] green = this.getFrequency(matrix,1);
    int[] blue = this.getFrequency(matrix,2);

    int redPeak = Arrays.stream(red).max().getAsInt();
    int greenPeak =  Arrays.stream(green).max().getAsInt();
    int bluePeak =  Arrays.stream(blue).max().getAsInt();

    int redMin = Arrays.stream(red).min().getAsInt();
    int greenMin =  Arrays.stream(green).min().getAsInt();
    int blueMin = Arrays.stream(blue).min().getAsInt();

    int max_value = Math.max(Math.max(redPeak, greenPeak), bluePeak);
    int min_value = Math.min(Math.min(redMin, greenMin), blueMin);

    red = this.applyNormalize(red,max_value,min_value);
    green = this.applyNormalize(green,max_value,min_value);
    blue = this.applyNormalize(blue,max_value,min_value);

    this.connectPeak(newImage,red,green,blue);
    this.imageMap.put(newImageName,newImage);
    return true;
  }

  private void setBackground(ImageModel img) {
    for(int i=0;i<img.getHeight();i++){
      for(int j=0;j<img.getWidth();j++){
        if(i%(17)==0 || j%(17)==0){
          img.setPixelValue(i,j,0,200);
          img.setPixelValue(i,j,1,200);
          img.setPixelValue(i,j,2,200);
        }else{
          img.setPixelValue(i,j,0,255);
          img.setPixelValue(i,j,1,255);
          img.setPixelValue(i,j,2,255);
        }
      }
    }
  }

  protected int[] getFrequency(int[][][] matrix,int channel) {
    int[] n = new int[256];
    for(int i=0;i<matrix.length;i++){
      for(int j=0;j<matrix[0].length;j++){
        n[matrix[i][j][channel]]++;
      }
    }
    return n;
  }

  protected int[] applyNormalize(int[] arr,int max_value,
                                int min_value){
    for(int i=0;i<arr.length;i++){
      arr[i] = Math.round((float) ((arr[i]-min_value) * 255) /(max_value-min_value));
    }
    return arr;
  }

  private void connectPeak(ImageModel m,int[] red,
                             int[] green,int[] blue){
    int prevRed = 255 - red[0];
    int prevGreen = 255 - green[0];
    int prevBlue = 255 - blue[0];
    for(int j=1;j<256;j++){
      int currentRed = 255 - red[j];
      int currentGreen = 255 - green[j];
      int currentBlue = 255 - blue[j];
      this.drawLine(prevRed,currentRed,m,j,new int[] {255,0,0});
      this.drawLine(prevGreen,currentGreen,m,j,new int[] {0,255,0});
      this.drawLine(prevBlue,currentBlue,m,j,new int[] {0,0,255});
      prevRed = currentRed;
      prevGreen = currentGreen;
      prevBlue = currentBlue;
    }
  }

  private void drawLine(int start,int end,ImageModel m,
                          int index,int[] rgb){
    if(start<end){
      for(int i=start;i<=end;i++){
        m.setPixelValue(i,index,0,rgb[0]);
        m.setPixelValue(i,index,1,rgb[1]);
        m.setPixelValue(i,index,2,rgb[2]);
      }
    }else{
      for(int i=start;i>=end;i--){
        m.setPixelValue(i,index,0,rgb[0]);
        m.setPixelValue(i,index,1,rgb[1]);
        m.setPixelValue(i,index,2,rgb[2]);
      }
    }
  }

  @Override
  public boolean colorCorrection(String currentImageName,
                                 String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.getNewImageModel(oldImage);
    int[][][] matrix = oldImage.getPixelMatrix();
    int[] red = this.getFrequency(matrix,0);
    int[] green = this.getFrequency(matrix,1);
    int[] blue = this.getFrequency(matrix,2);
    int redPeak = this.getPeak(red);
    int greenPeak = this.getPeak(green);
    int bluePeak = this.getPeak(blue);
    int averagePeak = (redPeak+bluePeak+greenPeak)/3;
    this.setCorrectedValue(newImage,matrix,averagePeak,redPeak,greenPeak,bluePeak);
    this.imageMap.put(newImageName,newImage);
    return true;
  }

  protected int getPeak(int[] arr){
    int value = 0;
    int index = 0;
    for(int i=10;i<=245;i++){
      if(arr[i]>value){
        value = arr[i];
        index = i;
      }
    }
    return index;
  }

  private void setCorrectedValue(ImageModel m,int[][][] matrix,int avg,
                                   int red,int green,int blue){
    for(int i=0;i<m.getHeight();i++){
      for(int j=0;j<m.getWidth();j++){
        int red_value = matrix[i][j][0]+(avg-red);
        int green_value = matrix[i][j][1]+(avg-green);
        int blue_value = matrix[i][j][2]+(avg-blue);
        m.setPixelValue(i,j,0,red_value);
        m.setPixelValue(i,j,1,green_value);
        m.setPixelValue(i,j,2,blue_value);
      }
    }
  }

  @Override
  public boolean levelAdjustment(String currentImageName, String newImageName,
                                 int b,int m,int w) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.getNewImageModel(oldImage);
    double[] coeff = this.getCoefficient(b,m,w);
    this.applyAdjustment(newImage,oldImage,coeff);
    this.imageMap.put(newImageName,newImage);
    return true;
  }


  protected double[] getCoefficient(int b,int m,int w){
    int B = b*b;
    int M = m*m;
    int W = w*w;
    int A = B*(m-w)-b*(M-W)+w*m*(m-w);
    double Aa = -b*(128-255)+128*w-255*m;
    double Ab = B*(128-255)+255*M-128*W;
    double Ac = B*(255*m-128*w)-b*(255*M-128*W);
    return new double[] {Aa/A,Ab/A,Ac/A};
  }

  private void applyAdjustment(ImageModel n,ImageModel o,double[] coeff){
    for(int i=0;i<n.getHeight();i++){
      for(int j=0;j<n.getWidth();j++){
        for(int k=0;k<=2;k++){
          int x = o.getPixelValue(i,j,k);
          int y = (int) (coeff[0]*x*x+coeff[1]*x+coeff[2]);
          n.setPixelValue(i,j,k,y);
        }
      }
    }
  }

  @Override
  public void splitPreview(String currentImageName, String newImageName, double percentage) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    int new_height = oldImage.getHeight();
    int new_width = (int) (oldImage.getWidth()*(percentage/100.0));
    ImageModel newImage = new ImageModel(new_height,new_width);
    for(int i=0;i<new_height;i++){
      for(int j=0;j<new_width;j++){
        for(int k=0;k<3;k++){
          newImage.setPixelValue(i,j,k ,oldImage.getPixelValue(i,j,k));
        }
      }
    }
    this.imageMap.put(newImageName,newImage);
  }

   public boolean regain(String currentImageName,String newImageName){
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.imageMap.get(newImageName);
    ImageModel finalImage = this.getNewImageModel(oldImage);
    int width = newImage.getWidth();
    for(int i=0;i<oldImage.getHeight();i++){
      for(int j=0;j<oldImage.getWidth();j++){
        for(int k=0;k<=2;k++){
          if(j<width) {
            finalImage.setPixelValue(i, j, k, newImage.getPixelValue(i, j, k));
          }else{
            finalImage.setPixelValue(i, j, k, oldImage.getPixelValue(i, j, k));
          }
        }
      }
    }
    this.imageMap.put(newImageName,finalImage);
    return true;
  }

}
