CREATE TABLE matches (
    id UUID PRIMARY KEY,
    group_id UUID NOT NULL,
    title VARCHAR(150) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    home_score INTEGER,
    away_score INTEGER,
    created_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_matches_group_id ON matches (group_id);
CREATE INDEX idx_matches_status ON matches (status);