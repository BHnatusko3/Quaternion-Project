@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
javac -g -Xlint -Xdiags:verbose  renderer\framebuffer\*.java  &&^
javac -g -Xlint -Xdiags:verbose        renderer\scene\*.java  &&^
javac -g -Xlint -Xdiags:verbose       renderer\models\*.java  &&^
javac -g -Xlint -Xdiags:verbose     renderer\pipeline\*.java  &&^
javac -g -Xlint -Xdiags:verbose          renderer\gui\*.java  &&^
javac -g -Xlint -Xdiags:verbose                       *.java
pause
