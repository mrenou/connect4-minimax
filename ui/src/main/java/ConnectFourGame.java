import com.xebia.xke.algo.minimax.connect4.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class ConnectFourGame {

    private static final String PLAYERS_DIR = "players/";

    private Map<String, Player> players;

    public Component createComponents() {

        initPlayers();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        DefaultTableModel defaultTableModel = buildTableModel();
        JTable jtable = buildJTable(defaultTableModel);

        panel.add(jtable);

        return panel;
    }

    private JTable buildJTable(DefaultTableModel defaultTableModel) {
        JTable jtable = new JTable();

        ImageIcon redIcon = new ImageIcon(this.getClass().getResource("/images/counter-red.png"));
        ImageIcon yellowIcon = new ImageIcon(this.getClass().getResource("/images/counter-yellow.png"));
        ImageIcon emptyIcon = new ImageIcon(this.getClass().getResource("/images/counter-empty.png"));

        Board board = BoardFactory.createBoard("RY/YR/RY");

        for (int x = 0; x < board.getNbColumns(); x++) {
            for (int y = 0; y < board.getColumnSize(); y++) {
                CounterColor counterColor = board.getCounterColor(x, board.getColumnSize() - 1 - y);

                if (counterColor == null) {
                    defaultTableModel.setValueAt(emptyIcon, y, x);
                }
                if (CounterColor.RED.equals(counterColor)) {
                    defaultTableModel.setValueAt(redIcon, y, x);
                }
                if (CounterColor.YELLOW.equals(counterColor)) {
                    defaultTableModel.setValueAt(yellowIcon, y, x);
                }

            }
        }


        //TODO ?
        jtable.setShowGrid(true);
        jtable.setSize(700, 700);


        jtable.setModel(defaultTableModel);
        ;

        jtable.setRowHeight(100);
        jtable.getColumnModel().getColumn(0).setMinWidth(100);
        jtable.getColumnModel().getColumn(0).setMaxWidth(100);
        jtable.getColumnModel().getColumn(1).setMinWidth(100);
        jtable.getColumnModel().getColumn(1).setMaxWidth(100);
        jtable.getColumnModel().getColumn(2).setMinWidth(100);
        jtable.getColumnModel().getColumn(2).setMaxWidth(100);
        ;
        jtable.getColumnModel().getColumn(3).setMinWidth(100);
        jtable.getColumnModel().getColumn(3).setMaxWidth(100);
        jtable.getColumnModel().getColumn(4).setMinWidth(100);
        jtable.getColumnModel().getColumn(4).setMaxWidth(100);
        jtable.getColumnModel().getColumn(5).setMinWidth(100);
        jtable.getColumnModel().getColumn(5).setMaxWidth(100);
        jtable.getColumnModel().getColumn(6).setMinWidth(100);
        jtable.getColumnModel().getColumn(6).setMaxWidth(100);
        return jtable;
    }

    private DefaultTableModel buildTableModel() {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int i) {
                return Icon.class;
            }
        };
        defaultTableModel.setColumnCount(7);
        defaultTableModel.setRowCount(6);
        return defaultTableModel;
    }

    private void initPlayers() {
        PlayerLoader playerLoader = new PlayerLoader(new File(PLAYERS_DIR));

        try {
            players = playerLoader.loadAllPlayers();
        } catch (PlayerLoadingException e) {
            //TODO show error dialog
            throw new RuntimeException("BOOM");
        }
    }
}