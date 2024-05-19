package kg.fuankan.tezcargo.data.network.api

import kg.fuankan.tezcargo.data.models.ApiResponse
import kg.fuankan.tezcargo.data.models.CargoCreateEdit
import kg.fuankan.tezcargo.data.models.CargoDesc
import kg.fuankan.tezcargo.data.models.CargoFilter
import kg.fuankan.tezcargo.data.models.DeliveryStatus
import kg.fuankan.tezcargo.data.models.DriverInfo
import kg.fuankan.tezcargo.data.models.StorageInfo
import kg.fuankan.tezcargo.data.models.StorageOption
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DeliveryApi {

    @POST("/api/v1/delivery")
    suspend fun createDelivery(@Body createRequest: CargoCreateEdit): ApiResponse

    @PUT("/api/v1/delivery")
    suspend fun editDelivery(@Query("deliveryId") deliveryId: Int, @Body editRequest: CargoCreateEdit): ApiResponse

    @GET("/api/v1/delivery")
    suspend fun getDeliveryById(@Query("id") deliveryId: Int): CargoDesc

    @PUT("/api/v1/delivery/status")
    suspend fun changeDeliveryStatus(@Query("deliveryId") deliveryId: Int, @Query("status") status: DeliveryStatus): ApiResponse

    @POST("/api/v1/delivery/all/filtered")
    suspend fun getFilteredDeliveries(@Body cargoFilter: CargoFilter): List<CargoDesc>

    @GET("/api/v1/options/storages")
    suspend fun getStorages(): List<StorageOption>

    @GET("/api/v1/storage")
    suspend fun getStorageInfoById(@Query("id") storageId: Int): StorageInfo

    @PUT("/api/v1/driver/verify")
    suspend fun verifyDriver(@Query("driverId") driverId: Int, @Query("isVerified") isVerified: Boolean): ApiResponse

    @GET("/api/v1/driver/requests")
    suspend fun getDriverRequests(): List<DriverInfo>

    @GET("/api/v1/driver/accepted")
    suspend fun getAcceptedDrivers(): List<DriverInfo>

    @GET("/api/v1/options/accounting")
    suspend fun getAccounting(@Query("type") type: String): ApiResponse
}