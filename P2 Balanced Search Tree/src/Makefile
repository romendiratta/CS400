.PHONY =  junit5 junit4

junit5: BALST.java BALSTTest.java
	javac -cp .:./classes/:junit-platform-console-standalone-1.3.2.jar *.java

balst: junit5
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -c BALSTTest

all: junit5
	java -jar junit-platform-console-standalone-1.3.2.jar --class-path .:./classes/ -p ""

clean:
	rm -f *.class
