package sample.Controller;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class BlackBomb extends Bomb
{
    public ImageView bombaMain;
    public ImageView bomba1;
    public ImageView bomba2;

    public static double xCoord, yCoord;
    double radiusX, radiusY, X, Y;

    public static Timeline bmbAnimation;

    public BlackBomb(AnchorPane anchorPane)
    {
        this.bombaMain = new ImageView(getClass().getResource("../images/bomba.gif").toExternalForm());

        this.bombaMain.setX(100);
        this.bombaMain.setY(100);
        this.bombaMain.setFitWidth(150);
        this.bombaMain.setFitHeight(100);

        anchorPane.getChildren().add(this.bombaMain);
    }

    public BlackBomb(String filePath, double x, double y, AnchorPane anchorPane)
    {
        this.bombaMain = new ImageView(filePath);

        this.bombaMain.setX(x);
        this.bombaMain.setX(y);
        this.bombaMain.setFitWidth(150);
        this.bombaMain.setFitHeight(100);

        anchorPane.getChildren().add(this.bombaMain);
    }

    @Override
    public void explode(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());

        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.bombaMain.getLayoutX() - 27);
        trace.setLayoutY(this.bombaMain.getLayoutY() - 27);

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
    public void slice(AnchorPane anchorPane)
    {
        bomba1 = new ImageView("../images/bomba.tif");
        bomba2 = new ImageView("../images/bomba.tif");

        bomba1.setRotate(-81.3);
        bomba2.setRotate(90);

        bomba1.setLayoutX(this.bombaMain.getLayoutX() + 44);
        bomba1.setLayoutY(this.bombaMain.getLayoutY() + 9);

        bomba2.setLayoutX(this.bombaMain.getLayoutX() - 26);
        bomba2.setLayoutY(this.bombaMain.getLayoutY() + 32);

        bomba1.setFitWidth(90);
        bomba1.setFitHeight(83);

        bomba2.setFitWidth(90);
        bomba2.setFitHeight(83);

        anchorPane.getChildren().add(bomba1);
        anchorPane.getChildren().add(bomba2);

        this.bombaMain.setVisible(false);
        bomba1.setVisible(true);
        bomba2.setVisible(true);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.bomba1, this.bomba2);

             }));

       animation.setCycleCount(Timeline.INDEFINITE);
       animation.play();
    }

    public void Kesilme(ImageView bomba1, ImageView bomba2)
    {
        bomba1.setRotate(bomba2.getRotate() + 1);
        bomba2.setRotate(bomba2.getRotate() + 1);

        bomba1.setX(bomba1.getX() + 1);
        bomba1.setY(bomba1.getY() + 1);

        bomba2.setX(bomba2.getX() - 1);
        bomba2.setY(bomba2.getY() + 1);
    }


    public void appearBomb(AnchorPane pane)
    {
        this.radiusX = ((pane.getPrefWidth() / 2) - 100) * ThreadLocalRandom.current().nextDouble(0.3, 1.0);
        this.radiusY = (pane.getPrefHeight() - 136) * ThreadLocalRandom.current().nextDouble(0.4, 1.0);

        this.X = pane.getPrefWidth()/2 - this.radiusX;
        this.bombaMain.setLayoutX(this.X);

        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= BlackBomb.xCoord - this.radiusX && this.X < BlackBomb.xCoord + this.radiusX)
            {
                this.X++;
                this.Y = BlackBomb.yCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - BlackBomb.xCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            this.bombaMain.setLayoutX(this.X);
            this.bombaMain.setLayoutY(this.Y);
            this.bombaMain.setRotate(this.bombaMain.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
        bmbAnimation = makeAnimation;
    }
}