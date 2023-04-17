package ru.omsu.fctk.data;

import java.util.Objects;

public class ProfitPerMinute implements IProfitPerMinute{
    public final long profitPerMinute;

    public ProfitPerMinute(long profitPerMinute) {
        if (profitPerMinute < 0) throw new IllegalArgumentException();
        this.profitPerMinute = profitPerMinute;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitPerMinute that = (ProfitPerMinute) o;
        return profitPerMinute == that.profitPerMinute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profitPerMinute);
    }

    @Override
    public String toString() {
        return "ProfitPerMinute{" +
                "profitPerMinute=" + profitPerMinute +
                '}';
    }
}
