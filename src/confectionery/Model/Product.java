package confectionery.Model;

public class Product {

    int idProduct;
    String name;
    float price;
    float weight;
    ExpirationDate expirationDate;
     public Product(int idProduct, String name, float price, float weight, ExpirationDate expirationDate) {
         this.idProduct = idProduct;
         this.name = name;
         this.price = price;
         this.weight = weight;
         this.expirationDate = expirationDate;

    }

    public void viewExpirationDate(){
         System.out.print("Expiration Date :");
        System.out.print(expirationDate.day+".");
        System.out.print(expirationDate.month+".");
       System.out.println(expirationDate.year+".");
    }
    public void getPrice(){
        System.out.println("Price: " +price);
    }
    public void getWeight(){
       System.out.println("Weight: " +weight);
    }

    public void getName() {
        System.out.println("Name: "+name);
    }
}
