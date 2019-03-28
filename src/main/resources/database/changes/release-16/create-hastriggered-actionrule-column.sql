ALTER TABLE action.actionrule ADD hastriggered boolean;

UPDATE action.actionrule SET hastriggered = 't';