package elliottkatz.burnout.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import elliottkatz.burnout.level.Level;
import elliottkatz.burnout.player.Player;
import elliottkatz.burnout.score.Score;


/**
 * This is a Singleton - don't go instantiating more of them.
 */
public class ScoreDao {

    private static final String CSV_PATH = System.getProperty("csvLocation") != null ? System.getProperty("csvLocation") : "scores.csv";
	private List<Score> scores = new ArrayList<Score>();
    private Map<Level, List<Score>> scoresByLevel = new HashMap<Level, List<Score>>();
    
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ScoreDao() {
        scores = getAllScores();
        populateScoresByLevel();
    }
    
    private void populateScoresByLevel() {
    	for(Level level : Level.values()) {
    		scoresByLevel.put(level, new ArrayList<Score>());
    	}
		for(Score score : scores) {
			scoresByLevel.get(score.getLevel()).add(score);
		}
		
	}

	public void createScore(Player player, Level level, float value,
			String notes) throws IOException {
		Score score = new Score(player, level, value, notes, new Date());
		try {
			appendToScoresFile(getCsvLine(score));
		} catch (IOException e) {
			throw e;
		}
		scores.add(score);
		scoresByLevel.get(score.getLevel()).add(score);
	}

    public List<Score> getAllScores() {
    	List<Score> allScores = null;
    	try {
			String scoresFile = readFile(CSV_PATH);
			allScores = parseScores(scoresFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return allScores;
    }
    
    public List<Score> getScoresByLevelAndPlayers(Level level, Set<Player> players) {
        List<Score> levelScores = scoresByLevel.get(level);
        List<Score> filteredScores = new ArrayList<Score>();
        for(Score score : levelScores) {
        	if(players.contains(score.getPlayer())) {
        		filteredScores.add(score);
        	}
        }
		return filteredScores;
    }

    private List<Score> parseScores(String csv) {
        List<Score> parsedScores = new ArrayList<Score>();
        
        List<String> scoreLines = Arrays.asList(csv.split(LINE_SEPARATOR));
        
        for(String line : scoreLines) {
        	if(line.isEmpty()) {
        		continue;
        	}
        	String[] splitScore = line.split(",");
        	Level level = Level.getByValue(splitScore[0]);
        	Player player = Player.getByName(splitScore[1]);
        	Date date = null;
			try {
				date = DATE_FORMAT.parse(splitScore[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	float value = Float.parseFloat(splitScore[3]);
        	String notes = "";
        	if(splitScore.length >= 5) {
        		notes = splitScore[4];
        	}
        	
        	parsedScores.add(new Score(player, level, value, notes, date));
        }
        
        return parsedScores;
    }
    
    String getCsvLine(Score score) {
    	String level = score.getLevel().toString();
    	String player = score.getPlayer().toString();
    	String date = DATE_FORMAT.format(score.getDate());
    	String value = String.valueOf(score.getValue());
    	String notes = score.getNotes();
    	
    	return level + "," + player + "," + date + "," + value + "," + notes;
    }
    
	private static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
	
	private void appendToScoresFile(String string) throws IOException {
		Files.write(Paths.get(CSV_PATH), (LINE_SEPARATOR + string).getBytes(),
				StandardOpenOption.APPEND);
	}
	
}
