Assignment : https://docs.google.com/document/d/19mVoVSO7OCK2rgmm1SDNjz0r-r1qM1FBG1YEyNhmQkQ/edit#

This project develops to automate an API for "https://jsonplaceholder.typicode.com".  Rest Assured & TestNG framework used to automate 30 different test scenarios.

Suites allow end user to:
1. Run test suite in parallel
2. Parametrization done, Test Data is abstract from script
3. Framework is Generic, easy to understand & maintainable 

Folder Structure:
lib: It contains common methods
scripts: it contains the actual TestNG class
Suite: TestNG suite file
TestData: It contains "TestCasesData.xlsx" workbook and it has Test Cases & its respective Test data.

Steps to run suite:
Run TestNG suite : runTest.xml 

NOTE : Test cases present in TestCasesData.xlsx. Please refer to the "Feature" column for test case detail.

TestCaseData.xlsx has below worksheets
userIDTest - it contains below columns:
	SrNo - Serial number
	Feature	- Test cases step to execute & expected result description
	Texecute - "Yes" or "No" - Whether user wants to execute the test or not	
	Testcase_ID	- Test cases description
	API_URL	 - REST API URL
	Protocol - "https://" or "http://" - to use in Rest API request
	Domain	 - API Domain - to use in Rest API request
	Url	 - API URL. - to use in Rest API request
	Number_of_Rows	- Expected Number of records/rows return by API request
	Search_Field_Name - Field name on which it can be search record in response
	userId	- one of the field as output of API response
	id	- one of the field as output of API response
	title - one of the field as output of API response
	body- one of the field as output of API response
	
	
postIDTest - it contains below columns:
	SrNo - Serial number
	Feature	- Test cases step to execute & expected result description
	Texecute - "Yes" or "No" - Whether user wants to execute the test or not	
	Testcase_ID	- Test cases description
	API_URL	 - REST API URL
	Protocol - "https://" or "http://" - to use in Rest API request
	Domain	 - API Domain - to use in Rest API request
	Url	 - API URL. - to use in Rest API request
	Number_of_Rows	- Expected Number of records/rows return by API request
	Search_Field_Name - Field name on which it can be search record in response
	postId	- one of the field as output of API response
	id	- one of the field as output of API response
	name - one of the field as output of API response
	email - one of the field as output of API response
	body- one of the field as output of API response

RequestTest - it contains below columns:
	SrNo - Serial number
	Feature	- Test cases step to execute & expected result description
	Texecute - "Yes" or "No" - Whether user wants to execute the test or not	
	Testcase_ID	- Test cases description
	API_URL	 - REST API URL
	Protocol - "https://" or "http://" - to use in Rest API request
	Domain	 - API Domain - to use in Rest API request
	Url	 - API URL. - to use in Rest API request
	Method	- POST or PUT or PATCH or DELETE or GET
	ResponseCode - Expected API Response code
	FieldNames	- Field names separated with semi-colon (;). This field will be by script to build Jason Body
	title - one of the field as output of API response	
	body - one of the field as output of API response	
	userId - one of the field as output of API response	
	id - one of the field as output of API response	
	ResponseFieldNamesToVerify - User can mentioned single file name or multiple fields name separated by semi-colon. Test verify these columns value in Jason response
	FieldNamesShouldNotPresentInResponse -  - User can mentioned single file name or multiple fields name separated by semi-colon. Test verify these columns SHOULD NOT be present Jason response
