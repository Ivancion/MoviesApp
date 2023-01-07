package com.blaze.moviesapp.data.local

import com.blaze.moviesapp.domain.models.ApiConfigResponse

interface IApiConfiguration {
    val configurationData: ApiConfigResponse

    fun setConfigurationData(configuration: ApiConfigResponse)
}

class ApiConfiguration: IApiConfiguration {
    private var _configurationData: ApiConfigResponse? = null
    override val configurationData: ApiConfigResponse
        get() = _configurationData!!

    override fun setConfigurationData(configuration: ApiConfigResponse) {
        _configurationData = configuration
    }

}