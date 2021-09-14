package sample.Controller;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Muz extends Fruit
{
    public ImageView muzMain;
    public ImageView muz1;
    public ImageView muz2;

    public static double xCoord, yCoord;
    double radiusX, radiusY, X, Y;

    public static Timeline bnaAnimation;

    public Muz(AnchorPane anchorPane)
    {
        this.muzMain = new ImageView(getClass().getResource("../images/muz.png").toExternalForm());

        this.muzMain.setX(100);
        this.muzMain.setY(100);
        this.muzMain.setFitWidth(150);
        this.muzMain.setFitHeight(100);

        anchorPane.getChildren().add(this.muzMain);
    }

    public Muz(String filePath, double x, double y, AnchorPane anchorPane)
    {
        this.muzMain = new ImageView(filePath);

        this.muzMain.setX(x);
        this.muzMain.setX(y);
        this.muzMain.setFitWidth(150);
        this.muzMain.setFitHeight(100);

        anchorPane.getChildren().add(this.muzMain);
    }

    // Methods
    @Override
    public void slice(AnchorPane anchorPane)
    {
         muz1 = new ImageView(getClass().getResource("../images/muz2.png").toExternalForm());
        muz2 = new ImageView(getClass().getResource("../images/muz2.png").toExternalForm());

        muz1.setRotate(-167.5);
        muz2.setRotate(33.7);

        muz1.setLayoutX(this.muzMain.getLayoutX() + 20);
        muz1.setLayoutY(this.muzMain.getLayoutY() - 16);

        muz2.setLayoutX(this.muzMain.getLayoutX() - 14);
        muz2.setLayoutY(this.muzMain.getLayoutY() + 27);

        muz1.setFitWidth(100);
        muz1.setFitHeight(150);

        muz2.setFitWidth(100);
        muz2.setFitHeight(150);

        anchorPane.getChildren().add(muz1);
        anchorPane.getChildren().add(muz2);

        this.muzMain.setVisible(false);
        muz1.setVisible(true);
        muz2.setVisible(true);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.muz1, this.muz2);
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    @Override
    public void Kesilme(ImageView muz1, ImageView muz2)
    {
        muz1.setRotate(muz1.getRotate() + 1);
        muz2.setRotate(muz2.getRotate() + 1);

        muz1.setX(muz1.getX() + 1);
        muz1.setY(muz1.getY() + 1);

        muz2.setX(muz2.getX() - 1);
        muz2.setY(muz2.getY() + 1);
    }



    @Override
    public void splash(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());

        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.muzMain.getLayoutX() - 37);
        trace.setLayoutY(this.muzMain.getLayoutY());

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
        this.muzMain.setLayoutX(this.X);

        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= Muz.xCoord - this.radiusX && this.X < Muz.xCoord + this.radiusX)
            {
                this.X++;
                this.Y = Muz.yCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - Muz.xCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            // Banana
            this.muzMain.setLayoutX(this.X);
            this.muzMain.setLayoutY(this.Y);
            this.muzMain.setRotate(this.muzMain.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
        bnaAnimation = makeAnimation;
    }
}