package com.example.nytime.presentation;

public interface ItemClickListener<T> {
    void onItemClick(int position, T model);
}
