package org.itmo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MagicCalendarTest {
    private MagicCalendar calendar;

    @BeforeEach
    void intit() {
        calendar = new MagicCalendar();
    }

    @Test
    void testReplaceWorkWithPersonal() {
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));

    }

    @Test
    void test0() {
        assertTrue(calendar.scheduleMeeting("Alice", "01:01", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "09:51", MagicCalendar.MeetingType.WORK));
    }

    @Test
    void test1() {
        assertThrows(IllegalArgumentException.class, () -> calendar.scheduleMeeting("Alice", "23:61", MagicCalendar.MeetingType.PERSONAL));
        assertThrows(IllegalArgumentException.class, () -> calendar.scheduleMeeting("Alice", "25:00", MagicCalendar.MeetingType.PERSONAL));
        assertThrows(IllegalArgumentException.class, () -> calendar.scheduleMeeting("Alice", "23:61", MagicCalendar.MeetingType.WORK));
        assertThrows(IllegalArgumentException.class, () -> calendar.scheduleMeeting("Alice", "25:00", MagicCalendar.MeetingType.WORK));

    }

    @Test
    public void test2() {
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
        assertFalse(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void test3() {
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.scheduleMeeting("Alice", "10:05", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.scheduleMeeting("Alice", "10:59", MagicCalendar.MeetingType.PERSONAL));
    }

    @Test
    public void test4() {
        assertTrue(calendar.scheduleMeeting("Alice", "09:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "11:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "13:00", MagicCalendar.MeetingType.WORK));
        assertEquals(calendar.getMeetings("Alice").size(), 5);
        assertFalse(calendar.scheduleMeeting("Alice", "14:00", MagicCalendar.MeetingType.WORK));
        assertEquals(calendar.getMeetings("Alice").size(), 5);
        assertTrue(calendar.cancelMeeting("Alice", "13:00"));
        assertTrue(calendar.scheduleMeeting("Alice", "13:00", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.cancelMeeting("Alice", "13:00"));
    }

    @Test
    public void test5() {
        calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK);
        calendar.scheduleMeeting("Alice", "11:00", MagicCalendar.MeetingType.PERSONAL);
        List<String> meetings = calendar.getMeetings("Alice");
        assertEquals(2, meetings.size());
        assertTrue(meetings.contains("10:00"));
        assertTrue(meetings.contains("11:00"));
    }

    @Test
    public void test6() {
        calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK);
        assertTrue(calendar.cancelMeeting("Alice", "10:00"));
    }

    @Test
    public void test7() {
        assertFalse(calendar.cancelMeeting("Alice", "10:00"));
    }

    @Test
    public void test8() {
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.cancelMeeting("Alice", "11:00"));
        assertEquals(calendar.getMeetings("Alice").size(), 1);
        assertFalse(calendar.cancelMeeting("Alice", "10:00"));
        assertEquals(calendar.getMeetings("Alice").size(), 1);
    }

    @Test
    public void test9() {
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "10:01", MagicCalendar.MeetingType.PERSONAL));
        assertEquals(calendar.getMeetings("Alice").size(), 1);
        assertEquals(calendar.getMeetings("Alice").getFirst(), "10:00");
    }

    @Test
    public void test10() {
        assertTrue(calendar.scheduleMeeting("Alice1", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice2", "10:00", MagicCalendar.MeetingType.WORK));
        assertEquals(calendar.getMeetings("Alice1").size(), 1);
        assertEquals(calendar.getMeetings("Alice2").size(), 1);
        assertTrue(calendar.cancelMeeting("Alice2", "10:00"));
        assertEquals(calendar.getMeetings("Alice1").size(), 1);
        assertEquals(calendar.getMeetings("Alice2").size(), 0);
    }

    @Test
    public void test11() {
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void test12() {
        assertFalse(calendar.cancelMeeting("Alice", "10:00"));
        assertEquals(calendar.getMeetings("Alice").size(), 0);
    }

    @Test
    public void test13() {
        assertFalse(calendar.cancelMeeting("Alice", "10:00"));
        assertThrows(IllegalArgumentException.class, () -> calendar.cancelMeeting("Alice2", "29:51"));
        assertEquals(calendar.getMeetings("Alice").size(), 0);
        assertEquals(calendar.getMeetings("Alice1").size(), 0);
        assertEquals(calendar.getMeetings("Alice2").size(), 0);
    }

    @Test
    public void test14 () {

    }
}
