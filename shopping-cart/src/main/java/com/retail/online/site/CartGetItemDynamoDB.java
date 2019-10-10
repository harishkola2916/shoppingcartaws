package com.retail.online.site;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CartGetItemDynamoDB implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);

		// Initialize DyanamoDB
		Item item = initDynamoDbClient(100);
		if (item != null) {
			return item.toJSONPretty();
		}

		return "Object does not exist";
	}

	/**
	 * Get cart record from the table by passing cartid
	 * 
	 * @param cartId
	 * @return
	 */
	private Item initDynamoDbClient(int cartId) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Cart");
		// Item item = table.getItem("id", cartId); //for selecting total object
		// based on primary key

		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put("#nam", "name");
		expressionAttributeValues.put("#coun", "count");
		// for selecting specific fields
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("id", cartId)
				.withProjectionExpression("id,img,price");
		Item item2 = table.getItem(spec);

		System.out.println(item2.toJSONPretty());
		return item2;
	}

}
