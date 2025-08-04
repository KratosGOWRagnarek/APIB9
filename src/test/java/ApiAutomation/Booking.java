package ApiAutomation;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class Booking {

    @Test
    public void validateBooking(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        RestAssured.basePath="booking/{id}";
       Response response= RestAssured.given().accept("application/json")
               .pathParams("id",44).when().get().then().log()
               .body().statusCode(200).extract().response();

        Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });
        Assert.assertEquals(deserializedResponse.get("firstname"),"Josh");
        Map<String, Object> checking = (Map<String, Object>) deserializedResponse.get("bookingdates");
        Assert.assertEquals(checking.get("checkin"),"2018-01-01");
    }

    @Test
    public void validateAuthToken(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        RestAssured.basePath="auth";
        Response response = RestAssured.given().header("Content-Type",
                "application/json").header("Accept","application/json")
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when().post().then().log().body()
                .statusCode(200).extract().response();

        Map<String,Object> deserializedResponse= response.as(new TypeRef<Map<String, Object>>() {
        });

        Assert.assertNotNull(deserializedResponse.get("token"));
        Assert.assertEquals(deserializedResponse.get("token")
                .toString().length(),15);
    }

    @Test
    public void validateCreateBooking(){

        //Validate the bookingId is not null  <MAP>
        //Validate the firstName <MAP>
        //Validate the checkout date <MAP>

        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        RestAssured.basePath="booking";

        Response response=RestAssured.given().contentType("application/json").accept("application/json")
                .body("{\n" +
                        "    \"firstname\" : \"Sam\",\n" +
                        "    \"lastname\" : \"Av\",\n" +
                        "    \"totalprice\" : 777,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"IT WORLD\"\n" +
                        "}")
                .when().post().then().log().body().statusCode(200).extract().response();

        Map<String, Object> deserializedResponse=response.as(new TypeRef<Map<String, Object>>() {});

        Assert.assertNotNull(deserializedResponse.get("bookingid"));

        Map<String, Object> booking= (Map<String, Object>) deserializedResponse.get("booking");

        Assert.assertEquals(booking.get("firstname"),"Sam");

        Map<String,Object> bookingdates= (Map<String, Object>) booking.get("bookingdates");

        Assert.assertEquals(bookingdates.get("checkout"),"2019-01-01");
    }

    @Test
    public void validateUpdatingBooking(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        RestAssured.basePath="booking/{id}";

        Response response = RestAssured.given().contentType("application/json").accept("application/json")
                 .pathParams("id",1126).header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=").
                body("{\n" +
                        "    \"firstname\": \"Sam\",\n" +
                        "    \"lastname\": \"Brown\",\n" +
                        "    \"totalprice\": 121,\n" +
                        "    \"depositpaid\": true,\n" +
                        "    \"bookingdates\": {\n" +
                        "        \"checkin\": \"2018-01-01\",\n" +
                        "        \"checkout\": \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\": \"Breakfast\"\n" +
                        "}").when()
                .put().then().log().body().statusCode(200).extract().response();
        Map<String,Object> deserializedResponse=response.as(new TypeRef<Map<String, Object>>() {});
        Assert.assertEquals(deserializedResponse.get("lastname"),"Brown");
        Assert.assertEquals(deserializedResponse.get("firstname"),"Sam");
        Assert.assertEquals(deserializedResponse.get("totalprice"),121);
        Assert.assertEquals(deserializedResponse.get("additionalneeds"),"Breakfast");
        Assert.assertEquals(deserializedResponse.get("depositpaid"),true);

        Map<String,Object> bookingdates= (Map<String, Object>) deserializedResponse.get("bookingdates");

        Assert.assertEquals(bookingdates.get("checkin"),"2018-01-01");
        Assert.assertEquals(bookingdates.get("checkout"),"2019-01-01");


    }

    @Test
    public void validateBookingWithJsonPath(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        RestAssured.basePath="booking/{id}";
        Response response= RestAssured.given().accept("application/json")
                .pathParams("id",33).when().get().then().log()
                .body().statusCode(200).extract().response();

        JsonPath deserializedResponse=response.jsonPath();
        Assert.assertEquals(deserializedResponse.get("firstname"),"Bob");
        Assert.assertEquals(deserializedResponse.get("bookingdates.checkin"),"2018-01-01");
    }

}
