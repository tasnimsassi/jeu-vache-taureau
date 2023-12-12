##jeu Vache-Taureau
Ce projet propose une implémentation du jeu de logique "Vache-Taureau" utilisant les concepts de serveur-client et les communications RMI (Remote Method Invocation).

##Description
Le jeu Vache-Taureau est un jeu de devinette basé sur la logique. Le joueur essaie de deviner un nombre à quatre chiffres généré par le serveur. Pour chaque tentative, le serveur renvoie des indices sous forme de "Vaches" et "Taureaux" :

Vache : Chiffre correct mais à la mauvaise position.
Taureau : Chiffre correct et à la bonne position.
L'objectif du joueur est de deviner le nombre avec le moins de tentatives possibles.

##Fonctionnalités
Serveur : Implémente la logique du jeu, génère un nombre à deviner et évalue les tentatives des joueurs.
Client : Permet au joueur de saisir ses suppositions et communique avec le serveur pour obtenir des réponses.
##Comment jouer
Exécutez le serveur en premier.
Exécutez le client et suivez les instructions pour saisir les suppositions.
Le serveur renvoie les indices de "Vaches" et "Taureaux" après chaque supposition.
Continuez jusqu'à ce que le nombre soit deviné correctement.
Le serveur indiquera le nombre de tentatives et le meilleur score obtenu.
##Installation
Prérequis
Java Development Kit (JDK)
Éditeur de code (par exemple, IntelliJ IDEA, Eclipse)
