package com.me.calendar.repository;

import android.content.Context;

import com.me.calendar.repository.dao.EventsDao;
import com.me.calendar.repository.dao.NotificationsDao;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.EventNotification;
import com.me.calendar.repository.model.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class EventService {
    private EventsDao eventsDao;
    private NotificationsDao notificationDao;
    private Store store;

    public EventService(Context applicationContext) {
        store = new Store(applicationContext);
        eventsDao = new EventsDao(store);
        notificationDao = new NotificationsDao(store);
    }

    public void addNewEvent(Event event) {
        eventsDao.addNewEvent(event);
        EventNotification eventNotification = event.getEventNotification();

        if (eventNotification != EventNotification.alarm_no) {

            LocalDateTime notificationDate = event.getLocalDateTime();

            if (eventNotification == EventNotification.alarm_10_min_before) {
                notificationDate.minusMinutes(10);

            } else if (eventNotification == EventNotification.alarm_30_min_before) {
                notificationDate.minusMinutes(30);
            } else if (eventNotification == EventNotification.alarm_1_hour_before) {
                notificationDate.minusHours(1);
            } else if (eventNotification == EventNotification.alarm_1_day_before) {
                notificationDate.minusDays(1);
            }

            Notification notification = new Notification(event.getEventId(), notificationDate);
            notificationDao.addNotification(notification);
        }
    }

    public void deleteEventById(long eventId) {
        eventsDao.deleteById(eventId);
        notificationDao.deleteById(eventId);
    }

    public ArrayList<Event> eventsForWeekAndTime(LocalDate date, LocalTime time) {
        return eventsDao.selectForWeekAndTime(date, time);
    }

    public ArrayList<Event> eventsForMonth(LocalDate date) {
        return eventsDao.selectForMonth(date);
    }

    public ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        return eventsDao.selectByDateTime(date, time);
    }

    public void deleteAllEvents() {
        eventsDao.deleteAllEvents();
        notificationDao.deleteAllNotifications();
    }

    public void updateEvent(Event editEvent) {
        eventsDao.updateEvent(editEvent);
        notificationDao.updateNotificationForEvent(editEvent);
    }

    public void deleteAllTables() {
        eventsDao.dropTable();
        notificationDao.dropTable();
    }
}
