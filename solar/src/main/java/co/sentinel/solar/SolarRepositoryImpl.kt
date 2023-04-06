package co.sentinel.solar

import android.content.Context
import co.sentinel.dvpn.domain.core.exception.Failure
import co.sentinel.dvpn.domain.core.functional.Either
import co.sentinel.dvpn.domain.core.functional.requireLeft
import co.sentinel.dvpn.domain.core.functional.requireRight
import co.sentinel.dvpn.domain.features.solar.model.Continent
import co.sentinel.dvpn.domain.features.solar.model.request.GetCountriesByContinentRequest
import co.sentinel.dvpn.domain.features.solar.model.request.GetNodesByAddressRequest
import co.sentinel.dvpn.domain.features.solar.model.request.GetNodesRequest
import co.sentinel.dvpn.domain.features.solar.model.result.FetchContinentsResult
import co.sentinel.dvpn.domain.features.solar.model.result.FetchCountriesResult
import co.sentinel.dvpn.domain.features.solar.model.result.FetchNodesResult
import co.sentinel.dvpn.domain.features.solar.source.SolarRepository
import co.sentinel.solar.core.api.DVPNService
import co.sentinel.solar.core.mapper.NodeMapper
import co.sentinel.solar.core.model.CountryData
import co.sentinel.solar.core.util.jsonToClass
import co.sentinel.solar.core.util.safeApiCall
import timber.log.Timber


class SolarRepositoryImpl(
    private val context: Context,
    private val dvpnService: DVPNService
) : SolarRepository {

    companion object {
        private val continentCodes = listOf(
            "AF",
            "SA",
            "NA",
            "AS",
            "EU",
            "OC",
            "AN"
        )
    }

    override suspend fun fetchNodes(request: GetNodesRequest?): Either<Failure, FetchNodesResult> =
        kotlin.runCatching {
            safeApiCall {
                dvpnService.getNodes(
                    continent = request?.continent,
                    country = request?.country,
                    status = request?.status.toString(),
                    minPrice = request?.minPrice,
                    maxPrice = request?.maxPrice,
                    orderBy = request?.orderBy?.toString(),
                    query = request?.query,
                    page = request?.page
                )
            }.let { result ->
                when {
                    result.isRight -> {
                        val response = result.requireRight()

                        response.data.mapNotNull { node ->
                            NodeMapper.map(node)
                        }.let {
                            Either.Right(
                                FetchNodesResult(
                                    currentPage = response.currentPage,
                                    data = it,
                                    total = response.total
                                )
                            )
                        }
                    }
                    else -> {
                        Either.Left(result.requireLeft())
                    }
                }
            }
        }.onFailure { Timber.e(it) }.getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchNodesByAddress(request: GetNodesByAddressRequest): Either<Failure, FetchNodesResult> =
        kotlin.runCatching {
            safeApiCall { dvpnService.getNodesByAddress(request) }.let { result ->
                when {
                    result.isRight -> {
                        val response = result.requireRight()

                        response.data.mapNotNull { node ->
                            NodeMapper.map(node)
                        }.let {
                            Either.Right(
                                FetchNodesResult(
                                    currentPage = response.currentPage,
                                    data = it,
                                    total = response.total
                                )
                            )
                        }
                    }
                    else -> {
                        Either.Left(result.requireLeft())
                    }
                }
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchCountries(): Either<Failure, FetchCountriesResult> =
        kotlin.runCatching {
            safeApiCall { dvpnService.getCountries() }.let { result ->
                when {
                    result.isRight -> {
                        Either.Right(FetchCountriesResult(result.requireRight()))
                    }
                    else -> {
                        Either.Left(result.requireLeft())
                    }
                }
            }
        }.onFailure { Timber.e(it) }
            .getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchContinents(): Either<Failure, FetchContinentsResult> =
        kotlin.runCatching {
            val continents = continentCodes.map { code ->
                Continent(code, 0)
            }

            val localCountries = getLocalCountries(context)
            fetchCountries().let { fetchCountriesResult ->
                when {
                    fetchCountriesResult.isRight -> {
                        val remoteCountries = fetchCountriesResult.requireRight().countries
                        remoteCountries.map { remoteCountry ->
                            val code = localCountries.firstOrNull {
                                it.alpha2.lowercase() == remoteCountry.code.lowercase()
                            }

                            code?.let { country ->
                                continents.firstOrNull { continent ->
                                    continent.code.lowercase() == country.continent.lowercase()
                                }?.let { continent ->
                                    continent.nodesCount += remoteCountry.nodesCount
                                }
                            }
                        }

                        Either.Right(FetchContinentsResult(continents))
                    }
                    else -> {
                        Either.Left(fetchCountriesResult.requireLeft())
                    }
                }
            }
        }.onFailure {
            Timber.e(it)
        }.getOrNull() ?: Either.Left(Failure.AppError)

    override suspend fun fetchCountriesByContinent(request: GetCountriesByContinentRequest): Either<Failure, FetchCountriesResult> =
        kotlin.runCatching {
            val localCountries = getLocalCountries(context)
                .filter { it.continent == request.continent }.map { it.alpha2.lowercase() }

            fetchCountries().let { fetchCountriesResult ->
                when {
                    fetchCountriesResult.isRight -> {
                        val remoteCountries = fetchCountriesResult.requireRight().countries.filter {
                            localCountries.contains(
                                it.code.lowercase()
                            )
                        }

                        Either.Right(FetchCountriesResult(remoteCountries))
                    }
                    else -> {
                        Either.Left(fetchCountriesResult.requireLeft())
                    }
                }
            }
        }.onFailure {
            Timber.e(it)
        }.getOrNull() ?: Either.Left(Failure.AppError)

    private fun getLocalCountries(context: Context): List<CountryData> {
        return context.jsonToClass<List<CountryData>>(R.raw.countries_data) ?: listOf()
    }
}