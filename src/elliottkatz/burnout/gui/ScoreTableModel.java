package elliottkatz.burnout.gui;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import elliottkatz.burnout.dao.ScoreDao;
import elliottkatz.burnout.score.Score;

public class ScoreTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private static final String[] columnNames = { "Player", "Score", "Notes", "Date" };

    private List<Score> scores;

    public ScoreTableModel() {
        this(Collections.<Score> emptyList());
    }

    public ScoreTableModel(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Score score = scores.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return score.getPlayer();
        case 1:
            return score.getDisplayValue();
        case 2:
            return score.getNotes();
        case 3:
            return ScoreDao.DATE_FORMAT.format(score.getDate());
        default:
            return "UNKNOWN DATA";
        }
    }

    @Override
    public int getRowCount() {
        return scores.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
