package com.retail.online.site;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.ConfigurationSetDoesNotExistException;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AWSSendEmail implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);

		try {
			// Initializing simple email service
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
					.standard().withRegion(Regions.US_EAST_1).build();
			// Make email request(configuration details)
			SendEmailRequest request = new SendEmailRequest()
					.withDestination(new Destination()
							.withToAddresses("Flashleo2211@gmail.com"))
					.withMessage(new Message().withBody(new Body().withHtml(
							new Content().withCharset("UTF-8").withData(
									"Sample Email for Testing SES in AWS Lambda"))
							.withText(
									new Content().withCharset("UTF-8").withData(
											"Sample Email for Testing SES in AWS Lambda")))
							.withSubject(new Content().withCharset("UTF-8")
									.withData("Testing Email")))
					.withSource("Flashleo2211@gmail.com");
			// To address has to be verified by aws first before using it
			// Comment or remove the next line if you are not using a
			// configuration set
			client.sendEmail(request);
			System.out.println("Email sent!");
			return "Email sent successfully";

		} catch (MessageRejectedException | MailFromDomainNotVerifiedException
				| ConfigurationSetDoesNotExistException e) {
			return "Problem in sending email";
		}

	}

}
