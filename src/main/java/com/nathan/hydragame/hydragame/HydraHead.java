package com.nathan.hydragame.hydragame;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Objects;
import java.util.Random;

// inherits from ImageView class
public class HydraHead extends ImageView {

    // variables for each head
    private int headSize;
    private int currentGridX;
    private int currentGridY;

    private int imageSize;

    public HydraHead(Image image, int size, int imageSize)
    {
        // generate its ImageView from its super class

        super(image);
        this.imageSize = imageSize;
        headSize = size;
        this.setFitHeight(imageSize);
        this.setFitWidth(imageSize);
    }

    // getter methods for the attributes
    public int getHeadSize()
    {
        return headSize;
    }

    public int getGridX()
    {
        return currentGridX;
    }

    public int getGridY()
    {
        return currentGridY;
    }

    // put the HydraHead onto the girdPane
    public void putln(int gridX, int gridY, GridPane board)
    {
        this.currentGridX = gridX;
        this.currentGridY = gridY;
        board.add(this,gridX,gridY);
    }


}
