package entity;
// Generated Sep 11, 2014 2:17:32 AM by Hibernate Tools 3.6.0



/**
 * Details generated by hbm2java
 */
public class Details  implements java.io.Serializable {


     private Integer idDetail;
     private Purchases purchases;
     private Products products;
     private int amount;
     private int price;

    public Details() {
    }

    public Details(Purchases purchases, Products products, int amount, int price) {
       this.purchases = purchases;
       this.products = products;
       this.amount = amount;
       this.price = price;
    }
   
    public Integer getIdDetail() {
        return this.idDetail;
    }
    
    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }
    public Purchases getPurchases() {
        return this.purchases;
    }
    
    public void setPurchases(Purchases purchases) {
        this.purchases = purchases;
    }
    public Products getProducts() {
        return this.products;
    }
    
    public void setProducts(Products products) {
        this.products = products;
    }
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getPrice() {
        return this.price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }




}


