ALTER TABLE action.actionrule ADD hastriggered boolean;

UPDATE action.actionrule SET hastriggered = 't';

ALTER TABLE action.actionrule ALTER COLUMN hastriggered SET NOT NULL;
ALTER TABLE action.actionrule ALTER COLUMN hastriggered SET DEFAULT FALSE;