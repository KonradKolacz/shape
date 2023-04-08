package com.example.final_test.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDto {
    private Long id;
    private LocalDateTime modifiedAt;
    private Long shapeId;
    private String changeAuthor;
    private List<AttributeChangeDto> attributeChanges;
}
