# Moteur Couchbase

Tous le code et les données se trouve dans le dossier src/

## Génération automatique des données

La géneration de données dont toutes les données se trouve dans le dossier src/main/java/moteur/couchbase/data

## Chargement des données

Solution utilisée : Ecrire un programme Java avec l’api fournit par le sgbd nosql.

Voici comment le code à était établit.

Le code est une application Java qui se connecte à une instance de base de données Couchbase, lit des données JSON à partir de fichiers, et insère ces données dans la base de données.

Le programme établit une connexion à la base de données en utilisant les identifiants fournis. En cas de succès, il imprime un message de confirmation, sinon, il imprime un message d'erreur et termine l'exécution.

Il utilise ensuite une carte qui associe chaque fichier JSON à une collection dans la base de données. Pour chaque entrée de cette carte, il lit le contenu du fichier, le convertit en un tableau JSON, et crée un scope et une collection dans la base de données si nécessaire.

Si le scope ou la collection existent déjà, il imprime un message indiquant qu'ils n'ont pas été créés. Sinon, il crée le scope ou la collection et imprime un message de confirmation.

Pour chaque objet du tableau JSON, il génère un identifiant de document unique, insère ou met à jour l'objet dans la collection, et imprime un message de confirmation.

Enfin, il ferme la connexion à la base de données et arrête l'environnement du cluster.

## Mise a jour des données et Interrogation des données

complet