package sample.Controller;


import javafx.scene.layout.AnchorPane;
import sample.Interface.ISliceable;

abstract class BaseManager implements ISliceable {
    abstract public void splash(double startX, double startY, double endX, double endY, AnchorPane pane);

}
