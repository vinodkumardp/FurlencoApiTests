# FurlencoApiTests
1.This basically is written using restassured , Testng , gradle and BDD framework 

This should be the flow if some one has to add any new test case in this framework
a.First the path can be added in config.properties 
b.next what all are the function is re usable should enter the baseclass
c.Then the actual test class can extend this and use the member methods and member variables.
d.Here in login and setting up the request spec everything happens before the suite 
e.Logout happens after the suite runs 

I have used gradle so that building the project dependencies etc would be a very easy task 
 
 So basically the gradle task test uses the TestNGRunTests.xml 
 
 So to run the suite one can either run the command gradle test in the project level or run the Apitests class using testng
 
 Here i have placed the functions signup,login in base class rather than writing it as tests because as the product 
 is getting automated more from QA point of view these two are reusable functions but still to validate the api 
 carefully i have added the asserts in basclass too
 
 
