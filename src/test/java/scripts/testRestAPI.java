package scripts;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import lib.CommonUtilities;
import lib.RestMethods;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Iterator;

@Test()
public class testRestAPI {
    RestMethods restMethods = new RestMethods();
    SoftAssert softAssert = new SoftAssert();

    Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

    /**
     * This test Verify User ID Data API, it sends request and verify the response
     * Test case 1 : It send request and verify the number of records
     * Test case 2 : Verify title in response
     * Test case 3 : Verify body in response
     * @param    data - GET or POST or PUT or DELETE or PATCH
     *
     *
     * @return   None
     * @throws   Exception
     *
     */
    @Test(dataProvider = "getDataProviderDetails", enabled = true,groups = {"userId"})
    public void testVerifyUserIDData(Hashtable<String,String> data) throws IOException {

        //Read variables from test data and store in variable
        //Variable declaration section
        String sUserID = data.get("userId");
        String sID = data.get("id");
        String sExpectedTitle = data.get("title").trim();
        String sExpectedBody = data.get("body").trim();
        String sSearch_Field_Name = data.get("Search_Field_Name").trim();
        int iExpectedNumberOfRecords = Integer.parseInt(data.get("Number_of_Rows"));
        String sProtocol = data.get("Protocol").trim();
        String sDomain = data.get("Domain").trim();
        String sUrl = data.get("Url").trim();
        String sActualTitle = "";
        String sActualBody = "";
        String sActualBodyAllLines="";
        int iActualNumberOfRecords = 0;
        BufferedReader bfrActualBody =null;
        String sAPIURL = "";

        boolean blnValidateTestData = true;

        //Check user has passed the correct protocol from test data
        //Used Assert (hard) so that test will failed, exit & proceed for next test
        blnValidateTestData = CommonUtilities.validateTestData(sProtocol,sDomain,sUrl);
        Assert.assertEquals(true,blnValidateTestData,"Test Data issue - 'Protocol' should be 'https' or 'https' or Domain or Url is blank");
        //Build REST API Url
        sAPIURL = sProtocol + sDomain + "/" + sUrl;

        //Test case 1 - Execute request and verify number of rows/data return in API response
        Reporter.log("Feature " + data.get("Feature") + " Testcase ID : " + data.get("Testcase_ID") + " For API : " + sAPIURL + " Started ");
        //Verifying number of records return by API
        Reporter.log("Verifying number of records return by API");
        //Test 1 - Verifying API response in terms of number lines
        iActualNumberOfRecords = restMethods.getDataNumber(sAPIURL);
        softAssert.assertEquals(iActualNumberOfRecords,iExpectedNumberOfRecords,"No of record should match");

        //Test 2 - Verifying Title in API response
        Reporter.log("Extracting 'title' for userid " + data.get("userId"));
        sActualTitle = restMethods.getData(sAPIURL,"", sSearch_Field_Name, sUserID,sID,"title");
        Reporter.log("Verifying 'title' : Expected '" + sExpectedTitle + "' Actual : '" + sActualTitle + "'");
        softAssert.assertEquals(sActualTitle,sExpectedTitle,"Verifying title");

        //Test 3 - Verifying body
        //Note - 'body' field has new line character (\n).  I tried 1st using replaceAll method to replace all new
        //line character with space. Also tried to use split method to split string with newline character.
        //But somehow it was not working. Here I am reading body, converting it to BufferReader.
        //Using buffer reader, reading the line & building expected lines and then do verification
        Reporter.log("Extracting 'body' for userid " + data.get("userId"));
        sActualBody = restMethods.getData(sAPIURL,"", sSearch_Field_Name, sUserID,sID,"body");
        //API response return the 'body' which has multi-line string
        //calling function which will read the mult-line string line by line and all lines concante
        //with "\n" chracter in single line
        sActualBodyAllLines = CommonUtilities.getStringFromMultiline(sActualBody);
        Reporter.log("Verifying 'body' : Expected '" + sExpectedBody + "' Actual : '" + sActualBodyAllLines + "'");
        softAssert.assertEquals(sActualBodyAllLines,sExpectedBody,"Verifying body");

        Reporter.log("Feature " + data.get("Feature") + " Testcase ID : " + data.get("Testcase_ID") + " Done!! ");
        softAssert.assertAll();
    }

