package sample.Controller;

import javafx.scene.layout.AnchorPane;
import sample.Interface.ISliceable;

abstract class  Bomb implements ISliceable {
    abstract public void explode(double startX, double startY, double endX, double endY, AnchorPane pane);
}
