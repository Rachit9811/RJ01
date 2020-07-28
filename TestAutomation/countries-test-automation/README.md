# countries-test-automation project
This is the child project of the frame-dependency-api project. 
<br><br>
**Run Command**: -
>mvn generate-resources test
<br><br>

It contains all the test scripts TC01, TC02 and TC03 using the parent main classes. 
1) TC01 test script output<br>
<div align="Left">
    <img src="./reportingOutput/TC01.JPG" width="500px"</img> 
</div>
<br>
2) TC01 test script output<br>
<div align="Left">
    <img src="./reportingOutput/TC02.JPG" width="500px"</img> 
</div>
<div align="Left">
    <img src="./reportingOutput/TC02_Mismatch.JPG" width="200px"</img> 
</div>
<br>
3) TC01 test script output<br>
<div align="Left">
    <img src="./reportingOutput/TC03.JPG" width="600px"</img> 
</div>

<br><br>Other than these, it also comprises the 2 main classes: -<br> 
1) **Launcher java** 
<br>a) It intilizes the config.properties file, which is working like TestSuite file. 
<br>b) Here is also a code to generate run time testng.xml which contains only those classes which are available in config file. **generate-resources** is the maven goal to generate testng.xml.
<br>c) And finally we are running that testng file.**test** is the maven goal to run testng.xml.
<br><br>
2) **TestBase.java**
<br>a) It is a place to initialize all the main classes object of the parent object and finally used a parent class for all the Test Script classes.
<br>b) Constructor is used to hit the https://restcountries.eu/rest/v2/all API and run some queries to get the values in api & db variables.
<br>c) @BeforeSuite and @AfterSuite testng annotation are also give here.
<br><br>

**Report output of the following 3 test cases as per the configuration, shown below**: -<br>
<div align="Left">
    <img src="./reportingOutput/All3.JPG" width="300px"</img> 
</div>
<div align="Center">
    <img src="./reportingOutput/1&3.JPG" width="300px"</img> 
</div>
<div align="Right">
    <img src="./reportingOutput/2&3.JPG" width="300px"</img> 
</div>
<br>
