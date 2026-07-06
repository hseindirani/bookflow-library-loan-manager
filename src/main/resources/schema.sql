CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL,
    CONSTRAINT chk_book_copies CHECK (total_copies >= 0 AND available_copies >= 0 AND available_copies <= total_copies)
);

CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    borrowed_at TIMESTAMP NOT NULL,
    returned_at TIMESTAMP NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_loans_member FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT fk_loans_book FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT chk_loan_status CHECK (status IN ('ACTIVE', 'RETURNED'))
);

CREATE TABLE ratings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    score INT NOT NULL,
    feedback VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_ratings_member FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT fk_ratings_book FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT uq_rating_member_book UNIQUE (member_id, book_id),
    CONSTRAINT chk_rating_score CHECK (score BETWEEN 1 AND 5)
);