
-- =========================
-- Books
-- =========================
INSERT INTO books (id, isbn, title, author, total_copies, available_copies)
VALUES
(1, '9780134685991', 'Java Basics', 'Joshua Bloch', 5, 5),
(2, '9781617294945', 'Spring Boot Guide', 'Craig Walls', 3, 1),
(3, '9781492078005', 'Database Fundamentals', 'Martin Kleppmann', 2, 0),
(4, '9780132350884', 'REST API Design', 'Robert C. Martin', 4, 2);

-- =========================
-- Members
-- =========================
INSERT INTO members (id, name, email)
VALUES
(1, 'Hussein Dirani', 'hussein.dirani@example.com'),
(2, 'Emma Wilson', 'emma.wilson@example.com'),
(3, 'David Brown', 'david.brown@example.com'),
(4, 'Mark Taylor', 'mark.taylor@example.com');

-- =========================
-- Loans
-- =========================
INSERT INTO loans (id, member_id, book_id, borrowed_at, returned_at, status)
VALUES
(1, 1, 2, TIMESTAMP '2026-07-01 10:00:00', NULL, 'ACTIVE'),
(2, 2, 1, TIMESTAMP '2026-06-20 09:30:00', TIMESTAMP '2026-06-28 14:00:00', 'RETURNED'),
(3, 3, 1, TIMESTAMP '2026-06-22 11:00:00', TIMESTAMP '2026-06-30 16:15:00', 'RETURNED'),
(4, 4, 4, TIMESTAMP '2026-07-03 09:00:00', NULL, 'ACTIVE');


-- =========================
-- =========================
-- Ratings
-- =========================
INSERT INTO ratings (id, member_id, book_id, score, feedback, created_at, updated_at)
VALUES
(1, 2, 1, 5, 'Very helpful book.', TIMESTAMP '2026-06-28 15:00:00', TIMESTAMP '2026-06-28 15:00:00'),
(2, 3, 1, 4, 'Easy to understand.', TIMESTAMP '2026-06-30 17:00:00', TIMESTAMP '2026-06-30 17:00:00'),
(3, 1, 2, 4, 'Good introduction to Spring Boot.', TIMESTAMP '2026-07-02 12:00:00', TIMESTAMP '2026-07-02 12:00:00');

ALTER TABLE books ALTER COLUMN id RESTART WITH 5;
ALTER TABLE members ALTER COLUMN id RESTART WITH 5;
ALTER TABLE loans ALTER COLUMN id RESTART WITH 5;
ALTER TABLE ratings ALTER COLUMN id RESTART WITH 4;