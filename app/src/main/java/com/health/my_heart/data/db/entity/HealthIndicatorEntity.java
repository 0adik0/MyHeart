package com.health.my_heart.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.health.my_heart.domain.model.IndicatorType;

@Entity(tableName = "table_health_indicators")
public class HealthIndicatorEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "indicator1")
    public float indicator1;
    @ColumnInfo(name = "indicator2")
    public float indicator2;
    @ColumnInfo(name = "indicator_type")
    public IndicatorType indicatorType;
}
