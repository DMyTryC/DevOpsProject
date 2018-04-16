# DevOpsProject

## Project explanation

This is a DevOps project consisting in creating a Java library similar to the "pandas" package for Python.
The idea was to code some show/selection/statistics functions that will work similarly to those in the Python package. Some other functions were also created.
A full list of functions can be found lower.

Project authors : Stephanie BARONA ANDRADE, Riad BENOUNNAS, Baptiste BLEUZE, Dumitru CORINI

## Architectural decisions
A decision was made early into the project to create a hashmap structure in which the data will be stored. After some tests on hashmaps, we realised that a hashmap will organise the data using a hash. This means that the data will not be organised as the data is added.
This was solved by adding a list containing the organisation of the additions. For each label we will have a index of addition order.
The list structure was used mainly for the selection function that needs the labels between an inferior and superior label.
The linesNumber defines the highest column which is needed when trying to show a number of lines. It was decided that if the user wants to show a number of lines that is bigger than the maximum of the dataframe, an error will pop up.

For the dataframe creation:
The user can create a dataframe :
* with no data.
* from a list of labels and a list of elements, each list being associated with a label.
* from a file, by also specifying the data separator.

## Type decisions
We decided that only three types of data can be read from a file : Integer, Float and String.
For a file, the type of each column is defined by the first element of that column. 
Blank data and spaces are permitted except for the first element of each row because of the decision on the previous line.

## Functions
### Display
* show()										: shows the dataframe
* head(int n) 									: shows the first n lines of the dataframe
* head(String label, int n) 					: shows the first n lines of the dataframe for the label
* tail(int n)									: shows the last n lines of the dataframe
* tail(String label, int n)						: shows the last n lines of the dataframe for the label
* showLabels()									: shows the labels of the dataframe
### Selection
* loc(String label)								: selects a subset of the dataframe associated to the label
* loc(List<String> labels)						: selects a subset of the dataframe associated to the list of labels
* loc(String ... labels)						: selects a subset of the dataframe associated to the multiple labels
* loc(String infLabel, String supLabel)			: selects a subset of the dataframe between the inf label and the sup label
* iloc(int index)								: selects the line in the dataframe at the index
* iloc(List<Integer> indexes)					: selects the lines in the dataframe at the indexes in the list
* iloc(Integer ... indexes)						: selects the lines in the dataframe that are at the indexes
* iloc(Integer infIndex, Integer supIndex)		: selects the lines in the dataframe between inf and sup
### Statistics
* mean(String label)							: calculates the mean of the column label
* min(String label)								: finds the minimum of the column label
* max(String label)								: finds the maximum of the column label
* showStatistic(String label)					: shows mean, minimum and maximum for the column label
* size()										: finds the number of cells in the dataframe
* getMaxColumnSize()							: gives the size of the biggest column
* count(List list, Object element)				: calculates the occurence of the element in the list
### Data analysis
* groupby(label)								: groups the data depending on the label
* groupby(String[ ] labels)						: groups the data depending on the labels
* groupby(String[ ] labels, String aggregate)	: groups the data depending on the labels then applies a function on the data
* groupby(String label, String[ ] aggregate)	: groups the data depending on the label then applies functions on the data

## Tools used :
* Github - For collaborative development
* TravisCI - For the continuous integration
* Maven - For the automatization and management of the project
* JUnit - For testing
* Jacoco - For the code coverage

## Feedback on the tools used
* Github :
	This tool was mainly used to put the code of each collaborator together. Each collaborator worked on a different branch, while the "master" branch of the project was containing a stable version at any time.
	When a collaborator would want their changes added to the master branch, they would make a "pull request", which would be verified for compatibility with the master branch by the TravisCI.
* TravisCI :
	Used as a tool that would test the goals that were defined in the maven "pom.xml" file. If one of the goals would fail, it would stop a pull request or a push to the remote repository and would ask the collaborator to check the files.
* Maven :
	Mainly used for automating the tests and putting all the other tools together through defining goals to execute. This is also used by TravisCI to check the correct structure during pushes and pull requests.
* JUnit :
	Used for creating unit tests. We tried making tests for each case that is possible.
* Jacoco :
	Used for verifying what lines and branches weren't tested yet.

## Utils

### Maven use
For this command we can specify a parameter -Tx (x being the number of threads) that will execute the command with the parallelisation proprerty

mvn [life cycle]

Life cycles : 
-------------
The execution of any life cycle will execute any life cycle before it

* validate : validates that the project is correct and that all the needed infos are available
* compile : compiles the sources
* test : starts the tests
* package : packages the compiled sources to a distributed format (ex : jar)
* integration-test : integration tests
* verify : tests the quality of the package
* install : installs the packages into the local repository
* deploy : copies the final package into the remote repository

Note : These were taken from the Thomas Ropars DevOps class regarding the continuous integration

Code coverage :
---------------
The code coverage is made with the Jacoco maven plugin.
To find the code coverage : Execute "mvn site" which will generate a website found at "target/site/index.html"


How to push to a repository :
-----------------------------
Step 1. Make sure you are working on a branch 
	if you don't work on a branch : 
		git branch : list current branches
		git branch "nameBranchLocal" : create a branch with "nameBranchLocal"
		git checkout "nameBranchLocal" : move to the "nameBranchLocal"

Step 2. Commit changes of your local repository
		
Step 3. Fetch and merge the changes to the current branch
		git remote -v : list the remote repositories
		git fetch "nameRemoteRepo" : fetches the files made to the remote repository "name"
		git merge "nameRemoteRepo"/"nameBranchRemote" "nameBranchLocal" : Merges the files on the "nameRemoteRepo"/"nameBranchRemote" with the "nameBranchLocal" branchs
		
Step 4. Execute maven and clean generated files from pushing
		mvn install : lets you execute the test life cycle which will test your files for errors
		bash scriptCleanUp.sh or ./scriptCleanUp.sh : clears the local repository cache from the files that were generated
		Note : This will not delete them from your local repository, it will just enforce the .gitignore rules
				Explanation : The files that are written into the .gitignore will still be commited if they are present in the cache of the git repository
				
Step 5. Push the changes to the remote branch
	Recommit if needed
	Push the changes to the remote repository : 
		git push "nameRemoteRepo" "nameBranchLocal" : This creates a remote repository branch with the same name as the local branch (if it doesn't exist), if it does exist, will push to the remote branch otherwise
		