./gradlew build
killall java
nohup java -Djava.security.egd=file:/dev/./urandom -jar ./build/libs/minion-0.0.1-SNAPSHOT.jar &