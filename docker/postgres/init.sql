-- Exécuté UNE SEULE FOIS par l'image postgres, au premier démarrage
-- (quand le volume postgres-data est encore vide).
-- Une base par microservice : chaque service possède ses propres données.
CREATE DATABASE productdb;
CREATE DATABASE orderdb;
