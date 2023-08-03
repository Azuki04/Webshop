
# Webshop

### von Sy Viet und Ivan
## Instalation

1. Stellen Sie sicher, dass Sie eine MySQL-Datenbank "webapp" erstellt haben.
2. Konfigurieren Sie die Datenbankverbindung in der Datei `application.properties` mit den entsprechenden Daten (z. B. Datenbank-URL, Benutzername, Passwort).
3. Öffnen Sie das Backend-Projektverzeichnis in Ihrer Entwicklungsumgebung.
4. Führen Sie folgenden Befehl aus, um das Backend mit Maven zu starten:

```sh
mvn spring-boot:run
```
5. Öffnen Sie das Frontend-Projektverzeichnis in Ihrer Entwicklungsumgebung.
6. Führen Sie den folgenden Befehl aus, um das Frontend zu starten:
```sh
npm install
npm start
```
7. Öffnen Sie anschließend Ihren Webbrowser und navigieren Sie zur folgenden URL: [http://localhost:3000](http://localhost:3000)


## Benutzer
Benutzer wurde im configuration package  schon erstellt. Sie können sich mit folgenden Benutzer anmelden:

| Benutzername | Passwort | Rolle |
|--------------|----------| ----- |
| admin        | admin123 | ADMIN |
| student      | password | USER  |

## Applikation
Sie können als user Produkte erstellen, bearbeiten und löschen. Als admin können Sie auch Benutzer erstellen, bearbeiten und löschen.
Probieren Sie es aus!

