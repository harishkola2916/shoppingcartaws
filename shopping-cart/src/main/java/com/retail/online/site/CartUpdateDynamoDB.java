package com.retail.online.site;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CartUpdateDynamoDB implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);

		// Item item = initDynamoDbClient(100);

		Item item = updateExpression(100);

		if (item != null) {
			return item.toJSONPretty();
		}

		return "Object does not exist to update";

	}

	/**
	 * Updated movie table in dynamodb by using updateItem method
	 * 
	 * @param cartId
	 * @return
	 */
	private Item initDynamoDbClient(int cartId) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Cart");

		Map<String, String> expressionAttributeNames = new HashMap<String, String>();
		expressionAttributeNames.put("#n", "name");
		expressionAttributeNames.put("#coun", "count");

		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put(":val1", "Chillies");
		expressionAttributeValues.put(":val2", 128);

		UpdateItemOutcome outcome = table.updateItem("id", cartId,
				"set #n = :val1, #coun = :val2", expressionAttributeNames,
				expressionAttributeValues);

		// System.out.println(outcome.);
		return outcome.getItem();
	}

	/**
	 * Updating table record by passing cartid, used mappings for reserved words
	 * and updateItemspec
	 * 
	 * @param cartId
	 * @return
	 */
	private Item updateExpression(int cartId) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Movie");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("id", cartId)
				.withUpdateExpression("set #n = :val1, #coun = :val2")
				.withNameMap(
						new NameMap().with("#n", "name").with("#coun", "count"))
				.withValueMap(new ValueMap().with(":val1", "Cucumbers")
						.withNumber(":val2", 38))
				.withReturnValues(ReturnValue.ALL_NEW);

		UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
		return outcome.getItem();

	}

}
