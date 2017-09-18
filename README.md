# Eccp2Java

Projeto de integração com o Elastix usando o protocolo ECCP.


Instalação
----------

O projeto está disponível no Nexus da empresa: http://192.168.1.11:8092/nexus/.
Para usar, configurar a dependência no `pom.xml`:

``` xml 
 <dependency>
    <groupId>br.com.xbrain</groupId>
 	<artifactId>Eccp2Java</artifactId>
    <version>{numero_da_versao}</version>
 </dependency>
```


Tecnologias
-----------
Java8, JPA/Hibernate, MySQL Database


GIT Workflow
-----------
A organização de branches e padrão de commits deve ser seguido conforme o documento:

[Git WorkFlow X-Brain](https://docs.google.com/document/d/1oVzpbnLO7V-Nl-5cegE3nMF6ZYErsns9rQsKy2MFINs/pub#h.ytnw7m7yf7nk)


IDE
-----------
Por ser tratar um de um projeto MAVEN, pode ser utilizada qualquer IDE para desenvolvimento.
Sendo assim, não deve ser comitado nenhum arquivo específico da IDE. (nb-config, .idea, etc). 
