INSERT INTO users (email, password_hash, role, created_at)
VALUES ('admin@renewalguard.com', '$2b$10$XEeM1ZuGm.FM/JUGqgytl.cnaSD/3j0SoIvYZiaRBrTVlTO.7XeUa', 'ADMIN', UNIX_TIMESTAMP() * 1000);

INSERT INTO users (email, password_hash, role, created_at)
VALUES ('owner@renewalguard.com', '$2b$10$XEeM1ZuGm.FM/JUGqgytl.cnaSD/3j0SoIvYZiaRBrTVlTO.7XeUa', 'OWNER', UNIX_TIMESTAMP() * 1000);

-- Insert sample escalation policy (30/7/1 days)
INSERT INTO escalation_policies (name, description, created_at)
VALUES ('Standard 30/7/1', 'Escalate at 30 days, 7 days, and 1 day before expiry', UNIX_TIMESTAMP() * 1000);

-- Insert escalation steps for the policy
INSERT INTO escalation_steps (policy_id, days_before_expiry, target_status, step_order)
VALUES
  (1, 30, 'NEARING_EXPIRY', 1),
  (1, 7, 'ESCALATED', 2),
  (1, 1, 'ESCALATED', 3);