@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
javac -g -Xlint -Xdiags:verbose -cp .;renderer_8.jar  *.java
pause
