# CHAT WITH MEREDITH

Questo bot è stato ideato per chattare in modo più o meno semplice di argomenti generici scelti precedentemente dall'utente.

Lo scopo del progetto è stato quello di creare un protocollo che fosse accessibile da tutti gli utenti, anche i meno esperti.
Il protocollo si basa infatti su il linguaggio AIML (Artificial Intelligence Modelling Language) basato su XML.

  - Guida utilizzata: [Guida AIML]

Questo linguaggio nasce sul concetto di pattern e template, cioè per ogni domanda(pattern) si ricava la risposta indicata nel file in precdenza(template).

# AIML TAG
I TAG AIML sono pochi ma possono essere sfruttati per creare molteplici logiche e combinazioni. Vedere il link sopra per la spiegazione. L'unico omesso è stato il TAG 
```sh
<topic>
```
# GRAFICA
La grafica è stata fatta in modo da integrare sia Client che Server sullo stesso progetto. Si basa quindi su una schermata principale che ci permette sia di creare un Server, che di connetterci con un Client ad un indirizzo locale o esterno.

# SERVER
I file del server sono nella directory
>\src\robot\network\server
E sono:
- GestoreServer.java: si occupa di gestire l'interazione tra il server e l'interfaccia(Controlle);
- Server.java: si occupa di gestire le richieste client e elaborare le risposte per ciascuno(multithreading con ExecutorService);
- ServerConsoleController.java: gestisce la grafica della console del Server;
- ServerConsole.fxml

# CLIENT
I file del client sono nella directory
>\src\robot\network\client
E sono:
- GestoreClient.java: si occupa di gestire l'interazione tra il client e l'interfaccia(Controlle);
- Client.java: si occupa di inviare al server le richieste;
- ClientController.java: gestisce la grafica della chat Client;
- Client.fxml

# JAR FILE
Per eseguire il programma basta eseguire il jar file:
>\jar\Robot.jar

N.B. IL JAR DEVE ESSERE NELLA STESSA DIRECTORY CON LA CARTELLA LIB E LA CARTELLA AIML

# LIBRERIA USATA
Per simulare il Material Design su JavaFX ho utilizzato la libreria JFoenix
Il jar usato è nella directory
>\lib\jfoenix.jar

Link al sito: [JFoenix]

Scanferla Giovanni 20\11\2016 -- il ritardo è causato da  problemi non ancora ora risolti

   [Guida AIML]: <https://www.tutorialspoint.com/aiml/>
   [JFoenix]: <http://jfoenix.com/>
