package com.example.forecastmvvm.data.db.mapper

import com.example.forecastmvvm.data.db.entity.ConditionEntity
import com.example.forecastmvvm.data.network.response.Condition

class ConditionMapper : Mapper<ConditionEntity, Condition> {
    override fun mapFromEntity(type: ConditionEntity): Condition {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(type: Condition): ConditionEntity {
        return ConditionEntity(type.text, type.icon, type.code)
    }
}