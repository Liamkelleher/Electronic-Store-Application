package models;
//Class representing an electronic store
//Has an array of products that represent the items the store can sell



import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private ArrayList<Product> currCart; // holds all products in cart
    private ArrayList<Product> productsSold; //holds all sold products
    private HashSet <Product> uniqueCart; // holds all non-duplicate products in cart
    private HashSet <Product> uniqueProd; // holds all non-duplicate products in cart
    private double cartValue;
    private int numOfSales;
    private double avgSale;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        currCart = new ArrayList<>();
        productsSold = new ArrayList<>();
        uniqueCart = new HashSet<>();
        uniqueProd = new HashSet<>();
        cartValue = 0.0;
        numOfSales = 0;
        avgSale = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getRevenue() {
        return revenue;
    }

    public double getCartValue() {
        return cartValue;
    }

    public int getNumOfSales() {
        return numOfSales;
    }

    public int getCount() {
        return curProducts;
    }

    public ArrayList<Product> getCurrCart() {return currCart;}

    public ArrayList<Product> getProductsSold(){return productsSold;}

    public Set<Product> getUniqueCart(){return uniqueCart;}

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            uniqueProd.add(newProduct);
            curProducts++;
            return true;
        }
        return false;
    }

    //Displays store stock in List view
    public Product[] getProductsList() {
        if (curProducts <= 0) {
            return new Product[0];
        }
        Product[] validStock = new Product[curProducts];
        for (int i = 0; i < validStock.length; i++) {
            if (stock[i] != null)
                validStock[i] = stock[i];
        }
        return validStock;
    }


    public ArrayList<Product> getViewCart(){
        ArrayList<Product> viewCart = new ArrayList<>();
        for(Product product : uniqueCart){
            if(viewCart.size() != uniqueCart.size()){
                viewCart.add(product);
            }
        }
        return viewCart;
    }


    // Adds selected product to the cart
    // if product has no items left, removeStock() method will make item not be displayed in store stock list view
    public void addToCart(Product productSelected, int selected) throws Exception {
        cartValue += productSelected.getPrice();
        currCart.add(productSelected);
        uniqueCart.add(productSelected);

        //If amount added to cart is less than total product amount in store
        if (productSelected.getStockQuantity() > getCartQuantity(productSelected)) {
            System.out.println("Adding item to cart");
        } else { //if amount of selected product in cart is equal to total product amount left in store, hide product from stock list view
            if (productSelected.getStockQuantity() == getCartQuantity(productSelected)) {
                hideFromStock(selected);
                System.out.println("Adding item to cart");
            }
        }
    }

    // removes display of product with no valid items left
    // Get the amount of products in the cart
    public int getCartQuantity(Product product) {
        int quantity = 0;
        //if product input matches that in cart, add 1 to quantity
        for (Product p : currCart) {
            if (p == product) {
                quantity++;
            }
        }
        return quantity;
    }

    public void removeFromCart(Product productSelected) {
        cartValue -= productSelected.getPrice();
        currCart.remove(productSelected);
        System.out.println("Removing item from cart");

        //if all items are removed from cart, remove the '0th'/null item
        if(getCartQuantity(productSelected) == 0){
            uniqueCart.remove(productSelected);
        }

        // if product is not in the stock list view anymore, add it back when it's removed from cart
        for (Product element : stock) {
            if (element == productSelected)
                return ;
        }
        addProduct(productSelected);
    }

    // if product has no items left, it will not be displayed in store stock list view
    private void hideFromStock(int selected) {
        // Create another array of size one less
        Product newArray = stock[curProducts - 1];
        stock[selected] = null;
        // Copy all elements except the one to be removed onto a new array
        for (int i = selected + 1; i < curProducts; i++) {
            stock[i - 1] = stock[i];
            stock[i] = newArray;
        }
        curProducts--;
    }

    public String[] getStringCartList() {
        //string view for cart display
        String[] stringCartList = new String[getUniqueCart().size()];
        int i = 0;
        for (Product p : getViewCart()) {
            stringCartList[i] = getCartQuantity(p) + " x " + p.toString();
            i++;
        }
        return stringCartList;
    }

    public void completeSale() {
        numOfSales += 1;
        cartValue = 0.0;
        System.out.println("Transaction Complete");
        //sells each product in cart, one at a time
        for (Product product : currCart) {
            revenue += product.sellUnits(1);
            uniqueCart.remove(product);
            //the product in cart is added to the sold products array
            if (!searchProductSold(product))
                getProductsSold().add(product);
        }
        currCart.clear();
    }

    //finds average value per sale
    public double getAvgSale(){
        avgSale = revenue / numOfSales;
        return avgSale;
    }

    //checks if product has previously been sold. Acts as a hash set in the form of array list
    public boolean searchProductSold(Product product) {
        for (Product p : getProductsSold()) {
            if (p == product)
                return true;
        }
        return false;
    }

    public String[] sortPopularItems(){ //uses insertion sort algorithm
        Product [] popularItemsList = uniqueProd.toArray(new Product[3]);

        int i = 0;
        //iterate through both arrays
        while (i < popularItemsList.length) {
            int j = i + 1;
            while(j < popularItemsList.length){
                //compare current element to the element 1 index to the right of itself
                if (popularItemsList[j].getSoldQuantity() > popularItemsList[i].getSoldQuantity()){
                    //if predecessor is greater than current element, swap their places
                    Product temp;
                    temp = popularItemsList[i];
                    popularItemsList[i] = popularItemsList[j];
                    popularItemsList[j] = temp;
                }j++;
            }i++;
        }

        //string array to display
        String[] sortedItemList = new String[3];
        for (int n = 0; n < 3;n ++){
            sortedItemList[n] = "(" + popularItemsList[n].getSoldQuantity() + ") " + popularItemsList[n].toString();
        }
        return  sortedItemList;
    }


    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

}