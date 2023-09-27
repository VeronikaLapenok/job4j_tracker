package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

public class TrackerMockTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "dd-MMMM-EEEE-yyyy HH:mm:ss");

    @Test
    public void whenDeleteItemMockTest() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Input input = mock(Input.class);
        tracker.add(new Item("New item"));
        DeleteAction deleteAction = new DeleteAction(output);

        when(input.askInt(any(String.class))).thenReturn(1);

        deleteAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Delete item ===" + ln
                + "Заявка удалена успешно." + ln);
    }

    @Test
    public void whenFindItemByIdMockTest() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Input input = mock(Input.class);
        Item item = new Item("New item");
        tracker.add(item);
        var date = item.getCreated();
        FindByIdAction findByIdAction = new FindByIdAction(output);

        when(input.askInt(any(String.class))).thenReturn(1);

        findByIdAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Find item by id ===" + ln
                + item + ln);
    }

    @Test
    public void whenFindItemByNameMockTest() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Input input = mock(Input.class);
        Item item = new Item("New item");
        tracker.add(item);
        var date = item.getCreated();
        FindByNameAction findByNameAction = new FindByNameAction(output);

        when(input.askStr(any(String.class))).thenReturn("New item");

        findByNameAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Find item by name ===" + ln
                + item + ln);
    }
}
