package com.company.utils;

import java.util.List;

public class KeyGenerator <T>{
    // Class that simulates auto primary key generator system that some DBs have
    private Integer key;

    public KeyGenerator()
    {
        key = 0;
    }

    public KeyGenerator(Integer lastKey)
    {
        key = lastKey + 1;
    }

    public Integer nextKey()
    {
        return key++;
    }

    public void updateKey(Integer newKey)
    {
        if (this.key < newKey)
            this.key = newKey + 1;
    }
}
