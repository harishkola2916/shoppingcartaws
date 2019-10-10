package com.retail.online.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class SNSLambdaFunctionToMobile
		implements
			RequestHandler<SNSEvent, String> {

	@Override
	public String handleRequest(SNSEvent event, Context context) {
		context.getLogger().log("Received event: " + event);
		// Published message in SNS will be received here and assigned to
		// message
		String message = event.getRecords().get(0).getSNS().getMessage();
		context.getLogger()
				.log("Recieved message from SNS Topic --> : " + message);
		// Creating SNS Client
		AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
		// configuring subscription object ... (If user wants to notified about
		// published message)
		PublishRequest pr = new PublishRequest();
		pr.setMessage(message);
		// configuring phone
		pr.setPhoneNumber("+1232434543534");
		snsClient.publish(pr);
		return message;
	}
}
