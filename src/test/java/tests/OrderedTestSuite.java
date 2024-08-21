package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({SignUpPageTest.class,
        LoginPageTest.class,
        MainPageTest.class,
        MyQuizzesPageTest.class,
        QuizFormPageTest.class,
        QuizzesPageTest.class,
        MyQuizzesPageTest.class,
        GamesPageTest.class})
public class OrderedTestSuite {
}
