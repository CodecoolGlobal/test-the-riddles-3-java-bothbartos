package tests;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CredentialsLoader {
    private static final Dotenv dotenv = Dotenv.load();


    public static Stream<Arguments> getLoginCredentials() {
        return Stream.of(
                Arguments.of(dotenv.get("USERNAME_1"), dotenv.get("PASSWORD_1")),
                Arguments.of(dotenv.get("USERNAME_2"), dotenv.get("PASSWORD_2")),
                Arguments.of(dotenv.get("USERNAME_3"), dotenv.get("PASSWORD_3"))
        );
    }

    public static Stream<Arguments> getSignUpCredentials() {
        return Stream.of(
                Arguments.of(dotenv.get("USERNAME_1"), dotenv.get("EMAIL_1"), dotenv.get("PASSWORD_1")),
                Arguments.of(dotenv.get("USERNAME_2"), dotenv.get("EMAIL_2"), dotenv.get("PASSWORD_2")),
                Arguments.of(dotenv.get("USERNAME_3"), dotenv.get("EMAIL_3"), dotenv.get("PASSWORD_3"))
        );
    }

    public static Stream<Arguments> loadTimeCredentials() {
        return  Stream.of(
          Arguments.of("-1", dotenv.get("QUIZ_TITLE_1") ,dotenv.get("QUIZ_QUESTION_1"), dotenv.get("QUIZ_1_ANSWER_1"), dotenv.get("QUIZ_1_ANSWER_2"), "30" ),
          Arguments.of("45",dotenv.get("QUIZ_TITLE_2") , dotenv.get("QUIZ_QUESTION_2"), dotenv.get("QUIZ_1_ANSWER_3"), dotenv.get("QUIZ_1_ANSWER_2"), "45" ),
          Arguments.of("123456", dotenv.get("QUIZ_TITLE_5") , dotenv.get("QUIZ_QUESTION_1"), dotenv.get("QUIZ_1_ANSWER_1"), dotenv.get("QUIZ_1_ANSWER_2"), "123456" )
        );
    }
}

