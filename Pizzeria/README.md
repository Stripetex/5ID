# Pizzeria AutOrder Chat

Con questa chat è possibile effettuare degli ordini ad un'ipotetica pizzeria.

La chat offre la possibilità vedere la lista delle pizze con il relativo costo, e aspetta finché non si fa la scelta di quali pizze prendere, a quel punto chiede l'indirizzo dove il fattorino dovrà recarsi per consegnarle e fornisce direttamente il costo totale in modo che il cliente possa già prepararsi i soldi necessari al pagamento.


----------


Client
------
Il client presenta un'interfaccia grafica semplice che aiuta a rendere più gradevole l'utilizzo della chat.

Il Client è strutturato in questo modo:

 - Client.java
 - guiController.java
 - ClientController.java
 - gui.fxml
 - materialDesignText.css
 - *Cartella Font*
 - jfoenix.jar

Per la grafica in stile *Material Design* si fa utilizzo della libreria jFoenix.


----------


Server
------
Il server si basa invece sull'interfaccia a riga di comando per offrire qualche informazione riguardo il collegamento di nuovi utenti.

Il Server è strutturato in questo modo:

 - Server.java
 - ServerTask.java
 - Protocol.java

