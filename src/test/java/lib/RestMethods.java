package lib;


import com.google.gson.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class RestMethods {
    // Define Logger
    private final Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
    public String DB_USER="";
    public String DB_PASSWORD="";


    /**
     * enum method for rest method
     */
    public enum Method {
        GET, POST, PUT, DELETE, PATCH
    }

    /**
     * This method is will tales string which has new line character.  Read line by line and concanate each line & return as a String
     *
     * @param    method - GET OR POST OR PATCH OR DELETE OR PUT
     * @param    url - Rest API URL
     * @param    body - Rest API Jason Body
     * @param    username - Credential - Username
     * @param    password - Credential - Password
     *
     * @return   JasonObject object.
     * @throws Exception
     *
     */
    public JsonObject rest(Method method, String url, String body, String username, String password) {
        Response response = null;
        try {
            RestAssured.useRelaxedHTTPSValidation("TLSv1.2");
            switch (method) {
                case GET:
                    response = given().given().urlEncodingEnabled(false).contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when()
                            .get(url).then().contentType(ContentType.JSON).
                            extract().response();
                    break;
                case POST:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().post(url).then().
                            extract().response();
                    break;
                case PUT:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().put(url).then()
                            .extract().response();
                    break;
                case DELETE:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().delete(url).then().
                            extract().response();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null) {

            String resString = response.asString();
            if (resString.isEmpty())
                return null;
            JsonElement resJsonElement = gson.fromJson(resString, JsonElement.class);
            if (resJsonElement.isJsonObject())
                return resJsonElement.getAsJsonObject();
            else if (resJsonElement.isJsonArray() || resJsonElement.isJsonPrimitive()) {
                JsonObject resJsonObject = new JsonObject();
                resJsonObject.add("result", resJsonElement);
                return resJsonObject;
            }
        }
        return null;
    }

    /**
     * This method is will call methods with username & passport
     *
     * @param    method - GET OR POST OR PATCH OR DELETE OR PUT
     * @param    url - Rest API URL
     * @param    body - Rest API Jason Body
     *
     * @return   JasonObject object.
     * @throws
     *
     */
    public JsonObject rest(Method method, String url, String body) {
        return rest(method, url, body, DB_USER, DB_PASSWORD);
    }


    /**
     * This method will execute API and in response it search for data based User ID & ID then return the value
     *
     * @param    apiUrl - REST API url
     * @param    body - Rest API body
     * @param    sSearchFieldName - Field to search
     * @param    sSearchUserId - Search User ID
     * @param    id - Search for id
     * @param    fieldToNameToExtract - Read value and return it
     *
     * @return   String object.
     * @throws   Exception
     *
     */
    public String getData(String apiUrl, String body, String sSearchFieldName, String sSearchUserId, String id, String fieldToNameToExtract) {
        try {
            String url = apiUrl;
            JsonObject result = rest(Method.GET, url, "");
            //When API return single record then size method shows number of field instead of array
            if (result.size() > 1)
            {
                if (result.get(sSearchFieldName).getAsString().contentEquals(sSearchUserId) &&  result.get("id").getAsString().contentEquals(id))
                {
                    return result.get(fieldToNameToExtract).getAsString();
                }
            } else {
                for (JsonElement element : result.getAsJsonObject().get("result").getAsJsonArray()) {
                    if (element.getAsJsonObject().get(sSearchFieldName).getAsString().contentEquals(sSearchUserId) && element.getAsJsonObject().get("id").getAsString().contentEquals(id)) {
                        return element.getAsJsonObject().get(fieldToNameToExtract).getAsString();
                    }
                }
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * This method will execute API and return number of rows return as a API response
     *
     * @param    apiUrl - REST API url
     *
     *
     * @return   String object.
     * @throws   Exception
     *
     */
    public int getDataNumber(String apiUrl) {
        JsonObject result =null;
        try {
            String url = apiUrl;
            result = rest(Method.GET, url, "");

            if (result.isJsonObject())
            {
                return result.getAsJsonObject().get("result").getAsJsonArray().size();
            }

        } catch (Exception e) {
            if (result.isJsonObject())
            {
                return 1;
            }
            //System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * This method will execute API and return number of rows return as a API response
     *
     * @param    method - GET or POST or PUT or DELETE or PATCH
     * @param    url - API URL
     * @param    body - API JASON BODY IN STRING
     * @param    username - CREDENTIAL - Username (Optional)
     * @param    password - CREDENTIAL - Password (Optional)
     *
     *
     * @return   Response object.
     * @throws   Exception
     *
     */
    public Response sendRequestAndVerifyResponse(Method method, String url, String body, String username, String password) {

        Response response = null;
        try {
            RestAssured.useRelaxedHTTPSValidation("TLSv1.2");
            switch (method) {
                case GET:
                    response = given().given().urlEncodingEnabled(false).contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when()
                            .get(url).then().contentType(ContentType.JSON).
                            extract().response();
                    break;
                case POST:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().post(url).then().
                            extract().response();
                    break;
                case PUT:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().put(url).then()
                            .extract().response();
                    break;
                case PATCH:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().patch(url).then()
                            .extract().response();
                    break;
                case DELETE:
                    response = given().contentType("application/json")
                            .header("X-Requested-With", "RESTWeb", "Cache-Control", "no-cache")
                            .auth().basic(username, password).body(body).when().delete(url).then().
                            extract().response();
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null) {
            return response;
        }
        return null;
    }
}


