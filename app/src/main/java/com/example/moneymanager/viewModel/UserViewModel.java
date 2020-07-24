package com.example.moneymanager.viewModel;

import android.app.Application;

import com.example.moneymanager.entities.User;
import com.example.moneymanager.repository.UserRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> getUser(String email, String password) {
        return userRepository.getUser(email, password);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void updateUserProfile(int id, String name, String email){
        userRepository.updateUserProfile(id, name, email);
    }

    public LiveData<User> getUserById(int id) {
        return userRepository.getUserByIdInLiveData(id);
    }
}
