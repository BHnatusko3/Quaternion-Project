@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
javadoc  -d html  -link http://docs.oracle.com/javase/8/docs/api/  -linksource -subpackages -quiet -nohelp -nosince -nodeprecatedlist -nodeprecated -version -author -overview renderer/overview.html  -tag param -tag return -tag throws  renderer.scene  renderer.models  renderer.pipeline  renderer.framebuffer  renderer.gui
pause
