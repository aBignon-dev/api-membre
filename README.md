# Microservice membre-api

Ce microservice, membre-api, permet la gestion des membres et de leurs inscriptions à des événements. Il offre des fonctionnalités de création, lecture, mise à jour et suppression (CRUD) des membres, ainsi que la gestion des inscriptions des membres à des événements.

## Structure de la base de données

### Table membre
Cette table contient les informations relatives aux membres.

Structure :
- id : Identifiant unique du membre (clé primaire).
- adresse : Adresse du membre.
- dateNaissance : Date de naissance du membre.
- mail : Adresse e-mail du membre (unique).
- motDePasse : Mot de passe du membre.
- nom : Nom du membre.
- prenom : Prénom du membre.

### Table inscription
Cette table représente les inscriptions des membres à des événements.

Structure :
- id : Identifiant unique de l'inscription (clé primaire).
- evenementId : Identifiant de l'événement auquel le membre est inscrit.
- membreId : Identifiant du membre qui est inscrit à l'événement.

## Fonctionnalités du microservice

Ce microservice offre les fonctionnalités suivantes :

- **Création de membres** : Permet de créer un nouveau membre avec toutes ses informations requises.
- **Lecture des informations des membres** : Permet de récupérer les informations d'un membre en fonction de son identifiant.
- **Modification des informations des membres** : Permet de mettre à jour les informations d'un membre existant.
- **Suppression des membres** : Permet de supprimer un membre existant de la base de données.
- **Inscription des membres à des événements** : Permet d'inscrire un membre à un événement spécifique.

## Description des composants

### Controllers
- **MembreController** : Ce contrôleur gère les requêtes HTTP relatives aux opérations CRUD des membres. Il définit les points de terminaison pour créer, lire, mettre à jour et supprimer des membres.
- **InscriptionController** : Ce contrôleur gère les requêtes HTTP pour les opérations d'inscription des membres à des événements.

### Services
- **MembreService** : Ce service contient la logique métier pour la gestion des membres. Il interagit avec le repository de membre pour effectuer des opérations CRUD sur les membres.
- **InscriptionService** : Ce service gère la logique métier pour l'inscription des membres à des événements.

### Repositories
- **MembreRepository** : Ce repository gère les opérations de persistance des membres dans la base de données.
- **InscriptionRepository** : Ce repository gère les opérations de persistance des inscriptions des membres à des événements dans la base de données.

### Models
- **Membre** : Cette classe représente l'entité membre avec ses attributs et méthodes associées.
- **Inscription** : Cette classe représente l'entité inscription avec ses attributs et méthodes associées.

### DTOs (Data Transfer Objects)
- **MembreDTO** : C'est un objet de transfert de données utilisé pour transférer les informations des membres entre les différentes couches de l'application.
- **UpdateDTO** : C'est un objet de transfert de données utilisé pour mettre à jour les informations d'un membre.

### Autres classes
- **ErrorResponse** : Cette classe représente un objet de réponse d'erreur lorsqu'une erreur se produit dans l'API.
- **ResponseWrapper** : Cette classe représente un objet de réponse générique utilisé pour encapsuler les réponses des endpoints de l'API.
- **LoginDTO** : C'est un objet de transfert de données utilisé pour transférer les informations de connexion d'un utilisateur.

## Conclusion

Le microservice a pour role d'interagir avec les données des membres et des inscriptions aux événements. Les DTOs sont utilisés pour transférer les données entre les différentes couches de l'application. En utilisant ce microservice, il est possible de créer, lire, mettre à jour et supprimer des membres, ainsi que de gérer leurs inscriptions à des événements.
