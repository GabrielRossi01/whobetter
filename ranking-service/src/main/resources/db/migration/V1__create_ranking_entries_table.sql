CREATE TABLE ranking_entries (
    id UUID PRIMARY KEY,
    group_id UUID NOT NULL,
    user_id UUID NOT NULL,
    total_points INTEGER NOT NULL,
    rank_position INTEGER NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_ranking_group_user UNIQUE (group_id, user_id)
);

CREATE INDEX idx_ranking_group_id ON ranking_entries (group_id);
CREATE INDEX idx_ranking_user_id ON ranking_entries (user_id);