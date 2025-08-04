package api.qa.petstore.endpoint;
import api.qa.petstore.pojo.PJ_FindPet;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.ConfigReader;

public class EP_FindMyPet {
    public void validateFindingOfPet(String id,String status,String name){
        RestAssured.baseURI= ConfigReader.readProperty("base_url");
        RestAssured.basePath=ConfigReader.readProperty("ep_find_my_pet");

        Response response = RestAssured.given().accept("application/json").contentType("application/json")
                .body(id).when().get().then().log().body().statusCode(200).extract().response();

        PJ_FindPet deserializedResponse = response.as(PJ_FindPet.class);
        Assert.assertEquals(deserializedResponse.getId(),Integer.parseInt(id));
        Assert.assertEquals(deserializedResponse.getStatus(),status);
        Assert.assertEquals(deserializedResponse.getName(),name);

    }
}

