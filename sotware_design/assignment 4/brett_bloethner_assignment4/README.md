# cscie97-assignment4

## Running this application

The application lives under the com. directory pattern so compilation must be done with the below command

``` javac com/cscie97/smartcity/controller/*.java com/cscie97/smartcity/model/*.java com/cscie97/smartcity/authentication/*.java com/cscie97/smartcity/ledger/*.java com/cscie97/smartcity/test/*.java ```

Also the test script file name must be provided

The test script file name for the class provided test script is
```Assignment_4_sample_script.script``` so the full command after compilation would be
```java -cp . com.cscie97.smartcity.test.TestDriver Assignment_4_sample_script.script```

The test script file name for the custom test script is
```Assignment_4_custom_sample_script.script``` so the full command after compilation would be
```java -cp . com.cscie97.smartcity.test.TestDriver Assignment_4_custom_script.script```
