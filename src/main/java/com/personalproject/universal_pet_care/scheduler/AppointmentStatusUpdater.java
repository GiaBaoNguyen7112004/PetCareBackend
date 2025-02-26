package com.personalproject.universal_pet_care.scheduler;

import com.personalproject.universal_pet_care.entity.Appointment;
import com.personalproject.universal_pet_care.enums.AppointmentStatus;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.repository.AppointmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentStatusUpdater {
    AppointmentRepository appointmentRepository;
    TaskScheduler taskScheduler;
    Map<Long, List<ScheduledFuture<?>>> scheduledTasks = new ConcurrentHashMap<>();

    public void scheduleStatusUpdate(Long appointmentId) {
        cancelScheduledTasks(appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        if (appointment.getStatus().equals(AppointmentStatus.WAITING_FOR_APPROVAL))
            throw new AppException(ErrorCode.APPOINTMENT_NOT_APPROVED);
        LocalDateTime startTime = LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime());

        List<ScheduledFuture<?>> tasks = new ArrayList<>();
        Map<AppointmentStatus, Duration> statusSchedule = Map.of(
                AppointmentStatus.UP_COMING, Duration.ofMinutes(-2),
                AppointmentStatus.ON_GOING, Duration.ZERO
        );

        for (var entry : statusSchedule.entrySet()) {
            long delay = Duration.between(LocalDateTime.now(), startTime).plus(entry.getValue()).toMillis();
            if (delay < 0) continue;
            ScheduledFuture<?> task = taskScheduler.schedule(
                    () -> updateAppointmentStatus(appointment, entry.getKey()),
                    Instant.ofEpochMilli(System.currentTimeMillis() + delay));
            tasks.add(task);
        }
        scheduledTasks.put(appointmentId, tasks);
    }

    public void updateAppointmentStatus(Appointment appointment, AppointmentStatus status) {
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    public void cancelScheduledTasks(Long appointmentId) {
        List<ScheduledFuture<?>> tasks = scheduledTasks.remove(appointmentId);
        if (tasks != null) {
            for (ScheduledFuture<?> task : tasks) {
                task.cancel(false);
            }
        }
    }
}
