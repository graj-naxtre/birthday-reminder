package com.example.birthdayboom.data.database.mappers

interface Target

interface BiMapper<S:Any, T:Target> {
fun convert(source: S): T

fun convert(source: T): S
}