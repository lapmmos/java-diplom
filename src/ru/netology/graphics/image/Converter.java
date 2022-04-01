package ru.netology.graphics.image;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Converter implements TextGraphicsConverter{
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;
    private int newWidth;
    private int newHeight;

    public Converter() {
        schema = new ColorSchema();
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        maximumRatio(img);
        resizeImage(img);
        char[][] array = new char[newHeight][newWidth];
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        var bwRaster = bwImg.getRaster();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                array[h][w] = c;
            }
        }

        String k = "";
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                k = k + array[i][j] + array[i][j];
            }
            k = k + "\n";
        }
        return k;
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    private void maximumRatio(BufferedImage img) throws BadImageSizeException {
        double ratio;
        if (img.getWidth() / img.getHeight() > img.getHeight() / img.getWidth()) {
            ratio = (double) img.getWidth() / (double) img.getHeight();
        } else {
            ratio = (double) img.getHeight() / (double) img.getWidth();
        } if (ratio > maxRatio && maxRatio != 0) throw new BadImageSizeException(ratio, maxRatio);
    }

    private void resizeImage(BufferedImage img) {
        double cW = 0;
        double cH = 0;
        if (img.getWidth() > width || img.getHeight() > height) {
            if (width != 0) {
                cW = img.getWidth() / width;
            } else cW = 1;
            if (height != 0) {
                cH = img.getHeight() / height;
            } else cH = 1;
            double maxCoeff = Math.max(cW, cH);
            newWidth = (int) (img.getWidth() / maxCoeff);
            newHeight = (int) (img.getHeight() / maxCoeff);
        } else {
            newWidth = img.getWidth();
            newHeight = img.getHeight();
        }
    }
}
