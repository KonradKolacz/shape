package com.example.final_test.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "changes")
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime modifiedAt;
    private Long shapeId;
    private String changeAuthor;
    @OneToMany(mappedBy = "change", cascade = CascadeType.ALL)
    private List<AttributeChange> attributeChanges;

    public Change(LocalDateTime modifiedAt, Long shapeId, String changeAuthor, List<AttributeChange> attributeChanges) {
        this.modifiedAt = modifiedAt;
        this.shapeId = shapeId;
        this.changeAuthor = changeAuthor;
        this.attributeChanges = attributeChanges;
        this.attributeChanges.forEach(aCh->aCh.setChange(this));
    }

}
