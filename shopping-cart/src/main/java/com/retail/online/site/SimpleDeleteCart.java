package com.retail.online.site;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SimpleDeleteCart extends CartS3Client
		implements
			RequestHandler<HttpRequest, HttpProductResponse> {

	@Override
	public HttpProductResponse handleRequest(HttpRequest inputRequest,
			Context context) {
		context.getLogger().log("Input: " + inputRequest);
		// Reads the path variable value (cart id)from http request of web api
		String reqId = inputRequest.pathParameters.get("id").toString();

		Integer cartid = Integer.parseInt(reqId);

		List<Cart> cartList = getAllCartsList();
		// find the cart with passing cart id
		boolean removeStatus = cartList
				.removeIf(cart -> cart.getId() == cartid);

		// If the cart id exists delete from the list by removing it from the
		// list and updating the list on s3 bucket
		if (removeStatus) {
			if (updateAllCarts(cartList))
				return new HttpProductResponse();
		}

		HttpProductResponse response = new HttpProductResponse();
		response.setStatusCode("404");
		return response;
	}

}
