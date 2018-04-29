package elliottkatz.burnout.level;

import static elliottkatz.burnout.score.ScoreType.COUNT;
import static elliottkatz.burnout.score.ScoreType.PASS_FAIL;
import static elliottkatz.burnout.score.ScoreType.TIME;

import java.util.ArrayList;
import java.util.List;

import elliottkatz.burnout.exception.InvalidIdException;
import elliottkatz.burnout.score.ScoreType;
public enum Level {
	A_BRIDGE_TOO_FAR(1, "A Bridge Too Far", TIME),
	BARREL_ROLL_BASH(2, "Barrel Roll Bash", PASS_FAIL),
	BEACH_BARREL(3, "Beach Barrel", PASS_FAIL),
	BOARD_WALK_RUN(4, "Board Walk Run", TIME),
	CARRIAGE_RETURN(5, "Carriage Return", TIME),
	CLEARED_FOR_TAKE_OFF(6, "Cleared For Take-Off", COUNT),
	DAM_BUSTER(7, "Dam Buster", PASS_FAIL),
	DANGER_FALLING_CARS(8, "Danger: Falling Cars", COUNT),
	DEAD_MANS_SPIN(9, "Dead Man's Spin", COUNT),
	DOUBLE_TROUBLE(10, "Double Trouble", PASS_FAIL),
	DOWNHILL_SPRINT(11, "Downhill Sprint", TIME),
	DOWNTOWN_DASH(12, "Downtown Dash", TIME),
	DRIFT_KING(13, "Drift King", COUNT),
	EXPRESS_AIRWAYS(14, "Express Airways", COUNT),
	HOLE_IN_ONE(15, "Hole In One", PASS_FAIL),
	MERRY_GO_ROUND(16, "Merry Go Round", COUNT),
	MIND_THE_STEP(17, "Mind The Step", TIME),
	NEAR_MIST(18, "Near Mist", COUNT),
	PARK_AND_FLY(19, "Park and Fly", TIME),
	PARK_PANIC(20,"Park Panic", TIME),
	PICK_UP_SMASH_UP(21, "Pick Up Smash Up", TIME),
	ROCK_AND_ROLLS(22, "Rock and Rolls", PASS_FAIL),
	ROLL_WRECKER(23, "Roll Wrecker", PASS_FAIL),
	SAFE_LANDING(24, "Safe Landing (lol)", PASS_FAIL),
	SPIN_TO_WIN(25, "Spin To Win", COUNT),
	STAY_ON_TARGET(26, "Stay On Target", COUNT),
	STRAIGHT_8(27, "Straight 8", TIME),
	SUMMITS_SMASH(28, "Summits Smash", TIME),
	SWIMMING_UP_STREAM(29, "Swimming Up Stream", COUNT),
	SWITCHBACK_DRIFTER(30, "Switchback Drifter", COUNT),
	TOLL_TO_TOLL(31, "Toll To Toll", TIME),
	TUNNEL_VISION(32, "Tunnel Vision", COUNT),
	TWISTED_TRACK(33, "Twisted Track", TIME),
	VICE_VERSA(34, "Vice Versa", TIME),
	WALK_ON_THE_WILD_SIDE(35, "Walk on the Wild Side", COUNT),
	WRONG_WAY_STREET(36, "Wrong Way Street", COUNT)
	;
	
	private int id;
	private String name;
	private ScoreType scoreType;
	
	Level(int id, String name, ScoreType scoreType) {
		this.id = id;
		this.name = name;
		this.scoreType = scoreType;
	}
	
	public static Level getById(int id) throws InvalidIdException {
		for (Level level : Level.values()) {
			if (level.id == id) {
				return level;
			}
		}
		throw new InvalidIdException();
	}
	
	public static Level getByValue(String value) {
		for (Level level : Level.values()) {
			if (level.toString().equals(value)) {
				return level;
			}
		}
		throw new IllegalArgumentException("No level found with value: " + value);
	}
	
	public static List<Level> getAllLevels() {
		List<Level> levels = new ArrayList<Level>();
		for (Level level : values()) {
			levels.add(level);
		}
		return levels;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public ScoreType getScoreType() {
		return scoreType;
	}
	
}
