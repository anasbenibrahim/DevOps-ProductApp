# ============================================================
# ÉTAPE 1 : BUILD → compiler le code et produire le .jar
# ============================================================

# Image qui contient Maven + Java 17 (JDK complet pour compiler)
# "AS build" donne un nom à cette étape pour pouvoir la réutiliser plus bas
FROM maven:3.9-eclipse-temurin-17 AS build

# Dossier de travail dans le conteneur (créé automatiquement)
WORKDIR /app

# Copier TOUT le projet (racine + tous les modules)
# Nécessaire car product-service a besoin du pom.xml parent de la racine
COPY . .

# Compiler UNIQUEMENT product-service :
#   -pl product-service → "project list" : seulement ce module
#   -am                 → "also make" : construit aussi ce dont il dépend (le POM parent)
#   -DskipTests         → les tests seront lancés par Jenkins, pas ici
# --mount=type=cache → garde les dépendances Maven téléchargées entre deux builds
#                      (sinon Maven retélécharge tout à chaque fois = très lent)
RUN --mount=type=cache,target=/root/.m2 \
    mvn -pl product-service -am clean package -DskipTests

# ============================================================
# ÉTAPE 2 : RUNTIME → image finale, légère, qui exécute le .jar
# ============================================================

# Image avec SEULEMENT Java 17 pour exécuter (pas Maven, pas le compilateur)
# "alpine" = version Linux minuscule → image plus petite et plus sûre
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Sécurité : créer un utilisateur "spring" (sans droits admin)
# Par défaut un conteneur tourne en "root" → dangereux si l'app est piratée
RUN addgroup -S spring && adduser -S spring -G spring

# Récupérer UNIQUEMENT le .jar depuis l'étape "build"
# Tout le reste (Maven, code source...) n'est pas copié → image légère
COPY --from=build /app/product-service/target/*.jar app.jar

# À partir d'ici, le conteneur tourne avec l'utilisateur "spring"
USER spring

# Documentation : indique que l'application écoute sur le port 5001
# (n'ouvre rien réellement, c'est docker-compose qui publie les ports)
EXPOSE 5001

# Commande lancée au démarrage du conteneur
# -XX:MaxRAMPercentage=75.0 → Java utilise max 75% de la mémoire du conteneur
#                             (évite que le conteneur soit tué pour excès de RAM)
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]