package elliottkatz.burnout.score;

import java.util.Date;

import elliottkatz.burnout.level.Level;
import elliottkatz.burnout.player.Player;

public class Score {
	private Player player;
	private Level level;
	private float value;
	private String notes;

	private final Date date;

	public Score(Player player, Level level, float value, String notes,
			Date date) {
		this.player = player;
		this.level = level;
		this.value = value;
		this.notes = notes;
		this.date = date;
	}

	public Level getLevel() {
		return level;
	}

	public Player getPlayer() {
		return player;
	}

	public float getValue() {
		return value;
	}

	public String getNotes() {
		return notes;
	}

	public Date getDate() {
		return date;
	}

	public Object getDisplayValue() {
		if (value == 0.00F) {
			return "FAIL";
		}
		if (level.getScoreType() == ScoreType.PASS_FAIL && value == 1.00F) {
			return "PASS";
		}
		return value;
	}

}
