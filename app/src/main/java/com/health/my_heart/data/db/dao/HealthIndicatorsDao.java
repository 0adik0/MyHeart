package com.health.my_heart.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.health.my_heart.data.db.entity.HealthIndicatorEntity;
import com.health.my_heart.domain.model.IndicatorType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface HealthIndicatorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertIndicator(HealthIndicatorEntity healthIndicatorEntity);

    @Query("SELECT * FROM table_health_indicators WHERE indicator_type = :type")
    Single<List<HealthIndicatorEntity>> getIndicatorsByType(IndicatorType type);
}