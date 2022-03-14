package com.company.kun.uz.entity;

import com.company.kun.uz.enums.LikeStatus;
import com.company.kun.uz.enums.LikeType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column(name = "action_id")
    private Integer action_id;

    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @Enumerated(EnumType.STRING)
    private LikeType type;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
