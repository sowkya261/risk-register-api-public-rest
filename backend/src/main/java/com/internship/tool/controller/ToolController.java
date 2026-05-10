package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.dto.ToolDto;
import com.internship.tool.service.ToolService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class ToolController {
    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ToolDto>>> getAllTools(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ToolDto> tools = toolService.getAllTools(search, pageable);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Tools fetched successfully",
                tools
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ToolDto>> getToolById(@PathVariable Long id) {
        ToolDto tool = toolService.getToolById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Tool fetched successfully",
                tool
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ToolDto>> createTool(@Valid @RequestBody ToolDto toolDto) {
        ToolDto created = toolService.createTool(toolDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,
                "Tool created successfully",
                created
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ToolDto>> updateTool(@PathVariable Long id, @Valid @RequestBody ToolDto toolDto) {
        ToolDto updated = toolService.updateTool(id, toolDto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Tool updated successfully",
                updated
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Tool deleted successfully",
                null
        ));
    }
}
