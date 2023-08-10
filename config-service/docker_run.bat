docker run -it --rm --name config-service ^
-e "SPRING_PROFILES_ACTIVE=docker,native" ^
-e "CONFIG_USER=cfgusr" ^
-e "CONFIG_PASS=cfgpass" ^
-e "EUREKA_USER=euser" ^
-e "EUREKA_PASS=epass" ^
-p 8888:8888 ^
lapotkod/config-service:0.0.1
