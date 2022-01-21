package com.grace.hellospringbatch.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicUpdate // 일부값만 update 되는 경우 그 값만 update 쿼리를 날림
@Table(name="plain_text")
public class PlainText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;
}
