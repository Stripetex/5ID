Server:
L'algoritmo all'avvio del server va a leggere da un file le informazioni e le memorizza all'interno di un grafo. Resta in ascolto e, ogni volta che si connette un client, crea la relativa sessione che contiene l'interfaccia tra server e grafo. Quando viene ricevuto un messaggio esso viene ricercato il nodo che lo contiene. Se esiste il server risponderà con la frase presa casualmente da uno dei nodi collegati, altrimenti verrà aggiunto il nodo e risponderà "non lo so" richiedendo un esempio. Realizzato con l'aiuto di Cozzi Enrico.

Client:
Una volta che l'utente ha cliccato sul tasto "connetti" verifica che i campi del nome utente, indirizzo ip e porta non siano vuoti e si connetterà. Nel caso si voglia connettersi ad un altro host basterà riscrivere i dati e cliccare nuovamente sul tasto.
