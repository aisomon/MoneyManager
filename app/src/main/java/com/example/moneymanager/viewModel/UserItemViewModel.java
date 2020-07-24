package com.example.moneymanager.viewModel;

import android.app.Application;
import com.example.moneymanager.entities.UserItems;
import com.example.moneymanager.repository.ItemRepository;
import com.example.moneymanager.repository.UserRepository;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;
    private UserRepository userRepository;

    public UserItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);

    }
        public LiveData<List<UserItems>> getAllItemsByUserId(int userId) {
            return itemRepository.getAllItemByUserId(userId);
        }
        public LiveData<List<UserItems>> loadAllUserItemByCategory(String category, int userId) {
            return itemRepository.getAllUserItemByCategory(category, userId);
        }

        public void addItems(int id, String category, String amount , String date, String transactionType, String description) {
            itemRepository.addItems(id, category, amount, date,transactionType, description);
    }
}
