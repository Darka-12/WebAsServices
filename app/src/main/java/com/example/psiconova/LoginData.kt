package com.example.psiconova

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class UsuarioData(
    val id: Int,
    val nombre: String,
    val email: String,
    val rol: String
)

// Respuesta para el login normal y con Google
data class LoginResponse(
    val status: String,
    val message: String?,
    val rol: String?,
    val usuario: UsuarioData?
)

// 2. Interfaz de las peticiones a la API
interface PsicoNovaApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun iniciarSesion(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("obtener_perfil.php")
    suspend fun obtenerPerfil(
        @Query("email") email: String
    ): ProfileResponse

    // Nueva petición para registro
    @FormUrlEncoded
    @POST("registro.php")
    suspend fun registrarUsuario(
        @Field("nombre") nombre: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("rol") rol: String,
        @Field("cedula_profesional") cedula: String? = null,
        @Field("especialidad") especialidad: String? = null,
        @Field("precio_sesion") precio: Double? = null
    ): LoginResponse

    @FormUrlEncoded
    @POST("google_login.php")
    suspend fun loginConGoogle(
        @Field("id_token") idToken: String,
        @Field("rol") rol: String = "cliente"
    ): LoginResponse
}

// 3. Cliente Retrofit (Recuerda usar 10.0.2.2 para el emulador de Android Studio)
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2/psiconovaapi/"

    val apiService: PsicoNovaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PsicoNovaApiService::class.java)
    }
}