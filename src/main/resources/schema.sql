CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS classes (
    class_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(255) NOT NULL,
    course_rules_name VARCHAR(255) NOT NULL,
    credit_hours INT NOT NULL,
    academic_year INT NOT NULL,
    scheduled_term VARCHAR(50) NOT NULL,
    recommended_grade INT NOT NULL
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
    scheduled_term VARCHAR(50) NOT NULL,
    UNIQUE(student_id, class_id, academic_year, scheduled_term),
    FOREIGN KEY (student_id) REFERENCES users(user_id),
    FOREIGN KEY (class_id) REFERENCES classes(class_id)
);

CREATE TABLE IF NOT EXISTS schedule_events (
    event_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_id BIGINT NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    original_date DATE NOT NULL,
    description TEXT,
    makeup_date DATE,
    makeup_period INT,
    registered_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(class_id, original_date, event_type),
    FOREIGN KEY (class_id) REFERENCES classes(class_id),
    FOREIGN KEY (registered_by) REFERENCES users(user_id)
);