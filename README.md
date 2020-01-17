# ChatPeerToPeer
Cette application est un chat peer to peer.

Au démarrage on lance le seveur local puis on scan le réseau local.
Une fois ceci terminer, un fenetre s'ouvre avec en haut un liste des appereil connectés sur le réseau.

Si l'un d'entre eux utilise l'application, il suffit de sélectionner son adresse ip de la machine.

Les applications vont s'auto connectés.

C'est à dire que notre application va créer une socket pour se connecté au serveur de l'autre machine.
Puis à la réception de cette demande, l'autre application va demander la connection à notre serveur.
Lorsque celui-ci l'accepte, les deux machine échange les nom utilisateur.
Le champ texte du bas se dévérouille.
Puis le chat est fonctionnel.
Le texte saisie dans ce champ va être transmit à l'autre machine par la socket qui a demander la connection.
L'autre socket se charge de réceptionner les messages.

Pour lancer l'application : extraire le fichier userSave.properties dans le même répertoire que le jar.
Puis lancer l'application avec: java -jar <nom du jar>

