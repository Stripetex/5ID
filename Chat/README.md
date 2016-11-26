CHATROOM MULTIUTENTE
=====================

In questo progetto ho creato una chat multiutente utilizzando il protocollo UDP come richiesto nella consegna.

Il programma funziona secondo un sistema di registrazione alla chat del server, il quale permette o nega l'accesso alla stanza al client.

----------
# Server
Il server ha come scopo quello di gestire la registrazione/login dei client e di gestire la comunicazione tra i vari client.

I file riguardanti il server sono:

 - Server.java
 - GestoreServer.java
 - ProtocolServerEnum.java
 - ServerController.java
 - Server.fxml
 
 E sono situati nella directory:

> \src\chat\network\server

----------
# Client

Il client ha lo scopo di collegarsi al server per entrare in contatto con altri client nella rete.

I file riguardanti il client sono:

 - Client.java
 - GestoreClient.java
 - ProtocolClientEnum.java
 - ClientController.java
 - Client.fxml

E sono situati nella directory:

> \src\chat\network\client

----------
#Grafica
Per la grafica è stata ripresa quella del progetto precedente([Robot]), in quanto il concetto di base è sempre lo stesso.

----------
#Librerie
Le librerie usate sono due:

 - [JFoenix]: utilizzato per la grafica;
 - [JDBC]: driver di connessione al database per java.

Entrambi file JAR si possono trovare nella cartella:

> \lib

----------
# JAR FILE
Per eseguire il programma basta eseguire il jar file:
>\jar\Chat.jar

N.B. IL JAR DEVE ESSERE NELLA STESSA DIRECTORY CON LA CARTELLA LIB


----------


Giovanni Scanferla 5^ID  26/11/2016

[Robot]: <https://github.com/zuccante/5ID/tree/Scanferla/Robot>
[JFoenix]:<http://jfoenix.com/>
[JDBC]: <https://bitbucket.org/xerial/sqlite-jdbc>