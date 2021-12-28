package com.health.my_heart.di;

import com.health.my_heart.domain.repository.Repository;
import com.health.my_heart.ui.calendar.CalendarViewModel;
import com.health.my_heart.ui.content.categories.ArticlesViewModel;
import com.health.my_heart.ui.home.HomeViewModel;
import com.health.my_heart.ui.main.MainViewModel;
import com.health.my_heart.ui.profile.ProfileViewModel;
import com.health.my_heart.ui.profile.edit.EditProfileViewModel;
import com.health.my_heart.ui.profile.indicators.IndicatorsViewModel;
import com.health.my_heart.ui.quiz.QuizSharedViewModel;
import com.health.my_heart.ui.register.login.ForgotPasswordViewModel;
import com.health.my_heart.ui.register.login.LoginViewModel;
import com.health.my_heart.ui.register.registration.RegistrationViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelsModule {
    @Provides
    public static LoginViewModel provideLoginViewModel(Repository repository) {
        return new LoginViewModel(repository);
    }

    @Provides
    public static RegistrationViewModel provideRegistrationViewModel(Repository repository) {
        return new RegistrationViewModel(repository);
    }

    @Provides
    public static ForgotPasswordViewModel providePasswordViewModel(Repository repository) {
        return new ForgotPasswordViewModel(repository);
    }

    @Provides
    public static MainViewModel provideMainViewModel(Repository repository) {
        return new MainViewModel(repository);
    }

    @Provides
    public static HomeViewModel provideHomeViewModel(Repository repository) {
        return new HomeViewModel(repository);
    }

    @Provides
    public static ProfileViewModel provideProfileViewModel(Repository repository) {
        return new ProfileViewModel(repository);
    }

    @Provides
    public static EditProfileViewModel provideEditViewModel(Repository repository) {
        return new EditProfileViewModel(repository);
    }

    @Provides
    public static IndicatorsViewModel provideIndicatorsViewModel(Repository repository) {
        return new IndicatorsViewModel(repository);
    }

    @Provides
    public static QuizSharedViewModel provideQuizViewModel(Repository repository) {
        return new QuizSharedViewModel(repository);
    }

    @Provides
    public static ArticlesViewModel provideArticlesViewModel(Repository repository) {
        return new ArticlesViewModel(repository);
    }

    @Provides
    public static CalendarViewModel provideCalendarViewModel(Repository repository) {
        return new CalendarViewModel(repository);
    }
}
