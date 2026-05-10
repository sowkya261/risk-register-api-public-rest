package com.internship.tool.scheduler;

import com.internship.tool.service.EmailService;
import com.internship.tool.service.ToolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class ReminderSchedulerTest {

    @Mock
    private ToolService toolService;
    
    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReminderScheduler reminderScheduler;

    @Test
    void testSendDailyReminders() {
        Mockito.when(toolService.getAllTools(Mockito.any(), Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
                
        reminderScheduler.sendDailyReminders();
        
        Mockito.verify(toolService, Mockito.times(1)).getAllTools(Mockito.any(), Mockito.any());
    }
}
