package com.retail.online.site;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class CartUpdateFunction extends CartS3Client
		implements
			RequestHandler<HttpRequest, HttpProductResponse> {

	@Override
	public HttpProductResponse handleRequest(HttpRequest request,
			Context context) {
		context.getLogger().log("Input: " + request);

		Gson gson = new Gson();
		// Reads the cart to be updated from body from http request of web api
		String body = request.getBody();
		Cart cartToAdd = gson.fromJson(body, Cart.class);

		// Adds the cart to be updated to list of carts
		List<Cart> cartList = getAllCartsList();
		cartList.removeIf(cart -> cart.getId() == cartToAdd.getId());

		HttpProductResponse httpResponse = new HttpProductResponse(cartToAdd);

		// Update the cart in list and add it to list on bucket
		cartList.add(cartToAdd);
		if (!super.updateAllCarts(cartList)) {
			httpResponse.setStatusCode("500");
		}

		return httpResponse;
	}

}
