package lib;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.testng.Assert;
import org.testng.ITestContext;
import  org.testng.xml.XmlTest;

public class CommonUtilities {

    /**
     * This method is used to Read excel file and retrieves respective data return by SQL query.
     *
     * @param    context Excel file Name
     *
     * @return   ResultSet JDBC-ODBC recordset object.
     * @throws Exception
     *
     */
    public Hashtable[] getAllExcelRow(ITestContext context)
    {
        Hashtable[] htRow = null;
        String sTestDataFolder = System.getProperty("user.dir") + "\\src\\test\\java\\TestData\\";
        String sExcelFileName = sTestDataFolder + context.getCurrentXmlTest().getAllParameters().get("ExcelWorkbookName");
        String sSheetName = context.getCurrentXmlTest().getAllParameters().get("Worksheet");
        String sTestCaseName = context.getCurrentXmlTest().getAllParameters().get("TestCaseID");
        boolean bFound = false;
        int iRowCnt = 0, iRowCounter = 0;
        String sQuery = "";
        sQuery = "Select * from " + sSheetName + " where Testcase_ID='" + sTestCaseName
                + "' and TExecute='Yes'";
        try
        {
            Recordset rsExcelRow = readExcelDatabase(sExcelFileName, sQuery);
            iRowCnt = rsExcelRow.getCount();
            htRow = new Hashtable[iRowCnt];
            ArrayList<String> rsmd = rsExcelRow.getFieldNames();
            int iColumnCount = rsmd.size();
            while (rsExcelRow.next())
            {
                Hashtable htTemp = new Hashtable();
                // log.info(rsmd);
                for (int i = 0; i < iColumnCount; i++)
                {
                    htTemp.put(rsmd.get(i), rsExcelRow.getField(rsmd.get(i)));
                }
                htRow[iRowCounter] = htTemp;
                iRowCounter++;
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return htRow;
    }


    /**
     * This method is used to Read excel file and retrieves respective data return by SQL query.
     *
     * @param    sExcelFileName Excel file Name
     * @param    sSQLQuery SQL Query Name.
     *
     * @return   ResultSet JDBC-ODBC recordset object.
     * @throws Exception
     *
     */
    @SuppressWarnings("null")
    public Recordset readExcelDatabase(String sExcelFileName, String sSQLQuery) throws Exception
    {
        Recordset rsData = null;
        Fillo fillo = new Fillo();
        com.codoid.products.fillo.Connection connection = fillo.getConnection(sExcelFileName);
        rsData = connection.executeQuery(sSQLQuery);
        return rsData;
    }

    /**
     * This method is will tales string which has new line character.  Read line by line and concanate each line & return as a String
     *
     * @param    sMultilineString Excel file Name
     *
     * @return   ResultSet JDBC-ODBC recordset object.
     * @throws IOException
     *
     */
    public static String getStringFromMultiline(String sMultilineString) throws IOException {

        BufferedReader bfrActualBody = new BufferedReader(new StringReader(sMultilineString));

        String sActualBodyAllLines = "";
        String line2 = null;
        while ((line2 = bfrActualBody.readLine()) != null)
        {
            if (sActualBodyAllLines.isEmpty())
            {
                sActualBodyAllLines = line2;
            } else {
                sActualBodyAllLines += "\\n" + line2;
            }
        }
        return sActualBodyAllLines;
    }

    /**
     * This method is will verify that protocol, url and domain should not blank and it has valid test data
     *
     * @param    sProtocol - "https://" or "https"
     * @param    sDomain - Domain of API
     * @param    sUrl - Url of API
     *
     * @return   boolean.
     * @throws
     *
     */
    public static boolean validateTestData(String sProtocol, String sDomain, String sUrl)
    {
        if ((!sProtocol.equals("https://") &&  !sProtocol.equals("http://")  ))
        {
            return false;
        }
        if (sDomain.length() == 0)
        {
            return false;
        }
        if (sUrl.length() == 0)
        {
            return false;
        }
        return true;
    }
}
