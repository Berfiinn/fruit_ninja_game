package sample.Controller;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Kivi extends Fruit
{
    public ImageView KiviMain;
    public ImageView Kivi1;
    public ImageView Kivi2;

    double radiusX, radiusY, X, Y;
    public static double xCoord, yCoord;

    public Kivi(AnchorPane anchorPane)
    {
        this.KiviMain = new ImageView(getClass().getResource("../images/kiviMain.png").toExternalForm());


        this.KiviMain.setX(100);
        this.KiviMain.setY(100);
        this.KiviMain.setFitWidth(82);
        this.KiviMain.setFitHeight(89);

        anchorPane.getChildren().add(this.KiviMain);
    }

    public Kivi(String filePath, double x, double y, AnchorPane anchorPane)
    {
        this.KiviMain = new ImageView(filePath);

        this.KiviMain.setX(x);
        this.KiviMain.setX(y);
        this.KiviMain.setFitWidth(82);
        this.KiviMain.setFitHeight(89);

        anchorPane.getChildren().add(this.KiviMain);
    }

    @Override
    public void slice(AnchorPane anchorPane)
    {
        this.Kivi1 = new ImageView(getClass().getResource("../images/kivi.png").toExternalForm());

        this.Kivi2 = new ImageView(getClass().getResource("../images/kivi.png").toExternalForm());

        this.Kivi1.setRotate(22);
        this.Kivi2.setRotate(25);

        this.Kivi1.setLayoutX(this.KiviMain.getLayoutX() - 2);
        this.Kivi1.setLayoutY(this.KiviMain.getLayoutY() + 38);

        this.Kivi2.setLayoutX(this.KiviMain.getLayoutX() - 5);
        this.Kivi2.setLayoutY(this.KiviMain.getLayoutY() - 18);

        this.Kivi1.setFitWidth(92);
        this.Kivi1.setFitHeight(76);

        this.Kivi2.setFitWidth(92);
        this.Kivi2.setFitHeight(76);

        anchorPane.getChildren().add(this.Kivi1);
        anchorPane.getChildren().add(this.Kivi2);

        this.KiviMain.setVisible(false);
        this.Kivi1.setVisible(true);
        this.Kivi2.setVisible(true);


        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.Kivi1, this.Kivi2);
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    @Override
    public void Kesilme(ImageView imgHalfOrange1, ImageView imgHalfOrange2)
    {
        this.Kivi1.setRotate(Kivi1.getRotate() + 1);
        this.Kivi2.setRotate(Kivi2.getRotate() + 1);

        this.Kivi1.setX(Kivi1.getX() + 1);
        this.Kivi1.setY(Kivi1.getY() + 1);

        this.Kivi2.setX(Kivi2.getX() - 1);
        this.Kivi2.setY(Kivi2.getY() + 1);
    }


    @Override
    public void splash(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());


        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.KiviMain.getLayoutX() - 28);
        trace.setLayoutY(this.KiviMain.getLayoutY() - 25);

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
        this.KiviMain.setLayoutX(this.X);


        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= Kivi.xCoord - this.radiusX && this.X < Kivi.yCoord + this.radiusX)
            {
                this.X++;
                this.Y = Kivi.xCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - Kivi.xCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            // Kiwi
            this.KiviMain.setLayoutX(this.X);
            this.KiviMain.setLayoutY(this.Y);
            this.KiviMain.setRotate(this.KiviMain.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
    }
}