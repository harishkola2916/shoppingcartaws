package com.retail.online.site;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

/**
 * @author haree
 *
 */
public class SimpleInsertCart extends CartS3Client
		implements
			RequestHandler<HttpRequest, HttpProductResponse> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.amazonaws.services.lambda.runtime.RequestHandler#handleRequest(java.
	 * lang.Object, com.amazonaws.services.lambda.runtime.Context)
	 */
	@Override
	public HttpProductResponse handleRequest(HttpRequest inputRequest,
			Context context) {
		context.getLogger().log("Input: " + inputRequest);

		// Reads the new cart from body from http request of web api
		String reqData = inputRequest.getBody();
		Gson gson = new Gson();
		Cart cart = gson.fromJson(reqData, Cart.class);

		// inserting new cart data into table in dynamodb
		initDynamoDbClient(cart);

		// Adding new cart to cart list
		List<Cart> cartList = getAllCartsList();
		cartList.add(cart);

		// Updating the cart list on s3 bucket
		if (updateAllCarts(cartList))
			return new HttpProductResponse();

		HttpProductResponse response = new HttpProductResponse();
		response.setStatusCode("500");
		return response;
	}

	/**
	 * Initializing the DynamoDB client and insert new cart object into table
	 * 
	 * @param cart
	 */
	private void initDynamoDbClient(Cart cart) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Movie");
		Item item = new Item().withPrimaryKey("id", cart.getId())
				.withString("img", cart.getImg())
				.withString("name", cart.getName())
				.withString("price", cart.getPrice())
				.with("count", cart.getCount());
		table.putItem(item);
	}

}
