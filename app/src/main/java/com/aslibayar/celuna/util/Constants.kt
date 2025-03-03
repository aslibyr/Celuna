package com.aslibayar.celuna.util

object Constants {
    // Collection names
    const val USERS_COLLECTION = "users"
    const val EVENTS_COLLECTION = "events"

    // Navigation routes
    const val AUTH_GRAPH = "auth"
    const val MAIN_GRAPH = "main"
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val FORGOT_PASSWORD_SCREEN = "forgot_password"
    const val CALENDAR_SCREEN = "calendar"
    const val EVENT_DETAIL_SCREEN = "event_detail"
    const val ADD_EDIT_EVENT_SCREEN = "add_edit_event"
    const val PROFILE_SCREEN = "profile"

    // Arguments
    const val EVENT_ID_ARG = "eventId"

    // Error messages
    const val ERROR_SOMETHING_WENT_WRONG = "Bir şeyler yanlış gitti. Lütfen tekrar deneyin."
    const val ERROR_INVALID_EMAIL = "Geçersiz e-posta adresi"
    const val ERROR_INVALID_PASSWORD = "Şifre en az 6 karakter olmalıdır"
    const val ERROR_PASSWORDS_NOT_MATCH = "Şifreler eşleşmiyor"
    const val ERROR_EMAIL_ALREADY_IN_USE = "Bu e-posta adresi zaten kullanımda"
    const val ERROR_WEAK_PASSWORD = "Şifre çok zayıf"
    const val ERROR_USER_NOT_FOUND = "Kullanıcı bulunamadı"
    const val ERROR_WRONG_PASSWORD = "Yanlış şifre"
} 