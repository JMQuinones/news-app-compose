package com.jmquinones.newsappcompose.data.db

import androidx.room.TypeConverter
import com.jmquinones.newsappcompose.data.models.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}