    /**
     * This test Verify Post ID Data API, it sends request and verify the response
     * Test case 1 : It send request and verify the number of records
     * Test case 2 : Verify name in response
     * Test case 3 : Verify body in response
     * Test case 4 : Verify email in response
     * @param    data - GET or POST or PUT or DELETE or PATCH
     *
     *
     * @return   None
     * @throws   Exception
     *
     */
    @Test(dataProvider = "getDataProviderDetails", enabled = true,groups = {"postId"})
    public void testVerifyPostIDData(Hashtable<String,String> data) throws IOException {

        //Read variables from test data and store in variable
        //Variable declaration section
        String sAPI_URL = data.get("API_URL");
        String sPostID = data.get("postId");
        String sID = data.get("id");
        String sExpectedName = data.get("name").trim();
        String sExpectedBody = data.get("body").trim();
        String sExpectedEmail = data.get("email").trim();
        String sSearch_Field_Name = data.get("Search_Field_Name").trim();
        int iExpectedNumberOfRecords = Integer.parseInt(data.get("Number_of_Rows"));
        String sProtocol = data.get("Protocol").trim();
        String sDomain = data.get("Domain").trim();
        String sUrl = data.get("Url").trim();
        String sActualName = "";
        String sActualEmail="";
        String sActualBody = "";
        String sActualBodyAllLines="";
        int iActualNumberOfRecords = 0;
        boolean blnValidateTestData = true;

        //Check user has passed the correct protocol from test data
        //Used Assert (hard) so that test will failed, exit & proceed for next test
        blnValidateTestData = CommonUtilities.validateTestData(sProtocol,sDomain,sUrl);
        Assert.assertEquals(true,blnValidateTestData,"Test Data issue - 'Protocol' should be 'https' or 'https' or Domain or Url is blank");

        //Build REST API Url
        String sAPIURL = sProtocol + sDomain + "/" + sUrl;

        Reporter.log("Feature " + data.get("Feature") + " Testcase ID : " + data.get("Testcase_ID") + " For API : " + sAPI_URL + " Started ");
        //Verifying number of records return by API
        Reporter.log("Verifying number of records return by API");
        //Test 1 - Verifying API response in terms of number lines
        iActualNumberOfRecords = restMethods.getDataNumber(sAPI_URL);
        softAssert.assertEquals(iActualNumberOfRecords,iExpectedNumberOfRecords,"No of record should match");

        //Test 2 - Verifying Name in API response
        Reporter.log("Extracting 'name' for postId " + sPostID);
        sActualName = restMethods.getData(sAPI_URL,"", sSearch_Field_Name, sPostID,sID,"name");
        Reporter.log("Verifying 'name' : Expected '" + sExpectedName + "' Actual : '" + sActualName + "'");
        softAssert.assertEquals(sActualName,sExpectedName,"Verifying 'name'");

        //Test 3 - Verifying Email in API response
        Reporter.log("Extracting 'name' for postId " + sPostID);
        sActualEmail = restMethods.getData(sAPI_URL,"", sSearch_Field_Name, sPostID,sID,"email");
        Reporter.log("Verifying 'email' : Expected '" + sExpectedEmail + "' Actual : '" + sActualEmail + "'");
        softAssert.assertEquals(sActualEmail,sExpectedEmail,"Verifying 'email'");

        //Test 3 - Verifying body
        //Note - 'body' field has new line character (\n).  I tried 1st using replaceAll method to replace all new
        //line character with space. Also tried to use split method to split string with newline character.
        //But somehow it was not working. Here I am reading body, converting it to BufferReader.
        //Using buffer reader, reading the line & building expected lines and then do verification
        Reporter.log("Extracting 'body' for postId " + sPostID);
        sActualBody = restMethods.getData(sAPI_URL,"", sSearch_Field_Name, sPostID,sID,"body");
        //API response return the 'body' which has multi-line string
        //calling function which will read the mult-line string line by line and all lines concante
        //with "\n" chracter in single line
        sActualBodyAllLines = CommonUtilities.getStringFromMultiline(sActualBody);
        Reporter.log("Verifying 'body' : Expected '" + sExpectedBody + "' Actual : '" + sActualBodyAllLines + "'");
        softAssert.assertEquals(sActualBodyAllLines,sExpectedBody,"Verifying body");

        Reporter.log("Feature " + data.get("Feature") + " Testcase ID : " + data.get("Testcase_ID") + " Done!! ");
        softAssert.assertAll();
    }

