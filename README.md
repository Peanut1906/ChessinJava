# Schachspiel in Java

Dieses Projekt ist eine Implementierung des klassischen Schachspiels in Java. Es besteht aus mehreren Klassen, die die verschiedenen Schachfiguren und deren Bewegungsregeln repräsentieren.

## Funktionen

- Spielbrett wird in der Konsole dargestellt
- Gültige Züge werden für jede Figur überprüft
- Implementierung von Sonderregeln wie En-Passant, Bauernumwandlung und Rochade
- Erkennung von Schachmatt und Patt-Situationen
- Spielbrett kann ebenfalls graphisch dargestellt werden.

## Klassen

### `Piece` (abstrakte Klasse)

Die abstrakte Basisklasse für alle Schachfiguren. Sie definiert grundlegende Attribute und Methoden, die von allen Schachfiguren geerbt werden.

### `Pawn`

Repräsentiert die Schachfigur Bauer und implementiert die spezifischen Bewegungsregeln für Bauern.

### `Queen`

Repräsentiert die Schachfigur Königin und implementiert die spezifischen Bewegungsregeln für Königinnen.

### `Rook`

Repräsentiert die Schachfigur Turm und implementiert die spezifischen Bewegungsregeln für Türme.

### `Bishop`

Repräsentiert die Schachfigur Läufer und implementiert die spezifischen Bewegungsregeln für Läufer.

### `Knight`

Repräsentiert die Schachfigur Springer und implementiert die spezifischen Bewegungsregeln für Springer.

### `King`

Repräsentiert die Schachfigur König und implementiert die spezifischen Bewegungsregeln für Könige.

### `Game`

Die Hauptklasse, die das Spielbrett verwaltet, Züge ausführt und den Spielverlauf kontrolliert.
