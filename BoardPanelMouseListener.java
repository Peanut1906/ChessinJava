/**
 * Diese Klasse implementiert das MouseListener-Interface und behandelt Mausereignisse
 * für das BoardPanel eines Spielbretts. Sie überwacht Mausklicks auf den Spielfeldern
 * und ruft die entsprechende Methode auf, um den Klick zu verarbeiten.
 */
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class BoardPanelMouseListener implements MouseListener {
    private final Table.BoardPanel boardPanel;

    /**
     * Konstruktor, der das BoardPanel-Objekt akzeptiert, für das diese Instanz
     * Mausereignisse behandeln soll.
     *
     * @param boardPanel Das BoardPanel-Objekt, für das diese Instanz Mausereignisse behandeln soll.
     */
    BoardPanelMouseListener(Table.BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    /**
     * Behandelt das MouseClicked-Ereignis. Ermittelt das angeklickte Spielfeld
     * und ruft die handleTileClick-Methode des BoardPanels auf, um den Klick
     * zu verarbeiten.
     *
     * @param e Das MouseEvent-Objekt, das das Mausereignis enthält.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int tileId = getTileId(e.getPoint(), boardPanel);
        if (tileId != -1) {
            boardPanel.handleTileClick(tileId);
        }
    }

    /**
     * Behandelt das MousePressed-Ereignis. Derzeit nicht implementiert.
     *
     * @param e Das MouseEvent-Objekt, das das Mausereignis enthält.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Nicht in Verwendung
    }

    /**
     * Behandelt das MouseReleased-Ereignis. Derzeit nicht implementiert.
     *
     * @param e Das MouseEvent-Objekt, das das Mausereignis enthält.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Nicht in Verwendung
    }

    /**
     * Behandelt das MouseEntered-Ereignis. Derzeit nicht implementiert.
     *
     * @param e Das MouseEvent-Objekt, das das Mausereignis enthält.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Nicht in Verwendung
    }

    /**
     * Behandelt das MouseExited-Ereignis. Derzeit nicht implementiert.
     *
     * @param e Das MouseEvent-Objekt, das das Mausereignis enthält.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Nicht in Verwendung
    }

    /**
     * Ermittelt die ID des angeklickten Spielfelds basierend auf den übergebenen
     * Koordinaten und dem BoardPanel-Objekt.
     *
     * @param point     Der Punkt, der die Koordinaten des Mausklicks enthält.
     * @param boardPanel Das BoardPanel-Objekt, auf dem die Spielfelder gezeichnet sind.
     * @return Die ID des angeklickten Spielfelds oder -1, wenn kein Spielfeld angeklickt wurde.
     */
    private int getTileId(Point point, Table.BoardPanel boardPanel) {
        for (int i = 0; i < boardPanel.boardTiles.size(); i++) {
            if (boardPanel.boardTiles.get(i).getBounds().contains(point)) {
                return i;
            }
        }
        return -1;
    }
}