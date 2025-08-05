INSERT INTO users (username, password, role) VALUES
('admin@example.com', '$2a$08$9D.kojYEqAUSzR2Xv/9QSO4R/E2ZLEN1CAhTeGPuilOMBfTAuoI8W', 'ADMIN'),
('student@example.com', '$2a$08$di0VY/U2oZGAl.DMNZ2GlODLJ06f1WMmRb1kAzQ5st71XPbv7UEi.', 'STUDENT');

-- course_rules テーブルの初期データ
INSERT INTO course_rules (course_rule_name) VALUES
('情報科学基礎'),
('数学基礎'),
('プログラミング基礎');

-- terms テーブルの初期データ
INSERT INTO terms (term_name) VALUES
('前期'),
('後期'),
('夏期集中講義'),
('春期集中講義');

-- classes テーブルの初期データ
INSERT INTO classes (class_name, course_rule_id, credit_hours, academic_year, term_id, recommended_grade) VALUES
('情報セキュリティ論', 1, 2, 2025, 1, 3),
('情報科学演習', 1, 2, 2025, 1, 3),
('応用数学A', 2, 2, 2025, 1, 3),
('応用数学B', 2, 2, 2025, 1, 3),
('プログラミング演習I', 3, 2, 2025, 1, 2),
('プログラミング演習II', 3, 2, 2025, 1, 2);

-- class_schedules テーブルの初期データ
-- 情報セキュリティ論: 月曜1限
INSERT INTO class_schedules (class_id, day_of_week, period) VALUES
(1, 'MONDAY', 1);
-- 応用数学A: 火曜2限
INSERT INTO class_schedules (class_id, day_of_week, period) VALUES
(3, 'TUESDAY', 2);
-- プログラミング演習I: 水曜3限
INSERT INTO class_schedules (class_id, day_of_week, period) VALUES
(5, 'WEDNESDAY', 3);

-- enrollments テーブルの初期データ
-- student@example.com が情報セキュリティ論、応用数学A、プログラミング演習Iを履修
INSERT INTO enrollments (student_id, class_id, academic_year, scheduled_term) VALUES
(2, 1, 2025, '前期'),
(2, 3, 2025, '前期'),
(2, 5, 2025, '前期');

-- schedule_events テーブルの初期データ
-- 情報セキュリティ論の休講
INSERT INTO schedule_events (class_id, event_type, event_date, event_period, description, makeup_date, makeup_period, registered_by, created_at) VALUES
(1, 'CANCELLATION', '2025-07-21', 1, '担当教員の都合により、情報セキュリティ論は休講となります。', NULL, NULL, 1, '2025-07-18 10:00:00');

-- プログラミング演習Iの振替授業
INSERT INTO schedule_events (class_id, event_type, event_date, event_period, description, makeup_date, makeup_period, registered_by, created_at) VALUES
(5, 'MAKEUP_CLASS', '2025-07-23', 3, 'プログラミング演習Iの振替授業です。', '2025-07-28', 3, 1, '2025-07-18 12:00:00');
