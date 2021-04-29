package com.example.project3;

/**
 * This class represents a single item that can be added and or
 * removed from the database
 */
public class Item {

    private String name;
    private String type = null;
    private String brand;
    private String condition = null;
    private String quantity;
    private String price;
    private String color;
    private String comments;
	
	/**
	* param name, type, brand, condition, quantity, price, color, comments 
	*
	**/

	public Item(){}

	public Item(String name, String brand, String quantity){
	    this.name = name;
	    this.brand = brand;
	    this.quantity = quantity;
    }

    public Item(String name, String brand, String condition, String quantity, String price, String color, String comments){
        this.name = name;
        this.brand = brand;
        this.condition = condition;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.comments = comments;
    }


    public Item(String name, String type, String brand, String condition, String quantity, String price, String color, String comments) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.condition = condition;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.comments = comments;
    }
	/** 
	* @return name
	*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	/** 
	* @return type
	*/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
	/** 
	* @return brand
	*/
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        brand = brand;
    }
	/** 
	* @return condition
	*/
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
	/** 
	* @return quantity
	*/
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
	/** 
	* @return price
	*/
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
	/** 
	* @return color
	*/
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
	/** 
	* @return comments
	*/
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
