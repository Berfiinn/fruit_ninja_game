package sample.Controller;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Interface.ISliceable;

abstract class Fruit implements ISliceable {
    abstract public void splash(double startX, double startY, double endX, double endY, AnchorPane pane);
    abstract public void slice(AnchorPane anchorPane);
    abstract public void Kesilme(ImageView fruitImage1, ImageView fruitImage2);
    abstract public void launchMeyve(AnchorPane pane);
}
