cd framework/WEB-INF/classes/ 
javac -d . *.java  
jar cf Framework.jar ./*
move Framework.jar ../../../testFramework/WEB-INF/lib/
cd ../../../
cd testFramework/WEB-INF/classes/ 
javac  -cp ../lib/Framework.jar -d . *.java 
cd ../../../
cd testFramework/
jar cvf testFramework22.war ./*
move testFramework22.war ../../
pause
