package com.internship.tool.service.impl;

import com.internship.tool.dto.ToolDto;
import com.internship.tool.entity.Tool;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.ToolRepository;
import com.internship.tool.service.ToolService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ToolServiceImpl implements ToolService {
    private final ToolRepository toolRepository;

    public ToolServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "tools", allEntries = true)
    public ToolDto createTool(ToolDto toolDto) {
        Tool tool = new Tool(
                toolDto.getName(),
                toolDto.getDescription(),
                toolDto.getActive() != null ? toolDto.getActive() : true
        );
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "tools", allEntries = true)
    public ToolDto updateTool(Long id, ToolDto toolDto) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + id));
        if (toolDto.getName() != null) tool.setName(toolDto.getName());
        if (toolDto.getDescription() != null) tool.setDescription(toolDto.getDescription());
        if (toolDto.getActive() != null) tool.setActive(toolDto.getActive());
        tool = toolRepository.save(tool);
        return toDto(tool);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Cacheable(value = "tools", key = "#id")
    public ToolDto getToolById(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + id));
        return toDto(tool);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Cacheable(value = "tools", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #search")
    public Page<ToolDto> getAllTools(String search, Pageable pageable) {
        Page<Tool> page;
        if (search != null && !search.isBlank()) {
            page = toolRepository.findAll(pageable) /* Replace with search logic if needed */;
        } else {
            page = toolRepository.findAll(pageable);
        }
        List<ToolDto> dtos = page.getContent().stream().map(this::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "tools", allEntries = true)
    public void deleteTool(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool not found with id: " + id));
        toolRepository.delete(tool);
    }

    private ToolDto toDto(Tool tool) {
        ToolDto dto = new ToolDto();
        BeanUtils.copyProperties(tool, dto);
        // copy timestamps if present
        try {
            dto.setCreatedAt(tool.getCreatedAt());
        } catch (Exception ignored) {}
        return dto;
    }
}
