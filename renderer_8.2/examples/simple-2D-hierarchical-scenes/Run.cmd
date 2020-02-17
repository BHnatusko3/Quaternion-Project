@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
java -cp .;renderer_8.jar  ShowModel_1
java -cp .;renderer_8.jar  ShowModel_2
java -cp .;renderer_8.jar  ShowModel_2a
java -cp .;renderer_8.jar  ShowModel_4
java -cp .;renderer_8.jar  ShowModel_4a
java -cp .;renderer_8.jar  ShowNestedModels_36
pause
