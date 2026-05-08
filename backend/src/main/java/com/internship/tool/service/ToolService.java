
package com.internship.tool.service;

import com.internship.tool.dto.ToolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToolService {
    ToolDto createTool(ToolDto toolDto);
    ToolDto updateTool(Long id, ToolDto toolDto);
    ToolDto getToolById(Long id);
    Page<ToolDto> getAllTools(String search, Pageable pageable);
    void deleteTool(Long id);
}
