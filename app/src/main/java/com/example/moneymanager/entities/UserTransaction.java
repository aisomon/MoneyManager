package com.example.moneymanager.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "user_transaction", indices = {@Index("userId")}, foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = CASCADE))
public class UserTransaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String amount;
    private String date;
    private String transactionType;
    @ColumnInfo(name = "userId")
    private int userId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
            this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(String date){
        this.date= date;
    }
    public String getDate(){
        return date;
    }

    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }
    public String getTransactionType(){
        return transactionType;
    }


}
