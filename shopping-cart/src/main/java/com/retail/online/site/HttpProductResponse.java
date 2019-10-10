/**
 * 
 */
package com.retail.online.site;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author haree
 *
 */
public class HttpProductResponse {
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}
	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	private String body;
	private String statusCode = "200";
	private Map<String, String> headers = new HashMap<>();
	/**
	 * 
	 */
	public HttpProductResponse() {
		super();
		this.headers.put("Content-Type", "application/json");
	}

	public HttpProductResponse(Cart cart) {
		this();
		Gson gson = new Gson();
		// this.headers.put("Content-Type", "application/json");
		this.body = gson.toJson(cart).toString();

	}

	public HttpProductResponse(Cart[] cart) {
		this();
		Gson gson = new Gson();
		this.body = gson.toJson(cart);

	}

}
