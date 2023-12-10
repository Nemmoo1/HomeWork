import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class Event {
    private String name;
    private LocalDate date;

    public Event(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

class Birthday extends Event {
    public Birthday(String name, LocalDate date) {
        super(name, date);
    }
}

class ImportantMeeting extends Event {
    public ImportantMeeting(String name, LocalDate date) {
        super(name, date);
    }
}

class Organizer {
    private Map<Integer, Map<Integer, Map<Integer, Event>>> events;

    public Organizer() {
        events = new HashMap<>();
    }

    public void addEvent(Event event) {
        int year = event.getDate().getYear();
        int month = event.getDate().getMonthValue();
        int day = event.getDate().getDayOfMonth();

        if (!events.containsKey(year)) {
            events.put(year, new HashMap<>());
        }
        if (!events.get(year).containsKey(month)) {
            events.get(year).put(month, new HashMap<>());
        }
        events.get(year).get(month).put(day, event);
    }

    public void deleteEvent(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if (events.containsKey(year) && events.get(year).containsKey(month) && events.get(year).get(month).containsKey(day)) {
            events.get(year).get(month).remove(day);
        }
    }

    public Event getEvent(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if (events.containsKey(year) && events.get(year).containsKey(month) && events.get(year).get(month).containsKey(day)) {
            return events.get(year).get(month).get(day);
        }
        return null;
    }

    public void editEvent(LocalDate oldDate, Event newEvent) {
        deleteEvent(oldDate);
        addEvent(newEvent);
    }
}