# Create Self signed Certificate for Client and Server

### Client Self signed Certificate:

```text
keytool -genkeypair -alias client-service -keyalg RSA -keysize 2048 -storetype JKS -keystore client-service.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1 
```

### Server Self signed Certificate:

```text
keytool -genkeypair -alias server-service -keyalg RSA -keysize 2048 -storetype JKS -keystore server-service.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1
```

Now we have client and server certs. So we need to set up trust between the certs. For gain the trust-ability we will
import client cert into servers trusted certificates and vice-versa.

Before doing this we extract public certificate from the jks files.

# Create public certificate file from Client and Server cert

### Extract public certificate from Client cert

```text
keytool -export -alias client-service -file client-service.crt -keystore client-service.jks
```

### Extract public certificate from Server cert

```text
keytool -export -alias server-service -file server-service.crt -keystore server-service.jks
```

Now, we will have to import client’s cert to server’s keystore and server’s cert to client’s keystore file.

### Import Client Certificate to Server jks file

```text
keytool -import -alias client-service -file client-service.crt -keystore server-service.jks
```

### Import Server Cert to Client jks File:

```text
keytool -import -alias server-service -file server-service.crt -keystore client-service.jks
```

# Configure Server For 2 Way SSL:

1. Firstly, Copy final server jks file (in my case, server-service.jks) to the src/main/resources/ folder of server-service application.
2. Secondly, copy final client jks (in my case client-service.jks) to src/main/resources/ folder of client-service application.

### Add the entries shown below in application.yml into client-service

```yaml
spring:
  application:
    name: client-service

server:
  port: 9001
  ssl:
    enabled: true
    client-auth: need
    key-store: classpath:client-service.jks
    key-store-password: client-service
    key-alias: client-service
    key-store-type: JKS
    key-store-provider: SUN
    trust-store: classpath:client-service.jks
    trust-store-password: client-service
    trust-store-type: JKS
```

### Add the entries shown below in application.yml into server-service

```yaml
spring:
  application:
    name: server-service

server:
  port: 9002
  ssl:
    enabled: true
    client-auth: need
    key-store: classpath:server-service.jks
    key-store-password: server-service
    key-alias: server-service
    key-store-type: JKS
    key-store-provider: SUN
    trust-store: classpath:server-service.jks
    trust-store-password: server-service
    trust-store-type: JKS
```

# Project Output Demo:

> Step - 01 :: Running two Services

1. Client Service Running 
![Client-Service-Running](images/Client-Service-Running.PNG)

2. Server Service Running
![Server-Service-Running](images/Server-Service-Running.PNG)

> Step - 02 :: Call Client Service To Server Service with maintain mutual SSL Communication

1. Hit the API
![Hit The API](images/Hit_On_The_API.PNG)

2. Client Service Console
![Hit The API](images/Client-Service-Console.PNG)

3. Server Service Console
![Hit The API](images/Server-Service-Console.PNG)

> Step - 03 :: Call Client Service To Server Service without maintain mutual SSL Communication

1. Hit the API
   ![Hit The API](images/Hit_On_The_API_2.PNG)

2. Client Service Console
   ![Hit The API](images/Client-Service-Error-Console.PNG)

3. Server Service Console
   ![Hit The API](images/Server-Service-Error-Console.PNG)





