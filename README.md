###running the test

`sbt run` 

###maintaining the list of test projects:

The projects to be used for the testing are placed under `test-projects`, and they are managed as [git submodules](https://git-scm.com/docs/git-submodule).

##Adding a project:
`git submodule add <git clone link> test-projects/<project name>`. E.g. `git submodule add git@github.com:allenai/pipeline.git test-projects/pipeline`