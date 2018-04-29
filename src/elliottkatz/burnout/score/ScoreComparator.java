package elliottkatz.burnout.score;

import java.util.Comparator;


public class ScoreComparator implements Comparator<Score> {

    @Override
    public int compare(Score s1, Score s2) {
        if (s1.getLevel().getScoreType() != s2.getLevel().getScoreType()) {
            throw new IncomparableScoreException();
        }

        float v1 = s1.getValue();
        float v2 = s2.getValue();

        switch (s1.getLevel().getScoreType()) {
        case PASS_FAIL:
            return Float.compare(v2, v1);
        case COUNT:
            return Float.compare(v2, v1);
        case TIME:
            if (v1 == 0) {
                return 1;
            }
            if (v2 == 0) {
                return -1;
            }
            return Float.compare(v1, v2);
        }
        return 0;
    }

    private class IncomparableScoreException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

}
