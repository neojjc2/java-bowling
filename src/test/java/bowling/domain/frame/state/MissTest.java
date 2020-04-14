package bowling.domain.frame.state;

import bowling.domain.pin.BowlCount;
import bowling.domain.pin.Pins;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissTest {
    @DisplayName("MISS 점수를 갖고올 수 있다.")
    @Test
    void spare() {
        String expect = "4|4";
        Pins first = Pins.of().knockOver(new BowlCount(4));
        Pins second = Pins.of().knockOver(new BowlCount(4));

        State actual = new Miss(first, second);

        assertThat(actual.toResult()).isEqualTo(expect);
    }
}