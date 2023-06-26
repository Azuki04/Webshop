
# m223-Mulituser Webshop


## Instalation

1. Stellen Sie sicher, dass Sie eine MySQL-Datenbank "webshop" installiert und konfiguriert haben.
2. Öffnen Sie das Backend-Projektverzeichnis in Ihrer Entwicklungsumgebung.
3. Konfigurieren Sie die Datenbankverbindung in der Datei `application.properties` mit den entsprechenden Daten (z. B. Datenbank-URL, Benutzername, Passwort).
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

## Datenbank
Um den Webshop mit Produkten zu befüllen, müssen in der Datenbank Kategorien mit Beispieldaten hinzugefügt werden. Sie können SQL-Abfragen verwenden, um Kategorien in der Datenbank einzufügen. Hier ist ein Beispiel:

```sql
INSERT INTO category (id, category) VALUES 
(1, "Clothing"),
(2, "Tv & Audio"),
(3, "Toy"),
(4, "Tools"),
(5, "Shoes"),
(6, "Computer & Gaming"),
(7, "Houshold & Kitchen"),
(8, "Beauty & Health"),
(9, "Sport"),
(10, "Office");
