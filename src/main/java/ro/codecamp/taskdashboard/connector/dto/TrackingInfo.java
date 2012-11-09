package ro.codecamp.taskdashboard.connector.dto;

public final class TrackingInfo {
    private Float estimate;
    private Float completed;
    private Float remaining;

    public Float getEstimate() {
        return estimate;
    }

    public Float getCompleted() {
        return completed;
    }

    public Float getRemaining() {
        return remaining;
    }

    public static class Builder {
        private TrackingInfo trackingInfo;

        private Builder() {
            trackingInfo = new TrackingInfo();
        }

        public Builder withEstimate(Float estimate) {
            trackingInfo.estimate = estimate;
            return this;
        }

        public Builder withCompleted(Float completed) {
            trackingInfo.completed = completed;
            return this;
        }

        public Builder withRemaining(Float remaining) {
            trackingInfo.remaining = remaining;
            return this;
        }

        public static Builder create() {
            return new Builder();
        }

        public TrackingInfo build() {
            return trackingInfo;
        }
    }
}
