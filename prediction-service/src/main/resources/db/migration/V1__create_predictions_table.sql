CREATE TABLE predictions (
    id UUID PRIMARY KEY,
    match_id UUID NOT NULL,
    group_id UUID NOT NULL,
    user_id UUID NOT NULL,
    predicted_home_score INTEGER NOT NULL,
    predicted_away_score INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_prediction_match_user UNIQUE (match_id, user_id)
);

CREATE INDEX idx_predictions_match_id ON predictions (match_id);
CREATE INDEX idx_predictions_user_id ON predictions (user_id);
CREATE INDEX idx_predictions_group_id ON predictions (group_id);