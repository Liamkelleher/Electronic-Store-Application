package views;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.ElectronicStore;
import models.Product;

public class ElectronicStoreView extends Pane {
    private ListView<Product> stockList;
    private ListView<String> cartList;
    private ListView<String> popularItemsList;
    private TextField salesList, revList, avgSaleList;
    private StoreButtonPane buttonPane;
    private Label cartHeader ;
    ElectronicStore model;


    public ListView<Product> getStockList(){return stockList;}
    public void setStockList( ListView<Product> stockList){this.stockList= stockList;}

    public ListView<String> getCartList() {return cartList;}
    public void setCartList(ListView<String> cartList){this.cartList = cartList;}

    public ListView<String> getPopItemsList() {return popularItemsList;}
    public void setPopItemsList(ListView<String> popItemsList){this.popularItemsList = popItemsList;}

    public TextField getSalesList() {return salesList;}
    public void setSalesList(TextField salesList){this.salesList = salesList;}

    public TextField getRevList() {return revList;}
    public void setRevList(TextField revList){this.revList = revList;}

    public TextField getAvgSaleList() {return avgSaleList;}
    public void setAvgSaleList(TextField avgSaleList){this.avgSaleList=avgSaleList;}

    public StoreButtonPane getButtonPane(){return buttonPane;}


    public ElectronicStoreView(ElectronicStore model){
        this.model=model;
        // Left third of window:
        Label summaryHeader = new Label("Store Summary:");
        summaryHeader.setStyle("-fx-font: 15 arial;");
        summaryHeader.setAlignment(Pos.CENTER);
        summaryHeader.relocate(10,10);
        summaryHeader.setPrefSize(200,20);

        Label numOfSalesLabel = new Label("# of Sales:");
        numOfSalesLabel.setStyle("-fx-font: 15 arial;");
        numOfSalesLabel.setAlignment(Pos.CENTER_RIGHT);
        numOfSalesLabel.relocate(20,40);
        numOfSalesLabel.setPrefSize( 80,10);

        salesList=new TextField("");
        salesList.relocate(110,40);
        salesList.setPrefSize(100,25);
        salesList.setEditable(false);
        salesList.setText("0");

        Label revLabel = new Label("Revenue:");
        revLabel.setStyle("-fx-font: 15 arial;");
        revLabel.setAlignment(Pos.CENTER_RIGHT);
        revLabel.relocate(20,70);
        revLabel.setPrefSize(80,10);

        revList=new TextField("");
        revList.relocate(110,70);
        revList.setPrefSize(100,25);
        revList.setEditable(false);
        revList.setText("0.00");

        Label avgSalesLabel = new Label("$/Sale:");
        avgSalesLabel.setStyle("-fx-font: 15 arial;");
        avgSalesLabel.setAlignment(Pos.CENTER_RIGHT);
        avgSalesLabel.relocate(20,100);
        avgSalesLabel.setPrefSize(80,10);

        avgSaleList=new TextField("");
        avgSaleList.relocate(110,100);
        avgSaleList.setPrefSize(100,25);
        avgSaleList.setEditable(false);
        avgSaleList.setText("N/A");

        Label popItemsHeader = new Label("Most Popular Items:");
        popItemsHeader.setStyle("-fx-font: 15 arial;");
        popItemsHeader.setAlignment(Pos.CENTER);
        popItemsHeader.relocate(10,130);
        popItemsHeader.setPrefSize(200,20);

        popularItemsList=new ListView<>();
        popularItemsList.relocate(10,160);
        popularItemsList.setPrefSize(200,160);


        //Middle third of window
        Label stockHeader = new Label("Store Stock:");
        stockHeader.setStyle("-fx-font: 15 arial;");
        stockHeader.setAlignment(Pos.CENTER);
        stockHeader.relocate(220,10);
        stockHeader.setPrefSize(280,20);

        stockList=new ListView<>();
        stockList.relocate(220,40);
        stockList.setPrefSize(280,280);


        //Right third of window
        cartHeader = new Label("Current Cart:");
        cartHeader.setStyle("-fx-font: 15 arial;");
        cartHeader.setAlignment(Pos.CENTER);
        cartHeader.relocate(510,10);
        cartHeader.setPrefSize(280,20);

        cartList=new ListView<>();
        cartList.relocate(510,40);
        cartList.setPrefSize(280,280);


        // button pane
        buttonPane = new StoreButtonPane();
        buttonPane.relocate(40,335);

        //add all components to pane
        getChildren().addAll(summaryHeader,stockHeader,numOfSalesLabel,revLabel,avgSalesLabel,cartHeader,
                popItemsHeader,popularItemsList,stockList,cartList,buttonPane,salesList,revList,avgSaleList);
    }

    public void updateAll(){
        // Update text fields
        cartHeader.setText("Current Cart ($" + String.format("%.2f", model.getCartValue()) + "):");
        salesList.setText(String.format("%d", model.getNumOfSales()));
        revList.setText(String.format("%.2f", model.getRevenue()));
        if (model.getAvgSale() >0){
            avgSaleList.setText(String.format("%.2f", model.getAvgSale()));
        }else{
            avgSaleList.setText("N/A");
        }


        //Update List Views
        stockList.setItems(FXCollections.observableArrayList(model.getProductsList()));

        cartList.setItems(FXCollections.observableArrayList(model.getStringCartList()));

        popularItemsList.setItems(FXCollections.observableArrayList(model.sortPopularItems()));


        //Update Buttons
        Object addSelect = getCartList().getSelectionModel().getSelectedItem();
        if(addSelect !=null){
            getButtonPane().getRemoveButton().setDisable(false);
        }else{
            getButtonPane().getRemoveButton().setDisable(true);
        }

        Object removeSelect = getStockList().getSelectionModel().getSelectedItem();
        if(removeSelect !=null){
           getButtonPane().getAddButton().setDisable(false);
        }else{
            getButtonPane().getAddButton().setDisable(true);
        }

        if (cartList.getItems().size() == 0) {
            buttonPane.getCompleteSaleButton().setDisable(true);
        } else {
            buttonPane.getCompleteSaleButton().setDisable(false);
        }



    }





}
