package com.example.final_test.controller;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.command.ShapeUpdateCommand;
import com.example.final_test.dto.ChangeDto;
import com.example.final_test.dto.ShapeDto;
import com.example.final_test.service.ShapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shapes")
public class ShapeController {
    private final ShapeService shapeService;

    @PostMapping
    public ResponseEntity<ShapeDto> add(@RequestBody @Valid ShapeCommand shapeCommand) {
        return new ResponseEntity<>(shapeService.add(shapeCommand), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ShapeDto>> findAll(@RequestParam(required = false) Map<String, String> queryParams, Pageable pageable) {
        return new ResponseEntity<>(shapeService.getShapes(queryParams, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authorChecker.hasPermissionToUpdate(#id)")
    public ResponseEntity<ShapeDto> updateShape(@PathVariable("id") Long id, @RequestBody @Valid ShapeUpdateCommand shapeUpdateCommand) {
        return new ResponseEntity<>(shapeService.updateShape(id, shapeUpdateCommand), HttpStatus.OK);
    }

    @GetMapping("/{id}/changes")
    @PreAuthorize("@authorChecker.hasPermissionToUpdate(#id)")
    public ResponseEntity<List<ChangeDto>> getShapeChanges(@PathVariable("id") Long id) {
        return new ResponseEntity<>(shapeService.getShapeChanges(id), HttpStatus.OK);
    }
}
