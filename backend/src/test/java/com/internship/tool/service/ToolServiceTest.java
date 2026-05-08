package com.internship.tool.service;

import com.internship.tool.dto.ToolDto;
import com.internship.tool.entity.Tool;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.ToolRepository;
import com.internship.tool.service.impl.ToolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ToolServiceTest {
    @Mock
    private ToolRepository toolRepository;
    @InjectMocks
    private ToolServiceImpl toolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTool() {
        ToolDto dto = ToolDto.builder().name("Tool1").description("Desc").active(true).build();
        Tool tool = Tool.builder().id(1L).name("Tool1").description("Desc").active(true).build();
        when(toolRepository.save(any(Tool.class))).thenReturn(tool);
        ToolDto result = toolService.createTool(dto);
        assertEquals("Tool1", result.getName());
        assertEquals("Desc", result.getDescription());
        assertTrue(result.getActive());
    }

    @Test
    void testGetToolById_NotFound() {
        when(toolRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> toolService.getToolById(1L));
    }

    @Test
    void testGetAllTools() {
        Tool tool1 = Tool.builder().id(1L).name("Tool1").description("Desc1").active(true).build();
        Tool tool2 = Tool.builder().id(2L).name("Tool2").description("Desc2").active(true).build();
        List<Tool> tools = Arrays.asList(tool1, tool2);
        Pageable pageable = PageRequest.of(0, 10);
        when(toolRepository.findAll(pageable)).thenReturn(new PageImpl<>(tools));
        Page<ToolDto> page = toolService.getAllTools(null, pageable);
        assertEquals(2, page.getTotalElements());
    }
}
