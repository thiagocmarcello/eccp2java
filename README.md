# Eccp2Java

Projeto de integração com o Elastix/Issabel usando o protocolo ECCP.


##Instalação
------------

O projeto está disponível no Nexus da empresa: http://192.168.1.11:8092/nexus/.
Para usar, configurar a seguinte dependência no `pom.xml`:

``` xml 
 <dependency>
    <groupId>br.com.xbrain</groupId>
 	<artifactId>Eccp2Java</artifactId>
    <version>{numero_da_versao}</version>
 </dependency>
```

##Utilização
----------

Para utilizar o projeto, você precisa criar uma instância de um EccpClient.

``` java
EccpClient client = new EccpClient(ElastixLoginData.create(ELASTIX_HOST_IP, DEFAULT_ELASTIX_TEST_PORT, ECCP_USER, ECCP_PASSWORD)).connect();
```

Cada usuário do discador é representado por uma instância de um AgentConsole.

``` java
AgentConsole ac = client.createAgentConsole("Agent/7006", "7006", 0000);
```

Para ouvir os eventos gerados pelo Elastix/Issabel, basta que você adicione uma instância de `IEccpEventListener` à instância do AgentConsole.

# Tecnologias
-----------
Java8, JPA/Hibernate, MySQL Database


IDE
-----------
Por ser tratar um de um projeto MAVEN, pode ser utilizada qualquer IDE para desenvolvimento.
Sendo assim, não deve ser comitado nenhum arquivo específico da IDE. (nb-config, .idea, etc). 
