package com.nathan.hydragame.hydragame;

import javafx.scene.image.Image;

import java.util.Random;

public class HydraHeadsFactory {

    // image objects, static so there is only one made
    private static Image headSize1 = new Image(HydraHeadsFactory.class.getResource("HeadSize1.png").toExternalForm());
    private static Image headSize2 = new Image(HydraHeadsFactory.class.getResource("HeadSize2.png").toExternalForm());
    private static Image headSize3 = new Image(HydraHeadsFactory.class.getResource("HeadSize3.png").toExternalForm());
    private static Image headSize4 = new Image(HydraHeadsFactory.class.getResource("HeadSize4.png").toExternalForm());
    private static Image headSize5 = new Image(HydraHeadsFactory.class.getResource("HeadSize5.png").toExternalForm());
    private static Image headSize6 = new Image(HydraHeadsFactory.class.getResource("HeadSize6.png").toExternalForm());
    private static Image rareOuda = new Image(HydraHeadsFactory.class.getResource("ouda.png").toExternalForm());

    // private empty construcor
    private HydraHeadsFactory()
    {

    }

    // get head method to create a new HydraHead object
    public static HydraHead getHead(int size)
    {
        // new image variable
        Image image;
        Random rand = new Random();
        int num = rand.nextInt(20);
        if(num == 0)
        {
            image = rareOuda;

            return new HydraHead(image,size);
        }

        // get the size of the hydraHead
        switch (size)
        {
            case 1:
                image = headSize1;
                break;
            case 2:
                image = headSize2;
                break;
            case 3:
                image = headSize3;
                break;
            case 4:
                image = headSize4;
                break;
            case 5:
                image = headSize5;
                break;
            case 6:
                image = headSize6;
                break;
            default: image = headSize1;
        }

        // create a new hydraHead with that size and return it
        HydraHead head = new HydraHead(image,size);

        return head;
    }

}
