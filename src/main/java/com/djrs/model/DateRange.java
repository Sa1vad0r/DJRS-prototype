package com.djrs.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class DateRange {
    private LocalDateTime start;
    private LocalDateTime end;

    public DateRange(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must not be before start");
        }
        this.start = start;
        this.end = end;
    }

    // Change both at once so the object is never in an invalid state
    public void setDates(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must not be before start");
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean contains(LocalDateTime date) {

    }
    @Override
    public String toString() {
        return "DateRange[start=" + start + ", end=" + end + "]";
    }
}