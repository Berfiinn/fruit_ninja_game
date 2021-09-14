package sample.Controller;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Portakal extends Fruit
{
    public ImageView portakalMain;
    public ImageView portakal1;
    public ImageView portakal2;

    public static double xCoord, yCoord;
    double radiusX, radiusY, X, Y;

    public Portakal(AnchorPane anchorPane)
    {
        this.portakalMain = new ImageView(getClass().getResource("../images/portakal.png").toExternalForm());

        this.portakalMain.setX(100);
        this.portakalMain.setY(100);
        this.portakalMain.setFitWidth(150);
        this.portakalMain.setFitHeight(100);

        anchorPane.getChildren().add(this.portakalMain);
    }

    public Portakal(String filePath, double x, double y, AnchorPane anchorPane)
    {
        this.portakalMain = new ImageView(filePath);

        this.portakalMain.setX(x);
        this.portakalMain.setX(y);
        this.portakalMain.setFitWidth(150);
        this.portakalMain.setFitHeight(100);

        anchorPane.getChildren().add(this.portakalMain);
    }

    @Override
    public void slice(AnchorPane anchorPane)
    {
        portakal1 = new ImageView(getClass().getResource("../images/portakal2.png").toExternalForm());
        portakal2 = new ImageView(getClass().getResource("../images/portakal2.png").toExternalForm());

        portakal1.setRotate(-82.9);
        portakal2.setRotate(97.1);

        portakal1.setLayoutX(this.portakalMain.getLayoutX() - 38);
        portakal1.setLayoutY(this.portakalMain.getLayoutY() + 8);

        portakal2.setLayoutX(this.portakalMain.getLayoutX() + 21);
        portakal2.setLayoutY(this.portakalMain.getLayoutY() + 11);

        portakal1.setFitWidth(120);
        portakal1.setFitHeight(100);

        portakal2.setFitWidth(120);
        portakal2.setFitHeight(100);

        anchorPane.getChildren().add(portakal1);
        anchorPane.getChildren().add(portakal2);

        this.portakalMain.setVisible(false);
        portakal1.setVisible(true);
        portakal2.setVisible(true);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.portakal1, this.portakal2);
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    @Override
    public void Kesilme(ImageView portakal1, ImageView portakal2)
    {
        portakal1.setRotate(portakal1.getRotate() + 1);
        portakal2.setRotate(portakal2.getRotate() + 1);

        portakal1.setX(portakal1.getX() + 1);
        portakal1.setY(portakal1.getY() + 1);

        portakal2.setX(portakal2.getX() - 1);
        portakal2.setY(portakal2.getY() + 1);
    }


    @Override
    public void launchMeyve(AnchorPane pane)
    {
        this.radiusX = ((pane.getPrefWidth() / 2) - 100) * ThreadLocalRandom.current().nextDouble(0.3, 1.0);
        this.radiusY = (pane.getPrefHeight() - 136) * ThreadLocalRandom.current().nextDouble(0.4, 1.0);

        this.X = pane.getPrefWidth()/2 - this.radiusX;
        this.portakalMain.setLayoutX(this.X);


        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= Portakal.xCoord - this.radiusX && this.X < Portakal.xCoord + this.radiusX)
            {
                this.X++;
                this.Y = Portakal.yCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - Portakal.xCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            // Orange
            this.portakalMain.setLayoutX(this.X);
            this.portakalMain.setLayoutY(this.Y);
            this.portakalMain.setRotate(this.portakalMain.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
    }

    @Override
    public void splash(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());


        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.portakalMain.getLayoutX() - 33);
        trace.setLayoutY(this.portakalMain.getLayoutY() - 16);

        pane.getChildren().add(trace);

        Timeline showSlash = new Timeline(
                new KeyFrame(Duration.ZERO, e ->
                {
                    trace.setVisible(true);
                }
                ),
                new KeyFrame(Duration.millis(300), e->
                {
                    trace.setVisible(false);
                }
                )
        );

        showSlash.setCycleCount(1);
        showSlash.play();
    }

}