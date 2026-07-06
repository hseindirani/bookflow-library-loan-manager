package com.nordea.bookflow.rating;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("ratings")
public class Rating {

    @Id
    private Long id;

    private Long memberId;
    private Long bookId;

    private Integer score;
    private String feedback;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Rating() {
    }

    public Rating(Long id,
                  Long memberId,
                  Long bookId,
                  Integer score,
                  String feedback,
                  LocalDateTime createdAt,
                  LocalDateTime updatedAt) {

        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.score = score;
        this.feedback = feedback;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Rating createNewRating(Long memberId,
                                Long bookId,
                                Integer score,
                                String feedback,
                                LocalDateTime createdAt) {

        return new Rating(null, memberId, bookId, score, feedback, createdAt, createdAt);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public Integer getScore() {
        return score;
    }

    public String getFeedback() {
        return feedback;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateRating(Integer score, String feedback, LocalDateTime updatedAt) {
        this.score = score;
        this.feedback = feedback;
        this.updatedAt = updatedAt;
    }
}