CREATE TABLE scores (
    id UUID PRIMARY KEY,
    match_id UUID NOT NULL,
    group_id UUID NOT NULL,
    user_id UUID NOT NULL,
    prediction_id UUID NOT NULL,
    points INTEGER NOT NULL,
    scoring_type VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_scores_prediction UNIQUE (prediction_id)
);

CREATE INDEX idx_scores_match_id ON scores (match_id);
CREATE INDEX idx_scores_group_id ON scores (group_id);
CREATE INDEX idx_scores_user_id ON scores (user_id);