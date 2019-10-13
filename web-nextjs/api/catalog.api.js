import fetch from 'isomorphic-unfetch'
import Api from './api'
import config from '../config'

class CatalogApi extends Api {
    constructor(host) {
        super(host)
    }

    getItemById = async (id) => {
        return await fetch(`${this.host}items/${id}`)
    }

    getItems = async (catalogTypeIds = [], catalogBrandIds = []) => {
        const catalogTypesParam = catalogTypeIds.join()
        const catalogBrandsParam = catalogBrandIds.join()

        if (catalogTypesParam !== '' && catalogBrandsParam !== '') {
            return await this.tryFetch(`${this.host}items/types/${catalogTypesParam}/brands/${catalogBrandsParam}`)
        }

        if (catalogTypesParam !== '') {
            return await this.tryFetch(`${this.host}items/types/${catalogTypesParam}`)
        }

        if (catalogBrandsParam !== '') {
            return await this.tryFetch(`${this.host}items/brands/${catalogBrandsParam}`)
        }

        return await this.tryFetch(`${this.host}items`)
    }

    getCatalogTypes = async (options) => {
        return await this.tryFetch(`${this.host}catalogTypes`, options)
    }

    getCatalogBrands = async (options) => {
        return await this.tryFetch(`${this.host}catalogBrands`, options)
    }

    getItemImage = async (catalogItemId) => {
        return await this.tryFetch(`${this.host}items/${catalogItemId}/img`)
    }
}

export default new CatalogApi(config.api.catalog.uri)