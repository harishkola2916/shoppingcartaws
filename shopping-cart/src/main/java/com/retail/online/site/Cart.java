/**
 * 
 */
package com.retail.online.site;

/**
 * @author haree
 *
 */
public class Cart {
	private int id;
	private String img;
	private String name;
	private String price;
	private int count;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	public Cart() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cart [id=" + id + ", img=" + img + ", name=" + name + ", price="
				+ price + ", count=" + count + "]";
	}
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}
	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @param id
	 * @param img
	 * @param name
	 * @param price
	 * @param count
	 */
	public Cart(int id, String img, String name, String price, int count) {
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.price = price;
		this.count = count;
	}

}
