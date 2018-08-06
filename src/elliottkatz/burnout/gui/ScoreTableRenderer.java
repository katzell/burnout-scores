package elliottkatz.burnout.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import elliottkatz.burnout.player.Player;
import elliottkatz.burnout.score.Score;
import elliottkatz.burnout.score.ScoreComparator;

public class ScoreTableRenderer extends DefaultTableCellRenderer {
    
    private static final long serialVersionUID = 1L;

    private static final ScoreComparator SCORE_COMPARATOR = new ScoreComparator();
    private ScoreTableModel model;
    
    private List<Score> sortedScores;
    
    private Score romMin;
    private Score katMin;
    private Score overallMin;
    
    public ScoreTableRenderer(ScoreTableModel model) {
        this.model = model;
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Score theScore = model.getScores().get(row);
        if(theScore.equals(overallMin)) {
        	c.setBackground(Color.YELLOW);
        }
        else if(theScore.equals(romMin) || theScore.equals(katMin)) {
            c.setBackground(Color.GREEN);
        }
        else {
            c.setBackground(Color.WHITE);
        }
        return c;
    }
    
    public void computeMins() {
        sortedScores = new ArrayList<Score>(model.getScores());
        
        Collections.sort(sortedScores, SCORE_COMPARATOR);
        
        overallMin = sortedScores.get(0);
        
        for(Score score : sortedScores) {
            if(score.getPlayer() == Player.ROM) {
                romMin = score;
                break;
            }
        }
        for(Score score : sortedScores) {
            if(score.getPlayer() == Player.KAT) {
                katMin = score;
                break;
            }
        }
    }
    
    
}