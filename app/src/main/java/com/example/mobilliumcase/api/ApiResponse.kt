package com.example.mobilliumcase.api

sealed class MovieResource<T>(
    val data: T? = null,
    val message: String? = null
) {

    // We'll wrap our data in this 'Success'
// class in case of success response from api
    class Success<T>(data: T) : MovieResource<T>(data = data)

    // We'll pass error message wrapped in this 'Error'
// class to the UI in case of failure response
    class Error<T>(errorMessage: String) : MovieResource<T>(message = errorMessage)

}

