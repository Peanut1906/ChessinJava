import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;

public class Table {

    // wichtige Variablen
    private final JFrame gameFrame;
    final BoardPanel boardPanel;
    private final Board chessBoard;

    // Farben für die Spielbrettfelder
    private final Color lightTileColor = Color.decode("#EEEED2");
    private final Color darkTileColor = Color.decode("#769656");
    private final Color highlightColor = Color.decode("#FE163");

    // Konstanten für die Abmessungen des Rahmens und des Spielfelds
    private static final DimensionUIResource OUTER_FRAME_DIMENSION = new DimensionUIResource(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static String defaultPieceImagesPath = "art/";

    /**
     * Konstruktor für die Klasse Table.
     *
     * @param chessBoard Das Schachbrett, das angezeigt werden soll.
     */
    public Table(Board chessBoard) {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = chessBoard;

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    /**
     * Erstellt die Menüleiste für die Tabelle.
     *
     * @return Die Menüleiste.
     */
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    /**
     * Erstellt das Menü "Datei" für die Tabelle.
     *
     * @return Das Menü "Datei".
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("Datei");

        final JMenuItem openPGN = new JMenuItem("PGN-Datei laden"); // TODO: PGN import und export 
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PGN-Datei öffnen");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Beenden");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    /**
     * Innere Klasse für das Spielfeldpanel.
     */
    class BoardPanel extends JPanel {

        // Membervariablen
        final List<TilePanel> boardTiles;
        String selectedSquare = "";

        /**
         * Konstruktor für die Klasse BoardPanel.
         */
        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        /**
         * Behandelt ein Kachelklick-Ereignis.
         *
         * @param tileId Die ID der angeklickten Kachel.
         */
        void handleTileClick(int tileId) {
            String moveString = getMoveString(tileId);
            if (!selectedSquare.isEmpty()) {
                Table.this.handleTileClick(selectedSquare + moveString);
                setSelectedSquare("");
            } else {
                setSelectedSquare(moveString);
            }
        }

        /**
         * Ruft den Zugstring für die angegebene Kachel-ID ab.
         *
         * @param tileId Die ID der Kachel.
         * @return Der Zugstring.
         */
        private String getMoveString(int tileId) {
            int selectedRow = (63 - tileId) / 8;
            int selectedCol = tileId % 8;
            String selectedSquare = String.valueOf((char) ('a' + selectedCol)) + String.valueOf(8 - selectedRow);
            return selectedSquare;
        }

        /**
         * Setzt das ausgewählte Feld.
         *
         * @param selectedSquare Das ausgewählte Feld.
         */
        public void setSelectedSquare(String selectedSquare) {
            // Markierung von allen Feldern entfernen
            for (TilePanel tilePanel : boardTiles) {
                tilePanel.setBackground(getSquareColor(boardTiles.indexOf(tilePanel)));
            }

            this.selectedSquare = selectedSquare;

            // Markiere das neu ausgewählte Feld
            if (!selectedSquare.equals("")) {
                int tileId = getTileId(selectedSquare);
                boardTiles.get(tileId).setBackground(highlightColor);
            }
        }

        /**
         * Ruft die Kachel-ID für das angegebene Feld ab.
         *
         * @param square Das Feld.
         * @return Die Kachel-ID.
         */
        private int getTileId(String square) {
            int row = 8 - Integer.parseInt(square.substring(1));
            int col = square.charAt(0) - 'a';
            return (7 - row) * 8 + col;
        }

        /**
         * Ruft die Farbe des Feldes ab.
         *
         * @param tileId Die ID der Kachel.
         * @return Die Farbe des Feldes.
         */
        private Color getSquareColor(int tileId) {
            if ((tileId / 8) % 2 == 0 && tileId % 2 == 0) {
                return lightTileColor;
            } else if ((tileId / 8) % 2 != 0 && tileId % 2 != 0) {
                return lightTileColor;
            } else {
                return darkTileColor;
            }
        }
    }

    /**
     * Innere Klasse für das Feld.
     */
    class TilePanel extends JPanel {

        // Membervariablen
        private final int tileId;
        private final BoardPanel boardPanel;

        /**
         * Konstruktor für die Klasse TilePanel.
         *
         * @param boardPanel Das Spielfeldpanel.
         * @param tileId     Die ID des Feldes.
         */
        public TilePanel(BoardPanel boardPanel, int tileId) {
            super(new GridBagLayout());
            this.boardPanel = boardPanel;
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
        }

        /**
         * Weist dem Feld die Farbe zu.
         */
        private void assignTileColor() {
            if ((tileId / 8) % 2 == 0 && tileId % 2 == 0) {
                setBackground(lightTileColor);
            } else if ((tileId / 8) % 2 != 0 && tileId % 2 != 0) {
                setBackground(lightTileColor);
            } else {
                setBackground(darkTileColor);
            }
        }

        /**
         * Weist der Kachel das Figurensymbol zu.
         *
         * @param chessBoard Das Schachbrett.
         */
        private void assignTilePieceIcon(Board chessBoard) {
            this.removeAll();
            int row = (63 - tileId) / 8;
            int col = tileId % 8;
            if (chessBoard.getPiece(row, col) != null) {
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + chessBoard.getPiece(row, col).toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            revalidate();
            repaint();
        }
    }

    private void handleTileClick(String moveString) {
        /**
         * Diese Methode verarbeitet einen Klick auf ein Feld und führt den entsprechenden Zug aus.
         *
         * @param moveString Der Zugstring, der die Start- und Zielfelder angibt.
         */
        parseMoveString(moveString);
        boardPanel.repaint();
    }
    
    private void parseMoveString(String moveString) {
        /**
         * Diese Methode parst den Zugstring und extrahiert die Start- und Zielfelder.
         *
         * @param moveString Der Zugstring, der die Start- und Zielfelder angibt.
         */
        String fromSquare = moveString.substring(0, 2);
        String toSquare = moveString.substring(2);
    
        int fromRow = 8 - Integer.parseInt(fromSquare.substring(1));
        int fromCol = fromSquare.charAt(0) - 'a';
        int toRow = 8 - Integer.parseInt(toSquare.substring(1));
        int toCol = toSquare.charAt(0) - 'a';
    
        String playerTurn;
        if (Main.getCurrentTurn() == 0) {
            playerTurn = "white";
        } else {
            playerTurn = "black";
        }
    
        try {
            chessBoard.movePiece(fromCol, fromRow, toCol, toRow, playerTurn);
            Main.currentTurn = 1 - Main.currentTurn;
    
            // Icon-Update nach einem Zug; TODO: Rocharden und En passant BUG lösen
            int fromTileId = (7 - fromRow) * 8 + fromCol;
            int toTileId = (7 - toRow) * 8 + toCol;
            boardPanel.boardTiles.get(fromTileId).assignTilePieceIcon(chessBoard);
            boardPanel.boardTiles.get(toTileId).assignTilePieceIcon(chessBoard);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
    }
}