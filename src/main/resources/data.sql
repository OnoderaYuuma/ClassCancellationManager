INSERT INTO users (username, password, role) VALUES
('admin@example.com', '$2a$08$9D.kojYEqAUSzR2Xv/9QSO4R/E2ZLEN1CAhTeGPuilOMBfTAuoI8W', 'ADMIN'),
('student@example.com', '$2a$08$di0VY/U2oZGAl.DMNZ2GlODLJ06f1WMmRb1kAzQ5st71XPbv7UEi.', 'STUDENT');

-- course_rules テーブルの初期データ
INSERT INTO course_rules (course_rule_name) VALUES
('キャリアデザイン'),
('情報システム概論'),
('システム開発演習'),
('システム開発実践');

-- terms テーブルの初期データ
INSERT INTO terms (term_name) VALUES
('前期'),
('後期'),
('夏期集中講義'),
('春期集中講義');

-- classes テーブルの初期データ
INSERT INTO classes (class_name, course_rule_id, credit_hours, academic_year, term_id, recommended_grade) VALUES
('コーディング試験対策', 1, 30, 2025, 1, 3),
('応用情報対策(午前試験)', 2, 30, 2025, 1, 3),
('応用情報対策(午後試験)', 2, 30, 2025, 1, 3),
('Java応用(Spring Boot)', 3, 90, 2025, 1, 3),
('CSS応用(Tailwind)', 3, 60, 2025, 1, 2),
('AWS基礎', 2, 60, 2025, 1, 2),
('テスト基礎', 3, 60, 2025, 1, 2),
('PHP基礎', 2, 60, 2025, 1, 2),
('開発ハッカソン', 4, 60, 2025, 1, 2),
('2024年度授業', 1, 30, 2024, 1, 1);

-- class_schedules テーブルの初期データ
INSERT INTO class_schedules (class_id, day_of_week, period) VALUES
(1, 'WEDNESDAY', 2),
(2, 'FRIDAY', 4),
(3, 'MONDAY', 4),
(4, 'MONDAY', 3),
(4, 'TUESDAY', 3),
(4, 'FRIDAY', 3),
(5, 'FRIDAY', 2),
(6, 'MONDAY', 2),
(6, 'THURSDAY', 1),
(7, 'WEDNESDAY', 1),
(7, 'THURSDAY', 2),
(8, 'TUESDAY', 2),
(8, 'FRIDAY', 1),
(9, 'MONDAY', 1),
(10, 'MONDAY', 1);

-- enrollments テーブルの初期データ
INSERT INTO enrollments (student_id, class_id, academic_year, term_id) VALUES
(2, 1, 2025, 1),
(2, 3, 2025, 1),
(2, 5, 2025, 1),
(2, 10, 2024, 1);

-- schedule_events テーブルの初期データ
-- 情報セキュリティ論の休講
INSERT INTO schedule_events (class_id, event_type, event_date, event_period, description, makeup_date, makeup_period, registered_by, created_at) VALUES
(1, 'CANCELLATION', '2025-08-20', 2, '担当教員の都合により休講となります。', NULL, NULL, 1, '2025-07-18 10:00:00');

-- プログラミング演習Iの振替授業
INSERT INTO schedule_events (class_id, event_type, event_date, event_period, description, makeup_date, makeup_period, registered_by, created_at) VALUES
(5, 'MAKEUP_CLASS', '2025-08-22', 3, '祝日分の振替授業です。', NULL, NULL, 1, '2025-07-18 12:00:00');

INSERT INTO schedule_events (class_id, event_type, event_date, event_period, description, makeup_date, makeup_period, registered_by, created_at) VALUES
(10, 'CANCELLATION', '2024-08-23', 3, '2024年度の授業です', NULL, NULL, 1, '2024-07-18 12:00:00');
