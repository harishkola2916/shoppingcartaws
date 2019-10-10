package com.retail.online.site;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SimpleOutputDisplay extends CartS3Client
		implements
			RequestHandler<HttpQuerystringRequest, HttpProductResponse> {

	@Override
	public HttpProductResponse handleRequest(
			HttpQuerystringRequest inputRequest, Context context) {
		context.getLogger().log("Input: " + inputRequest);
		// Reads the path variable value (cart id)from http request of web api
		String numb = inputRequest.getQueryStringParameters().get("id")
				.toString();
		// if the value is all, retrieve all objects
		if (numb.equalsIgnoreCase("all")) {
			Cart[] carts = getAllCarts();
			return new HttpProductResponse(carts);
		}
		// if the value is not all, retrieve only object that matches with id
		// passed
		Integer cartid = Integer.parseInt(numb);
		Cart resultCart = getCartbyId(cartid);
		return new HttpProductResponse(resultCart);
	}

	/**
	 * This method finds the cart for a mentioned cartid
	 * 
	 * @return
	 */
	private Cart getCartbyId(int cartId) {

		/*
		 * Region region = Region.US_WEST_2; S3Client s3Client =
		 * S3Client.builder().region(region).build(); ResponseInputStream<?>
		 * resObject = s3Client.getObject(
		 * GetObjectRequest.builder().bucket("shopping-cart-bucket-data")
		 * .key("shopping-cart.json").build());
		 * 
		 * InputStreamReader is = new InputStreamReader(resObject);
		 * BufferedReader br = new BufferedReader(is); Gson gson = new Gson();
		 * Cart[] carts = null; carts = gson.fromJson(br, Cart[].class);
		 */
		Cart[] carts = getAllCarts();
		for (Cart cart : carts) {
			if (cart.getId() == cartId)
				return cart;
		}

		return new Cart(1, "jksjldfs", "jasmie", "12", 6);
	}

}
