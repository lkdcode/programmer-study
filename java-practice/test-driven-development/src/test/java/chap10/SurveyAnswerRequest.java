package chap10;

import chap03.PayData;

import javax.print.DocFlavor;
import java.util.List;

public class SurveyAnswerRequest {
    static class Builder {

        public Builder surveyId(Long id) {
            return this;
        }

        public Builder repondentId(Long id) {
            return this;
        }

        public Builder answer(List<Integer> answer) {
            return this;
        }

        public SurveyAnswerRequest build() {
            return new SurveyAnswerRequest();
        }

    }
}
