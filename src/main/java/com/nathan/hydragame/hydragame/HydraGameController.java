package com.nathan.hydragame.hydragame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HydraGameController {

    // GamePane for the hydraHeads
    @FXML
    private GridPane gamePane;

    @FXML
    private Label sliderLabel;

    @FXML
    // HBox to display the UI elements like buttons and sliders
    private VBox uiPane;

    @FXML
    private Button resetButton;

    @FXML
    private Label gameOverLabel;

    @FXML
    private Button playButton;

    @FXML
    private Slider headSizeSlider;

    @FXML
    private Label timerLabel;

    @FXML
    private Button randomHeadButton;

    // boolean value for if the game is going to move the heads around
    private boolean randonHeadBool = false;


    private int headSize;

    // array list to keep track of hydraHeads on screen
    private List<HydraHead> headList;

    private Random rand;

    // cut counter
    private int totalCuts = 0;

    private final int ROWS = 16;
    private final int COLS = 16;

    private AnimationTimer timer;

    private double maxTime = 6;
    private double t = 0;


    @FXML
    public void initialize()
    {
        // initialize the head list

        headList = new ArrayList<>();

        //headSizeSlider.setPrefWidth(100);
       // headSizeSlider.setPrefSize(100,100);
        headSizeSlider.setSnapToTicks(true);
        headSizeSlider.setValue(4);

        // create a new random to be used
        rand = new Random();

        // make the col and row constraints for the gridPane
        for(int i = 0;i<COLS;i++)
        {
            ColumnConstraints col = new ColumnConstraints();
            col.setPrefWidth(40);
            gamePane.getColumnConstraints().add(col);
        }

        for(int i = 0;i<ROWS;i++)
        {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(40);
            gamePane.getRowConstraints().add(row);
        }
    }

    // reset button logic
    @FXML
    public void reset(ActionEvent event)
    {
       resetGame();
    }

    @FXML
    public void setRandomHeadButton(ActionEvent event) {
        randonHeadBool = true;
    }

    public void resetGame()
    {
        // clear the list of heads
        randonHeadBool = false;
        timer.stop();
        headList.clear();
        playButton.setDisable(false);
        headSizeSlider.setDisable(false);
        totalCuts = 0;
        // clear the screen
        gamePane.getChildren().clear();
        // display the gameOverLabel
    }

    // play Button logic
    @FXML
    public void play(ActionEvent event)
    {

        // reset time
        maxTime = 6.0;
        // disable the slider and button
        playButton.setDisable(true);
        gameOverLabel.setVisible(false);
        headSizeSlider.setDisable(true);

        timerLabel.setVisible(true);
        timerLabel.setText("Time left: " + maxTime +"s");

        // get the initial head size
        headSize = (int) headSizeSlider.getValue();
        // add the inital head to game
        HydraHead initialHead = HydraHeadsFactory.getHead(headSize);

        addHeadToGame(initialHead);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        timer.start();

    }

    public void update() {
        t += 0.016;

        timerLabel.setText("Time left: " + maxTime + "s");

        if(t > 1.5) {
            maxTime -= 0.5;

            t = 0;

            if(randonHeadBool)
            {
                int randHead = rand.nextInt(headList.size());

                System.out.println("size : " + headList.size());

                // get that hydraHead at the index in the list
                HydraHead headToMove = headList.get(randHead);

                // first remove it from the game and the list so you dont have duplicate heads
                gamePane.getChildren().remove(headToMove);
                headList.remove(headToMove);

                // add it back to the game
                addHeadToGame(headToMove);
            }
        }

        // if timer gets to 0 reset the game
        if(maxTime == 0)
        {
            timer.stop();
            gameOver();
            resetGame();

        }
    }

    // logic for when you click on a head
    public void clickedHead(MouseEvent event)
    {

        // get the source object of the head you clicked on
        HydraHead headToRemove = (HydraHead) event.getSource();
        // increment cut count
        totalCuts++;

        // add time to the clock
        maxTime += 0.5;

        // if the head is greater than size one, it should spawn more heads
        if(headToRemove.getHeadSize() > 1)
        {
            // generate a randonm number 0 or 1 and add it to 2 so it either spawns 2 or 3 heads
            int moreHeads = rand.nextInt(5) + 2;

            // loop through to add more heads to game
            for(int i = 0;i<moreHeads;i++)
            {
                // create a new headObject with the headSize - 1
                HydraHead newHead = HydraHeadsFactory.getHead(headToRemove.getHeadSize() - 1);
                addHeadToGame(newHead);
            }
        }

        // remove the clicked on head from the array list
        headList.remove(headToRemove);
        // remove it from the gridPane
        gamePane.getChildren().remove(headToRemove);

        // check if the list is empty, if it is there is no more heads left so you won
        if(headList.isEmpty())
        {
            gameOver();
            // stop the animationTimer
            timer.stop();
        }
    }

    // adding a head to game
    public void addHeadToGame(HydraHead head)
    {
        // x and y cords for where the head will be added
        int x;
        int y;
        boolean positionFound;

        // Loop to find a non-overlapping position
        do {
            // get a random x and y within the gridPand
            x = rand.nextInt(COLS);
            y = rand.nextInt(ROWS);
            positionFound = true;

            // Check if any existing head is at the (x, y) position
            for (HydraHead h : headList) {
                if (h.getGridX() == x && h.getGridY() == y) {
                    positionFound = false;
                    break;
                }
            }
        } while (!positionFound);


        // set the on mouse click logic
        head.setOnMouseClicked(MouseEvent -> clickedHead(MouseEvent));

        // add the head to the array list
        headList.add(head);
        // add the head to the gridPane
        head.putln(x,y,gamePane);
        // set the on mouse clicked event logic
    }

    @FXML
    public void setHeadSizeSlider(MouseEvent event)
    {
        headSize = (int) headSizeSlider.getValue();
    }

    // logic for when you finish the game
    public void gameOver()
    {
        // display the total cuts
        gameOverLabel.setText("You Win! Total Cuts: " + totalCuts);
        gameOverLabel.setVisible(true);
        // enable the play button and slider again
        totalCuts = 0;
        playButton.setDisable(false);
        headSizeSlider.setDisable(false);

    }

}
