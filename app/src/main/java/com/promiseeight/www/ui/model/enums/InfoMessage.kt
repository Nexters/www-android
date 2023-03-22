package com.promiseeight.www.ui.model.enums

sealed class InfoMessage() {
    object Ready : InfoMessage()
    object PeriodWarning14 : InfoMessage()
    object PeriodWarningEndStart : InfoMessage()
    data class PlaceWarningJoin(val message: String? = null) : InfoMessage()
}