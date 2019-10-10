package com.retail.online.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class SNSLambdaFunction implements RequestHandler<SNSEvent, String> {

	@Override
	public String handleRequest(SNSEvent event, Context context) {
		context.getLogger().log("Received event: " + event);
		// Published message in SNS will be received here and assigned to
		// message
		String message = event.getRecords().get(0).getSNS().getMessage();
		// Printing published message in log and it is displayed in cloudwatch
		// logs
		context.getLogger().log("From SNS: " + message);

		return message;
	}
}
