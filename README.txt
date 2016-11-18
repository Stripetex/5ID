La mia idea si basa su un IA auto apprendente, in grado di evolversi quando incontra una frase nuova o collocata in un contesto diverso rispetto a quelli gi� registrati. Funziona utilizzando un grafo come base dati su cui operare. Il grafo � la risorsa condivisa tra i vari utenti. Ho scelto di non utilizzare semafori o tecniche simili per la gestione delle risorse condivise visto le scarsissime possibilit� di conflitto. Essendo l'applicazione rivolta ad un utente umano, con i suoi lenti tempi di reazione, e l'accesso effettivo alla risorsa di molto inferiore al secondo (circa uno due millisecondi) le probabilit� che due utenti lavorino sullo stesso nodo sono bassissime e vanno diminuendo man mano che la base dati si ingrandisce. Un possibile upgrade sarebbe sicuramente quello di effettuare un analisi semantica e sintattica sulle frasi in modo da raggrupparle, infatti ora il programma identifica come due entit� completamente diverse due proposizioni con lo stesso significato (es: Come va? e Come stai?)


Controller 	Usato per gestire la grafica del client e gli eventi ad essa associati

Le seguenti classi compongono l'intelligenza artificiale

DummyNode 		Il singolo nodo del grafo, contiene una frase e i riferimenti alle possibili continuazioni della conversazione

DummyGraph		La base dati su cui si poggia l'IA. Viene salvato su file. Ha il compito di fornire la giusta continuazione della conversazione

DummyIA			L'IA vera e propria. Contiene il riferimento al nodo attuale, quindi quello che rappresenta l'ultima frase della conversazione. Ha il compito di richiamare i metodi per spostarsi sul grafo e aggiungere un nuovo nodo quando � necessario 

Classi per la connessione

DummyRobotClient	Avvia la grafica e permette all'utente di collegarsi al server	

DummyRobotServer	Il server che fornisce i servizi ai vari utenti

SessionProtocol		Ha il compito di gestire la connessione tra client e server



UML			Rappresentazione grafica a grandi linee dell'IA

Grafica realizzata grazie ad aiuto di @Rene Cabbia
