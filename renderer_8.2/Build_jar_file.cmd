@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
jar cvf renderer_8.jar  renderer\scene\*.class  renderer\models\*.class  renderer\pipeline\*.class  renderer\quaternions\*.class  renderer\framebuffer\*.class  renderer\gui\*.class
pause
