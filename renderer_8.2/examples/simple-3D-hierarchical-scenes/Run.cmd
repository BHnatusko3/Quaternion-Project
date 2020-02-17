@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
java -cp .;renderer_8.jar  NestedModels_v1
java -cp .;renderer_8.jar  NestedModels_v1a
java -cp .;renderer_8.jar  NestedModels_v2
java -cp .;renderer_8.jar  NestedPrisms_v1
java -cp .;renderer_8.jar  NestedPrisms_v2
java -cp .;renderer_8.jar  NestedPrisms_v3
pause
