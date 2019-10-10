package com.retail.online.site;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class CartS3Client {

	/**
	 * This method access file from s3 bucket and reads json data from file and
	 * convert into array of cart objects
	 * 
	 * @return
	 */
	protected Cart[] getAllCarts() {
		// Connecting to region
		Region region = Region.US_WEST_2;
		S3Client s3Client = S3Client.builder().region(region).build();
		// Accessing the s3 bucket and reading file.
		ResponseInputStream<?> objectData = s3Client.getObject(
				GetObjectRequest.builder().bucket("shopping-cart-bucket-data")
						.key("shopping-cart.json").build());

		InputStreamReader isr = new InputStreamReader(objectData);
		BufferedReader br = new BufferedReader(isr);

		Cart[] carts = null;

		// Convert json data from the file to pojo class
		Gson gson = new Gson();
		carts = gson.fromJson(br, Cart[].class);
		return carts;
	}

	/**
	 * Returns array list of carts
	 * 
	 * @return
	 */
	protected ArrayList<Cart> getAllCartsList() {
		return new ArrayList<Cart>(Arrays.asList(getAllCarts()));
	}

	/**
	 * This method converts array of cart objects to json string and add it to
	 * file in s3 bucket Here adding data to file means replacing all the
	 * contents of file with new file
	 * 
	 * @param carts
	 * @return
	 */
	protected boolean updateAllCarts(Cart[] carts) {

		Gson gson = new Gson();
		String jsonString = gson.toJson(carts);

		Region region = Region.US_WEST_2;
		S3Client s3Client = S3Client.builder().region(region).build();

		PutObjectResponse putResponse = s3Client.putObject(
				PutObjectRequest.builder().bucket("shopping-cart-bucket-data")
						.key("shopping-cart.json").build(),
				RequestBody.fromString(jsonString));

		return putResponse.sdkHttpResponse().isSuccessful();

	}

	/**
	 * This method returns the status of updating carts to s3 bucket
	 * 
	 * @param cartList
	 * @return
	 */
	protected boolean updateAllCarts(List<Cart> cartList) {
		Cart[] carts = cartList.toArray(new Cart[cartList.size()]);
		return updateAllCarts(carts);
	}

}
