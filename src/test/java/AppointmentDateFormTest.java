import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class AppointmentDateFormTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void testAppointmentDate() {
        String planningDate = generateDate(5);

        holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("span[data-test-id=city] input").setValue("Казань");
        $("span[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("span[data-test-id=date] input").sendKeys(planningDate);
        $("span[data-test-id=name] input").setValue("Тестеров Тестер");
        $("span[data-test-id=phone] input").setValue("+79797979797");
        $("label[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("div[data-test-id=notification]").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}