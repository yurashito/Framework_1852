set path_tomcat= E:\apache_tomcat9\webapps\
set path_actuel=E:\Sprint11\

rmdir /s /q test_framework
mkdir test_framework
cd  %path_actuel%test_framework
mkdir WEB-INF
cd WEB-INF
mkdir lib classes
cd  %path_actuel%framework\WEB-INF\classes 
javac -d . *.java  
jar cf Framework.jar ./*
move Framework.jar %path_actuel%testFramework\WEB-INF\lib\

cd  %path_actuel%testFramework/WEB-INF/classes/ 
javac  -cp ../lib/Framework.jar -d . *.java 
xcopy %path_actuel%testFramework\WEB-INF\web.xml %path_actuel%test_framework\WEB-INF\ 
xcopy %path_actuel%testFramework\*.jsp %path_actuel%test_framework\ /E /y
xcopy %path_actuel%testFramework\WEB-INF\classes\*.class %path_actuel%test_framework\WEB-INF\classes\ /E /y
xcopy %path_actuel%testFramework\WEB-INF\lib\*.jar %path_actuel%test_framework\WEB-INF\lib\ /E  /y

cd %path_actuel%test_framework
jar cvf test_framework7.war ./*
move test_framework7.war %path_tomcat%
pause


@REM destination en utilisant l'option /s pour inclure tous les sous-répertoires et leurs fichiers, 
@REM et l'option /y pour écraser les fichiers existants sans demander de confirmation
