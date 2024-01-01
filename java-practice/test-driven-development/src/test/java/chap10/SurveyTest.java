package chap10;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class SurveyTest {
    private SurveyRepository surveyRepository;
    private List<Integer> answers = Arrays.asList(1, 2, 3, 4);
    private Long respondentId = 100L;

    @DisplayName("답변에 성공하면 결과 저장함")
    @Test
    void saveAnswerSuccessfully_bad() {
        Survey survey = SurveyFactory.createApprovedSurvey(1L);
        surveyRepository.save(survey);

        SurveyAnswerRequest surveyAnswer = new SurveyAnswerRequest.Builder()
                .surveyId(survey.getId())
                .repondentId(respondentId)
                .answer(answers)
                .build();
        // ...
    }

    @DisplayName("답변에 성공하면 결과 저장함")
    @Test
    void saveAnswerSuccessfully_good() {
        Survey survey = SurveyFactory.createApprovedSurvey(1L);
        surveyRepository.save(survey);

        SurveyAnswerRequest surveyAnswer = new SurveyAnswerRequest.Builder()
                .surveyId(1L)
                .repondentId(100L)
                .answer(Arrays.asList(1, 2, 3, 4))
                .build();
        // ...
    }
}
