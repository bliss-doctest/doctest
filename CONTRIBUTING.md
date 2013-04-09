# How to contribute

The community is essential to spread the bliss of development. We improve 
our open source libraries as far as we need to use them in our projects. But 
we can't think of all the use cases you might have. So we'd like to have you
participating in our bliss. We want to keep it as easy as possible to contribute 
changes. There are just a few guidelines that we need contributors to follow 
so that we can have a chance of keeping on top of things.
If you want to be part of this project and looking for a opportunity please 
have a look at our [TODO's](http://devbliss.github.com/doctest/TODO.html).

## Writing issues

* Submit a new ticket for your issue, assuming one does not already exist. [link](https://github.com/devbliss/doctest/issues/new)
* Clearly describe the issue including steps to reproduce when it is a bug.

## Making Changes

* Fork this repository into your own github account [see this link](https://help.github.com/articles/fork-a-repo)
* Create a local copy of this repository on your machine. `git clone git@github.com:{your_username}/doctest.git`
* Create a topic branch from where you want to base your work. `git checkout -b {branchname}`
  * This is usually the master branch.
  * Only target release branches if you are certain your fix must be on that branch.
  * Please avoid working directly on the `master` branch.
* Import the `doctest-format.xml` (Eclipse IDE format definitions) file into your IDE of choice to have the correct file formatting in place.
* Make commits of logical units. `git add {file}; git commit -m "{commit message}"`
* Make sure your commit messages are informative.
* Make sure you have added the necessary tests for your changes.
* Run _all_ the tests to assure nothing else was accidentally broken. `mvn test`
* Add your contribution information
  * Add your name and email to the contributors section in the pom.xml
  * If you added new files make sure you added your name to it with the author tag `@author`
  * If you added new files make sure you added a license of your choice to that file.

## Submitting Changes

* Push your changes to the topic branch in your fork of the repository. `git push origin {branchname}`
* Submit a pull request to the repository in the devbliss organization. [see this link](https://help.github.com/articles/creating-a-pull-request)
* Make a informative pull request description.
* Please don't add information to the `CHANGES.md`. 