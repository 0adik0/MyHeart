package com.health.my_heart.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.health.my_heart.domain.model.QuizResult;
import com.health.my_heart.domain.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class QuizSharedViewModel extends ViewModel {
    public static final int LAST_INDEX = 12;
    public static final int CHEST_PAIN = 0;
    public static final int HEADACHE = 1;
    public static final int DYSPNEA = 2;
    public static final int PRESSURE_OK = 3;
    public static final int SWELLING = 4;
    public static final int PILLS = 5;
    public static final int SPORT = 6;

    public static final int BAD_HABITS = 7;
    public static final int DIET = 8;
    public static final int PROPER_NUTRITION = 9;
    public static final int SLEEP = 10;
    public static final int FAINTING = 11;
    public static final int TACHYCARDIA = 12;

    MutableLiveData<Integer> currentQuestionId;
    MutableLiveData<QuizResult> quizResult;
    private final Repository repository;

    private final Map<Integer, Boolean> answers;
    private final List<String> questions;

    @Inject
    public QuizSharedViewModel(Repository repository) {
        this.repository = repository;
        currentQuestionId = new MutableLiveData<>(0);
        quizResult = new MutableLiveData<>();
        answers = new HashMap<>();
        questions = repository.getQuestions();
    }

    public String getQuestion(int id) {
        return questions.get(id);
    }

    public void setAnswer(boolean result) {
        int currentId = currentQuestionId.getValue();
        answers.put(currentId, result);
        currentQuestionId.postValue(++currentId);
    }

    public void getResult() {
        QuizResult result;
        result = calculateResult();
        quizResult.postValue(result);
        repository.saveResult(result);
    }

    private QuizResult calculateResult() {
        if (answers.get(CHEST_PAIN) || answers.get(FAINTING))
            return QuizResult.RED;
        if (answers.get(HEADACHE) || answers.get(DYSPNEA) || !answers.get(PRESSURE_OK) || answers.get(SWELLING)
                || answers.get(TACHYCARDIA))
            return QuizResult.YELLOW;
        return QuizResult.GREEN;
    }



    public void skipSurvey() {
        repository.skipSurvey();
    }
}

