# Problem Statement
This code is meant to solve the problem stated here -> https://codu.ai/coding-problem/mymoney.

Your objective is to improve the code, make sure all output cases are covered, and unit tests pass. 

# Pre-requisites
* Java 1.8/1.11/1.15
* Maven

# How to run the code

We have provided scripts to execute the code. 

Use `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.

Internally both the scripts run the following commands 

 * `mvn clean install -DskipTests assembly:single -q` - This will create a jar file `geektrust.jar` in the `target` folder.
 * `java -jar target/geektrust.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file as the command line argument

 Use the pom.xml provided along with this project. Please change the main class entry (<mainClass>com.example.geektrust.Main</mainClass>) in the pom.xml if your main class has changed.

 # How to execute the unit tests

 `mvn clean test` will execute the unit test cases.

# Help

You can refer our help documents [here](https://help.geektrust.in)
You can read build instructions [here](https://github.com/geektrust/coding-problem-artefacts/tree/master/Java)

# Improvements Made

In the process of improving the code, I identified and fixed a bug that was not explicitly mentioned in the requirements. 
The bug caused incorrect calculation of investment values in certain scenarios. 
By making the necessary adjustments in the code, 
I ensured accurate investment calculations and improved the overall reliability of the solution.

I also made the following enhancements to the code:

- Refactored the code to adhere to clean code principles like:
  - Reduced code complexity
  - Used dedicated value objects instead of primitive types
  - Found out correlation between variables and grouped them into objects and properties
  - Utilised Polymorphism to replace switch cases
  - Followed scout rule, fixed existing issues, removed unnecessary code
  - Used explanatory variable names
  - Replaced magic numbers with constants
  - Decomposed larger functions
- Fixed existing and added additional unit tests for all the classes and edge cases.