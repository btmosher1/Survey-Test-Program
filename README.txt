SurveyTest
Written by Brendan Mosher in Java


Description:
This is a java program I originally wrote for a class. The purpous
of the assignment was to teach us how to design and organize our code in a way
that saves time in rewritting code and is also easy to follow.

The program itself allows the user to create and take surveys and tests. Tests are
an extention of surveys that include the ability to grade the user responses.

Surveys are saved as .survey files, Tests are saved as .test files, and results are
saved as .result files.

If there are multiple results for a single survey/test you can tabulate the responses
to see all of them at once.


How To Run:
To run this program, place all .java files, and the Makefile in the same directory. On
a commmand line, reach this directory and enter "make run" to launch the program. All
menus and interactions are done on the command line. I've included an example survey and
test, plus results for both. These also need to be inlcuded in the directory if you wish
to use them.

After quiting enter "make clean" to remove all .class files from the directory.


Instructions:
The first menu will ask you wether you wish to work with surveys or tests.

From here you are presented with a list of options. I have provided a sample test and survey
with some results to play with.

To work with the example, select "Load a Survey/Test" and select the only option in the list.
From here you are able to display, take, modify, or tabulate the results of the survey/test you
selected. For tests, you also have the option to see grades for result files.

IMPORTANT - You must load or create a survey/test before you can use any other menu options. 
If you create a new survey/test you must select "Save a Survey/Test" before you can work with
it in any of the other menu options. Otherwise the file will not be created and your work will
be lost.


Questions:
There are 6 question types in this program:
	Essay
	Matching
	Multiple Choice
	Ranking
	Short Answer
	True/False

Each of these implement the Question class and some such as T/F and MC are also connected in the class
hierarchy.

When taking surveys/tests the program will give you prompts on how to enter the information and
answers for each of the question types. This is also true for when creating the questions.


Tabulation:
This is the last major feature of the program. This allows you to see all responses given to a single
survey/test at once. If the same answer was given multiple times a counter will appear next to it showing
the number of responses that match.

Simply select "Tabulate a Survey/Test" from the menu and select from the list the survey/test you wish
to see answer for.