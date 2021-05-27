package com.example.newsapptask.database

import androidx.room.TypeConverter
import com.example.newsapptask.model.Source

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
class Converts {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}