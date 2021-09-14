package sample.Controller;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Karpuz extends Fruit
{
    public ImageView KarpuzImage;
    public ImageView KarpuzImage1;
    public ImageView KarpuzImage2;

    public static double xCoord, ycCoord;
    double radiusX, radiusY, X, Y;

    public Karpuz(AnchorPane anchorPane)
    {
        this.KarpuzImage = new ImageView(getClass().getResource("../images/karpuz.png").toExternalForm());


        this.KarpuzImage.setFitWidth(150);
        this.KarpuzImage.setFitHeight(100);

        anchorPane.getChildren().add(this.KarpuzImage);
    }

    public Karpuz(String filePath, double x, double y, double fitWidth, double fitHeight, AnchorPane anchorPane)
    {
        this.KarpuzImage = new ImageView(filePath);

        this.KarpuzImage.setLayoutX(x);
        this.KarpuzImage.setLayoutY(y);
        this.KarpuzImage.setFitWidth(fitWidth);
        this.KarpuzImage.setFitHeight(fitHeight);

        anchorPane.getChildren().add(this.KarpuzImage);
    }

    @Override
    public void slice(AnchorPane anchorPane)
    {
        KarpuzImage1 = new ImageView(getClass().getResource("../images/karpuz2.png").toExternalForm());
        KarpuzImage2 = new ImageView(getClass().getResource("../images/karpuz2.png").toExternalForm());

        KarpuzImage1.setRotate(-81.3);
        KarpuzImage2.setRotate(90);

        KarpuzImage1.setLayoutX(this.KarpuzImage.getLayoutX() + 44);
        KarpuzImage1.setLayoutY(this.KarpuzImage.getLayoutY() + 9);

        KarpuzImage2.setLayoutX(this.KarpuzImage.getLayoutX() - 26);
        KarpuzImage2.setLayoutY(this.KarpuzImage.getLayoutY() + 32);

        KarpuzImage1.setFitWidth(90);
        KarpuzImage1.setFitHeight(83);

        KarpuzImage2.setFitWidth(90);
        KarpuzImage2.setFitHeight(83);

        anchorPane.getChildren().add(KarpuzImage1);
        anchorPane.getChildren().add(KarpuzImage2);

        // Remove the watermelon and make the two halves of watermelon visible
        this.KarpuzImage.setVisible(false);
        KarpuzImage1.setVisible(true);
        KarpuzImage2.setVisible(true);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.KarpuzImage1, this.KarpuzImage2);
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    @Override
    public void Kesilme(ImageView karpuzImage1, ImageView karpuzImage2)
    {
        karpuzImage1.setRotate(karpuzImage1.getRotate() + 1);
        karpuzImage2.setRotate(karpuzImage2.getRotate() + 1);

        karpuzImage1.setX(karpuzImage1.getX() + 1);
        karpuzImage1.setY(karpuzImage1.getY() + 1);

        karpuzImage2.setX(karpuzImage2.getX() - 1);
        karpuzImage2.setY(karpuzImage2.getY() + 1);
    }


    public void splash(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());;

        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.KarpuzImage.getLayoutX() - 27);
        trace.setLayoutY(this.KarpuzImage.getLayoutY() - 27);

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

    @Override
    public void launchMeyve(AnchorPane pane)
    {
        this.radiusX = ((pane.getPrefWidth() / 2) - 100) * ThreadLocalRandom.current().nextDouble(0.3, 1.0);
        this.radiusY = (pane.getPrefHeight() - 136) * ThreadLocalRandom.current().nextDouble(0.4, 1.0);

        this.X = pane.getPrefWidth()/2 - this.radiusX;
        this.KarpuzImage.setLayoutX(this.X);


        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= Karpuz.xCoord - this.radiusX && this.X < Karpuz.xCoord + this.radiusX)
            {
                this.X++;
                this.Y = Karpuz.ycCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - Karpuz.xCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            // Watermelon
            this.KarpuzImage.setLayoutX(this.X);
            this.KarpuzImage.setLayoutY(this.Y);
            this.KarpuzImage.setRotate(this.KarpuzImage.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
    }
}