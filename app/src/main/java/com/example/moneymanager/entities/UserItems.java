package com.example.moneymanager.entities;


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

    @Entity(tableName = "user_items", indices = {@Index("userId")}, foreignKeys = @ForeignKey(entity = User.class,
            parentColumns = "id",
            childColumns = "userId",
            onDelete = CASCADE))
    public class UserItems {
        @PrimaryKey(autoGenerate = true)
        private int id;
        private String category;
        private String amount;
        private String date;
        private String description;
        private String transactionType;

    @ColumnInfo(name = "userId")
    private int userId;

    @Ignore
    public UserItems() {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
    }


    public UserItems(int id, String category, String amount, String date,String description, String transactionType) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }
    public String getTransactionType(){
        return transactionType;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
