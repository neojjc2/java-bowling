package bowling.domain.score;

import bowling.domain.exception.BowlingBuildingException;
import bowling.domain.pitch.Pitch;

import java.util.function.Function;

public enum PitchScoreType {
    STRIKE((pitch) -> "X"),
    SPARE((pitch) -> "/"),
    GUTTER((pitch) -> "-"),
    MISS((pitch) -> String.valueOf(pitch.getPitchScore())),
    NORMAL((pitch) -> String.valueOf(pitch.getPitchScore()));

    private static final int MAXIMUM_SCORE_TOTAL = 10;

    private final Function<Pitch, String> signatureFunction;

    private PitchScoreType(Function<Pitch, String> signatureFunction) {
        this.signatureFunction = signatureFunction;
    }

    public static PitchScoreType initiate(PitchScore pitchScore) {
        if (pitchScore.isMaximum()) {
            return STRIKE;
        }
        return pitchScore.isMinimum() ? GUTTER : NORMAL;
    }

    public static PitchScoreType next(Pitch lastPitch, PitchScore pitchScore) {
        int pitchScoreSum = lastPitch.calculatePitchScoreSum(pitchScore);
        validateNextCondition(lastPitch, pitchScoreSum);
        if (pitchScoreSum == MAXIMUM_SCORE_TOTAL) {
            return SPARE;
        }
        return pitchScore.isMinimum() ? GUTTER : MISS;
    }

    private static void validateNextCondition(Pitch lastPitch, int scoresSum) {
        if (lastPitch.isStrike() || lastPitch.isSpare()) {
            throw new BowlingBuildingException(BowlingBuildingException.INVALID_NORMAL_PITCHES_SCORE);
        }
        if (scoresSum > MAXIMUM_SCORE_TOTAL) {
            throw new BowlingBuildingException((BowlingBuildingException.OVER_SCORE));
        }
    }

    public String getPitchScoreSignature(Pitch pitch) {
        return signatureFunction.apply(pitch);
    }
}