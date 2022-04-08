package bo.ivan;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DemoqaTests {

    String firstName = "Ivan",
            lastName = "Ivanov",
            userEmail = "ivan@ivanov.su",
            gender = "Male",
            userNumber = "8919123456",
            dayOfBirth = "07",
            monthOfBirth = "May",
            yearOfBirth = "1980",
            dateOfBirth = String.format("%s %s,%s", dayOfBirth, monthOfBirth, yearOfBirth),
            subjects = "Comp",
            hobbies = "Reading",
            picturePath = "./img/1.png",
            currentAddress = "testing str., 20",
            state = "Haryana",
            city = "Karnal";

    String[] picturePathArr = picturePath.split("/");
    String pictureName = picturePathArr[picturePathArr.length - 1];

    @BeforeAll
    static void configTest() {
        Configuration.baseUrl = "https://demoqa.com/";
        /*
        чтобы была видна кнопка Submit на маленьком экране без уменьшения масштаба
        можно запустить тест с имитацией мобильного телефона,
        либо со значением в browserSize разрешения мобильного экрана
        */
        System.setProperty("chromeoptions.mobileEmulation", "deviceName=Nexus 5");
//        Configuration.browserSize = "1920x1080";
    }

    @Test
    void formTest() {
        Selenide.open("automation-practice-form");
//        Selenide.zoom(0.75);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(userNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(monthOfBirth);
        $(".react-datepicker__year-select").selectOption(yearOfBirth);
        $(".react-datepicker__day--0" + dayOfBirth + ":not(.react-datepicker__day--outside-month)").click();
        $("#subjectsInput").val(subjects).pressEnter();
        $("#hobbiesWrapper").$(byText(hobbies)).scrollIntoView(true).click();
        $("#uploadPicture").uploadFromClasspath(picturePath);
        $("#currentAddress").setValue(currentAddress);
        $("#state").scrollIntoView(true).click();
        $(byText(state)).click();
        $("#city").click();
        $(byText(city)).click();
        $("#submit").click();


        //Expected result
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $("div.table-responsive").shouldHave(
                text(firstName + " " + lastName),
                text(userEmail),
                text(gender),
                text(userNumber),
                text(dateOfBirth),
                text(subjects),
                text(hobbies),
                text(pictureName),
                text(currentAddress),
                text(state + " " + city)
       );
    }
}