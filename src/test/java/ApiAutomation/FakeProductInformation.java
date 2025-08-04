package ApiAutomation;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Objects;

public class FakeProductInformation {

    @Test
    public void validateProductInformation(){

// To be able to do API automation you need
        //1-BaseUrL + Endpoint
        // HTTP Methods
        // If you need, Request Body
        //Header
   // given is all about precondition(preparation for call)
        RestAssured.baseURI="https://fakestoreapi.com";
        RestAssured.basePath="products/1";
       Response response= RestAssured.given().header("Accept","application/json")
                //when is all about action that you take with HTTP methods

                .when().get()

                // it means give me body on my console and validate status code is 200

                .then().log().body().statusCode(200).extract().response();

        //Converting Json Object to Java Code --> DESERIALIZATION
        //We have 3 ways of deserialization
           // 1 Type Ref Conversion
        //This line is all about Deserialization(Converting Json Object to Java Code(Map))

        Map<String, Object> deserializedResponse=response.as(new TypeRef<Map<String, Object>>() {
        });
        System.out.println(deserializedResponse);

        Assert.assertEquals(deserializedResponse.get("id"),1);
        Assert.assertEquals(deserializedResponse.get("title"),"Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops");
        Assert.assertEquals(deserializedResponse.get("price"),109.95);
        Assert.assertTrue(deserializedResponse.get("description").toString().contains("your everyday"));
        Assert.assertEquals(deserializedResponse.get("category"),"men's clothing");
        Assert.assertEquals(deserializedResponse.get("image"),"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");

        // For every Json Object you need to convert it to Map
        Map<String,Object> rating = (Map<String, Object>) deserializedResponse.get("rating");
        Assert.assertEquals(rating.get("rate"),3.9);
        Assert.assertEquals(rating.get("count"),120);


    }


}
