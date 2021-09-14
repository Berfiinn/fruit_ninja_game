package sample.Controller;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Ananas extends Fruit
{
    public ImageView ananasImage;
    public ImageView ananasImage1;
    public ImageView ananasImage2;

    public static double xCoord, yCoord;
    double radiusX, radiusY, X, Y;

    public Ananas(AnchorPane anchorPane)
    {
        this.ananasImage = new ImageView(getClass().getResource("../images/ananasMain.png").toExternalForm());


        this.ananasImage.setX(100);
        this.ananasImage.setY(100);
        this.ananasImage.setFitWidth(200);
        this.ananasImage.setFitHeight(150);

        anchorPane.getChildren().add(this.ananasImage);
    }

    public Ananas(String filePath, double x, double y, AnchorPane anchorPane)
    {
        this.ananasImage = new ImageView(filePath);

        this.ananasImage.setX(x);
        this.ananasImage.setX(y);
        this.ananasImage.setFitWidth(200);
        this.ananasImage.setFitHeight(150);

        anchorPane.getChildren().add(this.ananasImage);
    }

    @Override
    public void slice(AnchorPane anchorPane)
    {
        ananasImage1 = new ImageView(getClass().getResource("../images/ananas.png").toExternalForm());
        ananasImage2 = new ImageView(getClass().getResource("../images/ananas2.png").toExternalForm());

        ananasImage1.setRotate(24.8);
        ananasImage2.setRotate(31);

        ananasImage1.setLayoutX(this.ananasImage.getLayoutX() - 13);
        ananasImage1.setLayoutY(this.ananasImage.getLayoutY() + 96);

        ananasImage2.setLayoutX(this.ananasImage.getLayoutX() + 20);
        ananasImage2.setLayoutY(this.ananasImage.getLayoutY() - 21);

        ananasImage1.setFitWidth(100);
        ananasImage1.setFitHeight(100);

        ananasImage2.setFitWidth(100);
        ananasImage2.setFitHeight(150);

        anchorPane.getChildren().add(ananasImage1);
        anchorPane.getChildren().add(ananasImage2);

        this.ananasImage.setVisible(false);
        ananasImage1.setVisible(true);
        ananasImage2.setVisible(true);


        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.ananasImage1, this.ananasImage2);
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    @Override
    public void Kesilme(ImageView ananasImage1, ImageView ananasImage2)
    {
        ananasImage1.setRotate(ananasImage1.getRotate() + 1);
        ananasImage2.setRotate(ananasImage2.getRotate() + 1);

        ananasImage1.setX(ananasImage1.getX() + 1);
        ananasImage1.setY(ananasImage1.getY() + 1);

        ananasImage2.setX(ananasImage2.getX() - 1);
        ananasImage2.setY(ananasImage2.getY() + 1);
    }

    @Override
    public void splash(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());

        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.ananasImage.getLayoutX() - 17);
        trace.setLayoutY(this.ananasImage.getLayoutY() + 21);

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
        this.ananasImage.setLayoutX(this.X);


        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= Ananas.xCoord - this.radiusX && this.X < Ananas.xCoord + this.radiusX)
            {
                this.X++;
                this.Y = Ananas.yCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - Ananas.yCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            // Pineapple
            this.ananasImage.setLayoutX(this.X);
            this.ananasImage.setLayoutY(this.Y);
            this.ananasImage.setRotate(this.ananasImage.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
    }
}