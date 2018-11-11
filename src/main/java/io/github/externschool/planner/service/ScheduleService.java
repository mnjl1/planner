package io.github.externschool.planner.service;

import io.github.externschool.planner.dto.ScheduleEventDTO;
import io.github.externschool.planner.dto.ScheduleEventReq;
import io.github.externschool.planner.entity.User;
import io.github.externschool.planner.entity.schedule.ScheduleEvent;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 * @author Benkoff (mailto.benkoff@gmail.com)
 */
public interface ScheduleService {
    ScheduleEvent createEvent(User owner, ScheduleEventReq eventReq);

    ScheduleEvent createEventWithDuration(User owner, ScheduleEventDTO eventDTO, int minutes);

    ScheduleEvent addParticipant(User user, ScheduleEvent event);

    void removeParticipant(Long id);

    ScheduleEvent getEventById(long id);

    LocalDate getCurrentWeekFirstDay();

    LocalDate getNextWeekFirstDay();

    List<LocalDate> getWeekStartingFirstDay(LocalDate firstDay);

    List<ScheduleEvent> getActualEventsByOwnerAndDate(User owner, LocalDate date);

    List<ScheduleEvent> getEventsByOwner(User owner);

    void cancelEvent(long id);

    void deleteEvent(long id);
}
