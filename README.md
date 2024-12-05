Architecture : Auth Service avec base de données des utilisateurs

    Données stockées dans Auth Service :
    Auth Service stocke uniquement les informations nécessaires à l'authentification :
        username
        password (haché pour la sécurité).

    Les autres détails utilisateur (rôles, profils, etc.) restent gérés par User Service.

    Protocole d'authentification :
        Lorsqu’un utilisateur tente de se connecter, Auth Service :
            Vérifie les informations de connexion (username et password) localement dans sa base de données.
            Si valides, génère un JWT.
            Si non valides, retourne une erreur 401 (Unauthorized).

    Synchronisation avec User Service :
        Lorsqu'un utilisateur est créé ou mis à jour dans User Service, Auth Service est notifié (via un événement asynchrone, ex. Kafka) pour synchroniser ses données.
