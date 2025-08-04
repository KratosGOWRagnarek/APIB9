package api.qa.petstore.test;

import api.qa.petstore.endpoint.EP_FindMyPet;
import org.testng.annotations.Test;

public class FindPetTests {
    @Test
    public void validateFindingPet(){
        EP_FindMyPet epFindMyPet = new EP_FindMyPet();
        epFindMyPet.validateFindingOfPet("737","Available","Hello");
    }
}
