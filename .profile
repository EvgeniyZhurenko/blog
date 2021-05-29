web: java $JAVA_OPTS -jar target/*.jar --spring.profiles.active=YOUR_PROD_PROFILE
$ echo unset JAVA_TOOL_OPTIONS