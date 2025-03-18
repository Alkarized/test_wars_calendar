# Магический календарь 🗓️🔮

## Описание задачи

Реализуйте класс `MagicCalendar`, который представляет собой волшебный календарь для планирования встреч пользователей. Каждый пользователь может запланировать встречи в течение дня, но действуют следующие правила и ограничения:

### Типы встреч

Встречи бывают двух фиксированных типов, задаваемых с помощью перечисления `MeetingType`:

- `WORK` – рабочая встреча
- `PERSONAL` – личная встреча

### Планирование встречи в одном временном слоте

- Встречи планируются на временные слоты, представленные строками (например, `"10:00"`).
- Если в указанный временной слот уже запланирована встреча, новая встреча не добавляется, за исключением случая:
    - Если уже запланирована встреча типа `WORK`, а новая встреча имеет тип `PERSONAL`, то новая встреча заменяет существующую.
    - Если в указанное время уже находится встреча типа `PERSONAL`, то попытка добавить встречу любого типа должна быть отклонена.

### Ограничение по количеству встреч в день

- Каждый пользователь может иметь не более **5 запланированных встреч** за один день.
- Если добавление новой встречи приводит к превышению этого лимита, метод `scheduleMeeting` должен возвращать `false`.

### Отмена встреч

- Календарь должен позволять отменять встречи с помощью метода `cancelMeeting(String user, String time)`.
- Отменять можно **только встречи типа `WORK`**. Попытка отменить встречу типа `PERSONAL` должна вернуть `false` и не изменять расписание.
- При успешной отмене временной слот освобождается и может быть использован для планирования новой встречи.

### Мультипользовательская поддержка

- Встречи для разных пользователей хранятся независимо, то есть планирование или отмена встречи одного пользователя не влияет на встречи другого.

## Интерфейс класса

```java
public class MagicCalendar {
    // Перечисление типов встреч
    public enum MeetingType {
        WORK, PERSONAL
    }

    /**
     * Запланировать встречу для пользователя.
     *
     * @param user имя пользователя
     * @param time временной слот (например, "10:00")
     * @param type тип встречи (WORK или PERSONAL)
     * @return true, если встреча успешно запланирована, false если:
     *         - в этот временной слот уже есть встреча, и правило замены не выполняется,
     *         - лимит в 5 встреч в день уже достигнут.
     */
    public boolean scheduleMeeting(String user, String time, MeetingType type) {
        // Реализация метода
        return false;
    }
    
    /**
     * Получить список всех встреч пользователя.
     *
     * @param user имя пользователя
     * @return список временных слотов, на которые запланированы встречи.
     */
    public List<String> getMeetings(String user) {
        // Реализация метода
        return new ArrayList<>();
    }

    /**
     * Отменить встречу для пользователя по заданному времени.
     *
     * @param user имя пользователя
     * @param time временной слот, который нужно отменить.
     * @return true, если встреча была успешно отменена; false, если:
     *         - встреча в указанное время отсутствует,
     *         - встреча имеет тип PERSONAL (отменять можно только WORK встречу).
     */
    public boolean cancelMeeting(String user, String time) {
        // Реализация метода
        return false;
    }
}
```

## Примеры тестов (JUnit 5)

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagicCalendarTest {

    @Test
    public void testReplaceWorkWithPersonal() {
        MagicCalendar calendar = new MagicCalendar();
        
        // Планируем рабочую встречу на "10:00"
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        
        // Попытка добавить вторую рабочую встречу на "10:00" должна вернуть false
        assertFalse(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        
        // Добавление личной встречи на "10:00" заменяет рабочую встречу
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        
        // После замены, попытка добавить рабочую встречу снова должна вернуть false
        assertFalse(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testDailyMeetingLimit() {
        MagicCalendar calendar = new MagicCalendar();
        
        // Планируем 5 встреч для пользователя "Bob" в разные временные слоты
        assertTrue(calendar.scheduleMeeting("Bob", "09:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertTrue(calendar.scheduleMeeting("Bob", "11:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "12:00", MagicCalendar.MeetingType.PERSONAL));
        assertTrue(calendar.scheduleMeeting("Bob", "13:00", MagicCalendar.MeetingType.WORK));
        
        // Попытка добавить 6-ю встречу должна вернуть false
        assertFalse(calendar.scheduleMeeting("Bob", "14:00", MagicCalendar.MeetingType.WORK));
    }
}
