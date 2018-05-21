# social-media-aggregator
## Sistema de detección de mensajes en redes sociales basados en busquedas por usuarios
La idea es construir un servicio agregador de entradas de APIs sociales, en este caso Twitter.

![alt text](https://github.com/olamas/social-media-aggregator/blob/master/social-media-aggregator.jpg)

Social media aggregator es un proyecto multi-modulo maven.
Los módulos son:

- social-media-aggregator-common

- social-media-aggregator-backend

- social-media-aggregator-frontend

- social-media-aggregator-stream-api

## Instalación y Primeros Pasos

Primero hacer un build del proyecto parent como:
```
social-media-aggregator$ mvn clean install
```
```
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO]
[INFO] social-media-aggregator-common 
[INFO] social-media-aggregator-stream-api
[INFO] social-media-aggregator-frontend
[INFO] social-media-aggregator-backend
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building social-media-aggregator-stream-api 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------


[INFO] Reactor Summary:
[INFO]
[INFO] social-media-aggregator-common...................... SUCCESS [  0.128 s] 
[INFO] social-media-aggregator-stream-api ................. SUCCESS [ 22.013 s]
[INFO] social-media-aggregator ............................ SUCCESS [  0.128 s]
[INFO] social-media-aggregator-frontend ................... SUCCESS [01:30 min]
[INFO] social-media-aggregator-backend .................... SUCCESS [ 17.055 s]
[INFO] ------------------------------------------------------------------------


```
Para generar informacion en el sistema, necesitamos correr un proceso "daemon" que permita obtener tweets filtrados de acuerdo a las preferencias del usuario del stream de datos de Tweeter.

Como primer paso ,si todo el build esta ok, navegar hasta la carpeta social-media-aggregator-stream-api y ejecutar:
```
social-media-aggregator-stream-api$ mvn java -jar target/social-media-aggregator-stream-api-1.0-FINAL-exec.jar

```
El servicio se conecta al configuration manager (zookeeper) para buscar nodos de información (/social/users) relacionadas con el cliente:
```
2018-05-21 02:28:40.230  INFO 31626 --- [pool-3-thread-1] c.o.s.stream.api.twitter.TwitterService  : Adding filter : batman
2018-05-21 02:28:40.237  INFO 31626 --- [pool-3-thread-1] c.o.s.stream.api.twitter.TwitterService  : Starting service reading tweets ......
```
NOTA: el build del proceso es generado como "fully executable" para aplicaciones en sistema Unix. (ver implementación como servicio).

  ```  
  service social-media-aggregator-stream-api start/status/stop
  
  ```

## Ejecutar servicios REST

Navegar hasta la carpeta social-media-aggregator-backend y ejecutar :

```
social-media-aggregator-backend$  mvn clean spring-boot:run
```

El proyecto corre en una instancia de Tomact server.
Entonces se exponen los siguientes servicios :

###### POST /oauth/token
Servicio de autenticación.Se genera un token despues que el usuario es validado contra el servidor de autenticacion.
```
{
    "access_token": "91d4152d-6058-41cc-b18b-bbcb7bdea17c",
    "token_type": "bearer",
    "refresh_token": "d35cb4ed-7ae1-4a17-a2cf-1be352618a7c",
    "expires_in": 43199,
    "scope": "read write trust"
}
```

###### POST /twitter/filter/
```
{
    "userName": "bruceWayne",
    "filterText": "batman",
    "fromUser": "",
    "validFilter": true
}
```


###### GET twitter/tweets/{user}/
```
[
    {
        "id": 1,
        "userFilter": "bruceWayne",
        "filter": "macri",
        "idStr": "998448566766063616",
        "text": "RT @hf183896: Como no me gusta mentir, confieso que voté a Cambiemos con Macri de Presidente, como el peor de los males Hoy, estoy convenci…",
        "fromUser": "chabela19731"
    }
]
```
## FrontEnd - Aplicación Social - Media - Aggregator

