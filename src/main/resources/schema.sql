CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS course_rules (
    course_rule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_rule_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS terms (
    term_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS classes (
    class_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(255) NOT NULL,
    course_rule_id BIGINT NOT NULL,
    credit_hours INT NOT NULL,
    academic_year INT NOT NULL,
    term_id BIGINT NOT NULL,
    recommended_grade INT NOT NULL,
    FOREIGN KEY (course_rule_id) REFERENCES course_rules(course_rule_id),
    FOREIGN KEY (term_id) REFERENCES terms(term_id)
);

CREATE TABLE IF NOT EXISTS class_schedules (
    schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_id BIGINT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    period INT NOT NULL,
    UNIQUE(class_id, day_of_week, period),
    FOREIGN KEY (class_id) REFERENCES classes(class_id)
);

CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,
    academic_year INT NOT NULL,
    term_id BIGINT NOT NULL,
    UNIQUE(student_id, class_id, academic_year, term_id),
    FOREIGN KEY (student_id) REFERENCES users(user_id),
    FOREIGN KEY (class_id) REFERENCES classes(class_id),
    FOREIGN KEY (term_id) REFERENCES terms(term_id)
);

CREATE TABLE IF NOT EXISTS schedule_events (
    event_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_id BIGINT NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    event_date DATE NOT NULL,
    event_period INT NOT NULL,
    description TEXT,
    makeup_date DATE,
    makeup_period INT,
    registered_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(class_id, event_date, event_period, event_type),
    FOREIGN KEY (class_id) REFERENCES classes(class_id),
    FOREIGN KEY (registered_by) REFERENCES users(user_id)
);
