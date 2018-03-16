1. Start the project in STS as a Spring boot application
or build the jar file and call it as a stand-alone Java application using the class org.dehkordi.oauth2demo.OAuth2SpringBootDemoServerApplication as application entry point.
So the server will be running as a Tomcat service and listening to the http port 8080 (and ssl port 8443)

2. Import the file src/test/resources/OAuth2SpringBootDemo.postman_collection.json in Postman as new collection

3. Use the imported requests in Postman to test the OAuth2SpringBootDemo application server.

4. All default users have password "pass". Default user "admin" has the roles "ADMIN", "USER" and "ACTUATOR", default user "user" has the roles "USER" and "ACTUATOR" and default user "public" has just the role "ACTUATOR". So not each user is allowed to call any end points. To call some of end points, is an access_token needed, which can be get by call the end point "/token"

5. Have fun ...

6. I'll be thankful for your feedbacks