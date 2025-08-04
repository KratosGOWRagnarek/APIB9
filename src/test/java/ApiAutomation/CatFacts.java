package ApiAutomation;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class CatFacts {

    @Test
    public void validateCatFacts(){
        RestAssured.baseURI="https://catfact.ninja";
        RestAssured.basePath="facts";

        Response response = RestAssured.given().accept("application/json")
                .queryParam("limit",332).when().get()
                .then().log().body().statusCode(200).extract().response();

        Map<String,Object> deserializedResponse=response.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String,Object>> allData= (List<Map<String, Object>>) deserializedResponse.get("data");

        int moreThan50=0;
        int lessThan200=0;
        int More50Less200=0;
        int containsCat=0;

        for (Map<String,Object> data: allData){
            if (Integer.parseInt(data.get("length").toString())>50){
            moreThan50++;
            }
            if(Integer.parseInt(data.get("length").toString())<200){
                lessThan200++;
            }
            if(Integer.parseInt( data.get("length").toString())<200&& Integer.parseInt(data.get("length").toString())>50){
                More50Less200++;
            }

            if(!data.get("fact").toString().toLowerCase().contains("cat")){
                containsCat++;
            }

        }

        Assert.assertEquals(moreThan50,299);
        Assert.assertEquals(lessThan200,293);
        Assert.assertEquals(More50Less200,260);
        Assert.assertEquals(containsCat,25);

    }
}
