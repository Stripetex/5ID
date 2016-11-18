# CHATBOT LAZZARATO

Questo "ChatBot" è molto semplice, ed è modellato sul progetto "Knock Knock" visto in classe.
Il protocollo è stato riscritto per rispondere a richieste molto semplici, e simula una chat
con un'intelligienza artificiale.

Il client è rimasto immutato, mentre il server è stato riscritto per la gestione concorrenziale dei client.
Per ogni task (client) viene creato un thread dedicato, in quanto prevedo l'utilizzo di pochi client alla volta.

L'applicativo gira su DOS, avendo interfaccia a riga di comando.