    /**
     * This test will verify various test scenarios with POST, PUT, PATCH AND DELETE METHOD
     * This test has 16 test scenarios
     *
     * @param    data - GET or POST or PUT or DELETE or PATCH
     *
     *
     * @return   None
     * @throws   Exception
     *
     */
    @Test(dataProvider = "getDataProviderDetails", enabled = true,groups = {"RestRequest"})
    public void testRequestAndResponse(Hashtable<String,String> data) throws IOException {

        //Read variables from test data and store in variable
        //Variable declaration section
        String sSrNo = data.get("SrNo");
        String sFeature = data.get("Feature").trim();
        String sTestcase_ID=data.get("Testcase_ID").trim();
        String sProtocol = data.get("Protocol").trim();
        String sDomain = data.get("Domain").trim();
        String sUrl = data.get("Url").trim();
        String sMethod = data.get("Method").trim();
        String sResponseCode = data.get("ResponseCode").trim();
        String sFieldNames = data.get("FieldNames");
        String sResponseFieldNamesToVerify = data.get("ResponseFieldNamesToVerify").trim();
        String sFieldNamesShouldNotPresentInResponse = data.get("FieldNamesShouldNotPresentInResponse").trim();
        String[] sArrayVerifyFields = new String[1];
        boolean blnValidateTestData = true;
        JsonObject resJsonObject = null;
        Response responseAPI = null;
        String sJsonBody = "";
        String[] sArrayFieldName = new String[1];
        String sFieldName = "";
        String sExpectedValue ="";
        boolean blnNotFound =true;

        //Check user has passed the correct protocol from test data
        //Used Assert (hard) so that test will failed, exit & proceed for next test
        blnValidateTestData = CommonUtilities.validateTestData(sProtocol,sDomain,sUrl);
        Assert.assertEquals(true,blnValidateTestData,"Test Data issue - 'Protocol' should be 'https' or 'https' or Domain or Url is blank");
        //Build REST API Url
        String sAPIURL = sProtocol + sDomain + "/" + sUrl;

        Reporter.log("Feature " + data.get("Feature") + " Testcase ID : " + data.get("Testcase_ID") + " For API : " + sAPIURL + " Started ");
        //Build Jason - Test Data sheet has 'FieldNames' column, which contains column names seperated by semi-colon

        if (sFeature.trim().length() >0 ) {
            sArrayFieldName = sFieldNames.split(";");
            sJsonBody = "{ ";
            for (int iCounter = 0; iCounter < sArrayFieldName.length; iCounter++) {
                if (iCounter != (sArrayFieldName.length - 1)) {
                    sJsonBody += " \"" + sArrayFieldName[iCounter] + "\" : " + data.get(sArrayFieldName[iCounter]) + ", ";
                } else {
                    sJsonBody += " \"" + sArrayFieldName[iCounter] + "\" : " + data.get(sArrayFieldName[iCounter]);
                }
            }
            sJsonBody +=" }";
            Reporter.log("Building JSON Body " + sJsonBody);
        }
        //Below is going to call generic method and capture API response in variable

        //System.out.println(sJsonBody);
        Reporter.log("Sending Request : " + sAPIURL);
        if (data.get("Method").toUpperCase().equals("POST")) {
            responseAPI = restMethods.sendRequestAndVerifyResponse(RestMethods.Method.POST, sAPIURL, sJsonBody, "", "");
        } else if (data.get("Method").toUpperCase().equals("PUT")) {
            responseAPI = restMethods.sendRequestAndVerifyResponse(RestMethods.Method.PUT, sAPIURL, sJsonBody, "", "");
        } else if (data.get("Method").toUpperCase().equals("PATCH")) {
            responseAPI = restMethods.sendRequestAndVerifyResponse(RestMethods.Method.PATCH, sAPIURL, sJsonBody, "", "");
        } else if (data.get("Method").toUpperCase().equals("DELETE")) {
            responseAPI = restMethods.sendRequestAndVerifyResponse(RestMethods.Method.DELETE, sAPIURL, sJsonBody, "", "");
        }

        //Checking whether responseAPI is not return valie
        if (responseAPI != null) {

            //Verifying response code
            int iResponseCode =responseAPI.statusCode();
            Reporter.log("Response Code from API : " + iResponseCode);
            softAssert.assertEquals(iResponseCode,Integer.parseInt(sResponseCode),"Verify response code Expected Response code : " + sResponseCode + " Actual : " + iResponseCode);


            String resString = responseAPI.asString();
            Reporter.log("Response from API : " + resString);
            //The below condition added because when there is internal server error from server
            //as response then Gson element throws exception.  To avoid it I am checking response code which
            //is 500 range so that below code does not throw error.
            if (iResponseCode >= 500 && iResponseCode <= 599 )
            {
                //don't do anything
                Reporter.log("API has Internal server error");
            } else {
                if (!resString.isEmpty()) {
                    JsonElement resJsonElement = gson.fromJson(resString, JsonElement.class);
                    if (resJsonElement.isJsonObject())
                        resJsonObject = resJsonElement.getAsJsonObject();
                    else if (resJsonElement.isJsonArray() || resJsonElement.isJsonPrimitive()) {
                        resJsonObject = new JsonObject();
                        resJsonObject.add("result", resJsonElement);
                    }
                    //Verify response of API field wise - In Test Data - column 'ResponseFieldNamesToVerify' contains
                    //field names separated by semi-colon. It is mandatory have column for each column name specified in this list
                    //program will read the expected value from respective column
                    //User can als keep single column without any semi-colon
                    //Below logic with split colum names using semi-colon seperator and read value expected value
                    //from test data and actual value from Jason response
                    if (sResponseFieldNamesToVerify.length() > 0) {
                        sArrayVerifyFields = sResponseFieldNamesToVerify.split(";");
                        if (sArrayVerifyFields.length >= 1) {
                            for (int iCounter = 0; iCounter < sArrayVerifyFields.length; iCounter++) {
                                sFieldName = sArrayVerifyFields[iCounter];
                                sExpectedValue = data.get(sFieldName);
                                if (sExpectedValue.trim().startsWith("\"")) {
                                    sExpectedValue = sExpectedValue.trim().substring(1, sExpectedValue.length());
                                }
                                if (sExpectedValue.trim().endsWith("\"")) {
                                    sExpectedValue = sExpectedValue.trim().substring(0, sExpectedValue.length() - 1);
                                }
                                String sActualValue = resJsonObject.get(sFieldName).getAsString();
                                if (sFieldName.trim().toLowerCase().equals("body")) {
                                    //Call function which will return single line from multiple
                                    if (sExpectedValue.contains("\\n")) {
                                        sActualValue = CommonUtilities.getStringFromMultiline(sActualValue);
                                    }
                                }
                                Reporter.log("Verify '" + sFieldName + "' Expected : '" + sExpectedValue + "' Actual : "+ sActualValue);
                                softAssert.assertEquals(sActualValue, sExpectedValue, "Verify field '" + sFieldName + "', Expected : " + sExpectedValue + " Actual : " + sActualValue);
                            }
                        }
                    }

                    //Below code is written for negative scenarios like Jason response SHOULD NOT contains these columns
                    //In Test Data - column 'FieldNamesShouldNotPresentInResponse' contains field names separated by semi-colon.
                    //Below logic will split multiple columns name using semi-colon separator and search that name
                    //is present in Jason Response and it should not present
                    if (sFieldNamesShouldNotPresentInResponse.length() > 0)
                    {

                        sArrayVerifyFields = sFieldNamesShouldNotPresentInResponse.split(";");
                        if (sArrayVerifyFields.length >= 1) {
                            for (int iCounter = 0; iCounter < sArrayVerifyFields.length; iCounter++) {
                                blnNotFound=true;
                                sFieldName = sArrayVerifyFields[iCounter];
                                Iterator<?> keys = resJsonObject.keySet().iterator();
                                while(keys.hasNext()) {
                                    if (keys.next().equals(sFieldName)){
                                        blnNotFound =false;
                                        break;
                                    }
                                }
                                Reporter.log("Verify field '" + sFieldName + "' should not be present in JSON response");
                                softAssert.assertEquals(true,blnNotFound,"Verify field '" + sFieldName + "' should not be present in JSON response");
                            }
                        }

                    }
                }
            }
        } else {
            Assert.fail("API does not return anything");
        }
        Reporter.log("Feature " + data.get("Feature") + " Testcase ID : " + data.get("Testcase_ID") + " For API : " + sAPIURL + " Done! ");
        softAssert.assertAll();
    }

    /**
     * This is Data Provider function which get use in Test Cases
     *
     *
     * @param    context - TestNG - ITestContext
     *
     *
     * @return   Object[][]
     * @throws   Exception
     *
     */
    @DataProvider(name = "getDataProviderDetails", parallel = false )
    public static Object[][] getDataProviderDetails(ITestContext context)
    {
        CommonUtilities commonUtl = new CommonUtilities();
        Hashtable[] hData = commonUtl.getAllExcelRow(context);
        Object[][] result = null;
        if (hData != null)
        {
            result = new Hashtable[hData.length][1];
            int iCnt = 0;
            for (Hashtable h : hData)
            {
                result[iCnt][0] = h;
                //System.out.println(h);
                iCnt++;
            }
        } else
        {
            result = new Hashtable[1][1];
            result[0][0] = null;
        }
        return result;
    }
}