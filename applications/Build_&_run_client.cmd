@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
javac.exe -cp .;renderer_8.jar  %1
java      -cp .;renderer_8.jar  %~n1
pause
