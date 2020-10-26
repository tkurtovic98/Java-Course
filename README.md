# Java Projects
Repository with projects that were to be done in order to pass an advanced Java course. 
The course was taught by Asst. Prof Marko Čupić at the Faculty of electrical engineering and computer science in Zagreb, Croatia

## The course included
1. Introduction. Java as programming language, Java as platform. First program
2. Classes and objects. References
3. Test-driven development
4. Java collection framework (1)
5. Working with files
6. Java collection framework (2)
7. Multithreaded applications
8. Swing (1)
9. Swing (2)
10. Distributed applications. java.net package
11. Web applications
12. Web forms. Tomcat servlet container. Web security.
13. Additional technologies (ANT, Hibernate, MySQL) 


### Project - 1

This project shows the ability to implement a custom lexer and parser as well as collections with JUnit test.

Collections implemented are Dictionary, List, Stack etc.

### Project - 2

This project shows the ability to implement custom 2D objects and some more collections

### Project - 3

This project has 2 parts.

The 1st part shows the implementation of a system that can build Lyndermayers systems.
It can be run from the lsystems.impl.demo package. 

You might have to run the following command in order to get the source code to compile

mvn install:install-file -Dfile=./lib/lsystems.jar -DgroupId=lsystems -DartifactId=lsystems -Dversion=1 -Dpackaging=jar

lsystems.jar is a library used to draw the fractals


The 2nd part shows the implementation of a database where the user can type in a query and the system then matches that query to display a result from a text file representing a database.

It can be run from the java.hw05.db.demo package.

You can try queries such as:

query jmbag="0000000003"
query   lastName  =    "Blažić"
query firstName>"A" and lastName LIKE "B*ć"query firstName>"A" and firstName<"C" and lastName LIKE "B*ć" and jmbag>"0000000002"

### Project - 4

This project represents an implementation of a custom unix-like shell.

The shell supports the following commands:

**charsets** -> takes no arguments and lists names of supported charsets for your Java platform

**cat** -> one or two arguments. The first argument is path to some file and is mandatory. The
second argument is charset name that should be used to interpret chars from bytes.

**ls** -> a single argument – directory – and writes a directory listing (not recursive)

**tree** -> expects a single argument: directory name and prints a tree (each directory level shifts
output two charatcers to the right).

**copy** -> expects two arguments: source file name and destination file name (i.e. paths and
names)

**mkdir** -> takes a single argument: directory name, and creates the appropriate directory
structure

**hexdump** -> expects a single argument: file name, and produces hex-output as illustrated
below

It also shows code used for cyphering and decyphering files.

### Project - 5

The goal of this project is to implement the **Observer** design pattern. 

It also consist of multiple other assignments such as 
ObjectMultiStack, working with Streams etc.

### Project - 6
To be added...

















