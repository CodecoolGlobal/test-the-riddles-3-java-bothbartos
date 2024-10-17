package tests.Utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;
import java.util.stream.Stream;

import static tests.Utils.Utils.*;

public class CredentialsLoader {
    private static final Dotenv dotenv = Dotenv.load();

    public static Stream<Arguments> getSignUpCredentials() {
        return Stream.of(
                Arguments.of(createRandomUsername(), createRandomEmail(), createRandomPassword()),
                Arguments.of(createRandomUsername(), createRandomEmail(), createRandomPassword()),
                Arguments.of(createRandomUsername(), createRandomEmail(), createRandomPassword())
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

