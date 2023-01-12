package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.ElectronicStore;
import models.Product;
import views.ElectronicStoreView;
import views.StoreButtonPane;

import java.util.ArrayList;

public class ElectronicStoreApp extends Application {
     ElectronicStore model;
     ElectronicStoreView view;

    public void start(Stage primaryStage) {
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        view.getButtonPane().getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {handleReset(primaryStage);}
        });

        view.getButtonPane().getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try {
                    handleAdd();
                } catch (Exception e) {
                    System.out.println("Unable to add to cart");
                }
            }
        });

        view.getButtonPane().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try {
                    handleRemove();
                } catch (Exception e) {
                    System.out.println("Cannot remove null item");
                }
            }
        });

        view.getButtonPane().getCompleteSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {handleSale();}
        });

        view.getStockList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                handleStockSelection();
            }
        });

        view.getCartList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {handleCartSelection();}
        });

        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view, 800,400));
        primaryStage.show();
        view.updateAll();
    }

    public static void main(String[] args) {launch(args);}

    private void handleCartSelection() {
        view.updateAll();
    }

    private void handleStockSelection() {
        view.updateAll();
    }

    private void handleSale() {
        model.completeSale();
        view.updateAll();
    }

    private void handleRemove() throws Exception{
        int selectedIndex = view.getCartList().getSelectionModel().getSelectedIndex();

        Product productSelected = model.getViewCart().get(selectedIndex);
        model.removeFromCart(productSelected);

        view.updateAll();
    }

    private void handleAdd() throws Exception {
        Product productSelected = view.getStockList().getSelectionModel().getSelectedItem();
        int selected = view.getStockList().getSelectionModel().getSelectedIndex();

        model.addToCart(productSelected, selected);
        view.getButtonPane().getCompleteSaleButton().setDisable(false);
        view.updateAll();
    }

    private void handleReset(Stage stage) {
        start(stage);
        System.out.println("Resetting the store");

    }



}
