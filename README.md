# Qadar — Foot arcade 1 contre 1 en Java

Projet **2D Game** (Epitech, module B-OOP-301) : un jeu de football arcade en 1 contre 1, vu de dessus, développé en Java 21 avec JavaFX.

Deux joueurs s'affrontent sur le même clavier. Chacun choisit son maillot et un super-pouvoir (Hulk ou Pépé), puis tente de marquer plus de buts que son adversaire. Le jeu s'inspire de l'esprit d'*Inazuma Eleven* : un football nerveux où les capacités spéciales des personnages sont au cœur du gameplay.

## Sommaire

1. [Le jeu](#le-jeu)
2. [Comprendre les idées avant le code](#comprendre-les-idées-avant-le-code)
3. [Choix techniques](#choix-techniques)
4. [Répartition des tâches](#répartition-des-tâches)
5. [Installation et lancement](#installation-et-lancement)
6. [Tests](#tests)
7. [Javadoc](#javadoc)
8. [Auteurs et remerciements](#auteurs-et-remerciements)

## Le jeu

- **Mode** : duel 1 contre 1 sur un seul clavier, terrain vu de dessus (fenêtre 1200 x 700).
- **Physique** : accélération et frottements des joueurs, rebonds du ballon sur les murs, collisions par hitbox.
- **Ballon** : récupération, tir, passe et tacle (vol de balle).
- **Pouvoirs** :
  - **Hulk** (attaque) : la puissance de tir passe à 75 pendant la durée d'activation.
  - **Pépé** (défense) : l'adversaire est gelé pendant la durée d'activation.
- **Cartes** : Classique (terrain libre), Piliers (quatre piliers au centre), Barres (deux barres qui coupent le milieu).
- **Réglages de partie** : durée (1, 2, 3 ou 5 min), nombre de buts pour gagner (illimité, 3, 5, 10), carte, mort subite.

### Commandes

| Action | Joueur 1 (gauche) | Joueur 2 (droite) |
|---|---|---|
| Déplacement | Z Q S D | Flèches |
| Tir | E | M |
| Passe | R | L |
| Tacle | Espace | K |

## Comprendre les idées avant le code

Toute la réflexion qui a précédé l'écriture du code est dans le dossier [`docs/`](docs/) :

- [`docs/Game_Design_Document_2D.pdf`](docs/Game_Design_Document_2D.pdf) : le Game Design Document (style de jeu, éléments techniques, level design).
- [`docs/GDD.txt`](docs/GDD.txt) : la version texte du GDD, avec le rôle de chaque classe.
- [`docs/DIAGRAM_UML.pdf`](docs/DIAGRAM_UML.pdf) : le diagramme UML des classes et de leurs relations.

Nous conseillons de lire ces documents avant de parcourir le code : ils expliquent pourquoi le projet est organisé ainsi.

## Choix techniques

- **Java 21** : langage imposé par le sujet, orienté objet, avec les `record`, `switch` modernes et une bonne prise en charge de JavaFX.
- **JavaFX 21** : proposé par le sujet comme outil d'interface. Il fournit la fenêtre, la boucle de jeu (`AnimationTimer`), le dessin sur `Canvas`, la gestion du clavier, les animations (`PauseTransition`) et le son (`javafx-media`).
- **Maven** : gestion des dépendances, compilation, tests et empaquetage reproductibles sur toutes les machines, avec un `pom.xml` unique.
- **JUnit 5** : framework de tests unitaires standard en Java, bien intégré à Maven via Surefire.
- **JaCoCo** : mesure de la couverture de tests, car le sujet demande de s'assurer de la conformité du code ajouté.
- **Maven Shade** : produit un jar exécutable unique (`Qadar.jar`) qui embarque toutes les dépendances.
- **Architecture POO** : héritage (`Entite`, `Joueur`, `Personnage`, `Pouvoir`), interfaces (`Controleur`, `CarteStade`, `PageSelection`), classes abstraites et fabriques (`FabriqueJoueurs`, `FabriqueControleurs`). Le moteur (`engine`, `models`) est séparé de l'affichage (`front`, `view`).

## Répartition des tâches

| Rôle | Membre | Périmètre |
|---|---|---|
| **Game design** (moteur et logique) | [@amd1.7](https://github.com/amd1.7) | Moteur de jeu et `MatchManager`, physique (vecteurs, hitbox, collisions), joueurs, ballon, possession, contrôleurs, pouvoirs Hulk et Pépé, règles de match, tests |
| **World design** (niveaux et interface) | [@ahmednourmeziane48-ui](https://github.com/ahmednourmeziane48-ui) | Cartes et obstacles, direction artistique, spritesheets et animations, menu, pages de sélection, HUD, tableau des scores, rendu du match |

## Installation et lancement

Le jeu s'installe en local à partir des sources.

### Prérequis

- **JDK 21** ou plus récent (vérifier avec `java -version`).
- **Apache Maven 3.8+** (vérifier avec `mvn -version`).
- **Git** pour récupérer le projet.
- Une connexion internet au premier lancement, pour que Maven télécharge les dépendances.
- Un environnement graphique (le jeu ouvre une fenêtre JavaFX).

### Dépendances (gérées automatiquement par Maven)

| Dépendance | Version | Usage |
|---|---|---|
| `org.openjfx:javafx-controls` | 21.0.2 | Interface graphique |
| `org.openjfx:javafx-media` | 21.0.2 | Audio |
| `org.junit.jupiter:junit-jupiter` | 5.10.2 | Tests (portée `test`) |

### Installation

```bash
git clone <URL_DU_DEPOT>
cd B-OOP-301-PAR-3-1-2dgame-10
mvn clean package
```

### Lancement

```bash
java -jar Qadar-V1.0.0.jar
```

Ou directement avec Maven :

```bash
mvn javafx:run
```

## Tests

Les tests unitaires sont écrits avec **JUnit 5** et se trouvent dans `src/test/java/com/game/models/`. Pour chaque classe, ils vérifient les cas normaux (le comportement attendu fonctionne) et les cas anormaux (les entrées invalides sont bien rejetées ou donnent le résultat prévu).

| Dossier | Classe de test | Ce qui est testé |
|---|---|---|
| `physics/` | `Vector2DTest` | Addition, soustraction, multiplication, norme, normalisation, valeurs `null` |
| `physics/` | `HitboxTest` | Getters, centre, `contient`, `intersect` dans les 4 directions, hitboxes séparées ou en simple contact, valeurs `null` |
| `powers/` | `HulkTest` | Activation, puissance de tir, disponibilité, double activation, joueur `null` |
| `powers/` | `PepeTest` | Gel de l'adversaire, disponibilité, double activation, adversaire `null` |
| `powers/` | `JavaFxTestSupport` | Utilitaire : démarre JavaFX pour les pouvoirs |

Lancer tous les tests :

```bash
mvn test
```

Lancer une seule classe :

```bash
mvn test -Dtest=HitboxTest
```

Le rapport de couverture **JaCoCo** est généré par `mvn test` dans `target/site/jacoco/index.html`.

## Javadoc

Les fichiers suivants contiennent de la Javadoc :

**Application**
- `com/game/MainApp.java`

**Moteur** (`engine/init`)
- `Maillot.java`

**Jeu** (`game/`)
- `Joueur.java`
- `Pouvoir.java`
- `ControleurHumain.java`

**Réglages** (`front`)
- `Parametres.java`

**Cartes** (`front/map`)
- `Carte.java`
- `CarteStade.java`
- `MapBarres.java`
- `MapClassique.java`
- `MapPiliers.java`

**Affichage du match** (`front/match`)
- `AnneauJauge.java`
- `CadrePouvoirHud.java`
- `HudMatch.java`
- `JaugePouvoir.java`
- `MessageMatch.java`
- `RenduMatch.java`

**Pages de sélection** (`front/selection`)
- `CadreSelection.java`
- `PageChoix.java`
- `PageMaillot.java`
- `PagePouvoir.java`
- `PageSelection.java`
- `ParcoursSelection.java`

**Composants d'interface** (`front/ui`)
- `BoutonFleche.java`
- `BoutonTexte.java`
- `Palette.java`
- `Ressources.java`
- `Texte.java`
- `Zone.java`

## Auteurs et remerciements

Projet réalisé par :

- Game design : [@amd1.7](https://github.com/amd1.7)
- World design : [@ahmednourmeziane48-ui](https://github.com/ahmednourmeziane48-ui)

Merci de vous être intéressé à notre projet ! N'hésitez pas à ouvrir une *issue* ou à nous faire un retour.