package views;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class StoreButtonPane extends Pane {
    private Button resetButton, addButton, removeButton, completeSaleButton;

    public Button getResetButton() { return resetButton; }
    public Button getAddButton() { return addButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getCompleteSaleButton() { return completeSaleButton; }

    public StoreButtonPane(){
        Pane innerPane = new Pane();

        //create the buttons
        resetButton = new Button("Reset Store");
        resetButton.setStyle("-fx-font: 14 arial; -fx-base: rgb(240,240,240); -fx-text-fill: rgb(0,0,0);");
        resetButton.relocate(0,0);
        resetButton.setPrefSize(140,50);

        addButton = new Button("Add to Cart");
        addButton.setStyle("-fx-font: 14 arial; -fx-base: rgb(240,240,240); -fx-text-fill: rgb(0,0,0);");
        addButton.relocate(255,0);
        addButton.setPrefSize(140,50);
        addButton.setDisable(true);

        removeButton = new Button("Remove from Cart");
        removeButton.setStyle("-fx-font: 14 arial; -fx-base: rgb(240,240,240); -fx-text-fill: rgb(0,0,0);");
        removeButton.relocate(470,0);
        removeButton.setPrefSize(140,50);
        removeButton.setDisable(true);

        completeSaleButton = new Button("Complete Sale");
        completeSaleButton.setStyle("-fx-font: 14 arial; -fx-base: rgb(240,240,240); -fx-text-fill: rgb(0,0,0);");
        completeSaleButton.relocate(610,0);
        completeSaleButton.setPrefSize(140,50);
        completeSaleButton.setDisable(true);

        //add all buttons to pane
        innerPane.getChildren().addAll(resetButton,addButton,removeButton,completeSaleButton);

        getChildren().addAll(innerPane);
    }
}
