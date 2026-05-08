package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.dto.ToolDto;
import com.internship.tool.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {
    private final ToolService toolService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ToolDto>>> getAllTools(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ToolDto> tools = toolService.getAllTools(search, pageable);
        return ResponseEntity.ok(ApiResponse.<Page<ToolDto>>builder()
                .success(true)
                .message("Tools fetched successfully")
                .data(tools)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ToolDto>> getToolById(@PathVariable Long id) {
        ToolDto tool = toolService.getToolById(id);
        return ResponseEntity.ok(ApiResponse.<ToolDto>builder()
                .success(true)
                .message("Tool fetched successfully")
                .data(tool)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ToolDto>> createTool(@Valid @RequestBody ToolDto toolDto) {
        ToolDto created = toolService.createTool(toolDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ToolDto>builder()
                .success(true)
                .message("Tool created successfully")
                .data(created)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ToolDto>> updateTool(@PathVariable Long id, @Valid @RequestBody ToolDto toolDto) {
        ToolDto updated = toolService.updateTool(id, toolDto);
        return ResponseEntity.ok(ApiResponse.<ToolDto>builder()
                .success(true)
                .message("Tool updated successfully")
                .data(updated)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Tool deleted successfully")
                .build());
    }
}
