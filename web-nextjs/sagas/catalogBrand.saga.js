import * as effects from 'redux-saga/effects'
import * as actions from '../actions/catalogBrand.actions'
import { revokeAccessToken } from '../actions/identity.actions'
import catalogApi from '../api/catalog.api'

function * loadCatalogBrandsWorker() {
    const { catalogBrandReducer: { catalogBrands } } = yield effects.select()
    if (!catalogBrands || catalogBrands.length === 0) {
        try {
            const { identityReducer: { accessToken } } = yield effects.select()
            const options = { headers: { 'Authorization': `Bearer ${accessToken}` } }
            const res = yield effects.call(catalogApi.getCatalogBrands, options)
            if (res.status !== 200) {
                throw res.status
            }
            const data = yield res.json()
            yield effects.put(actions.loadCatalogBrandsSuccess(data))
        } catch(err) {
            if (err === 401) {
                yield effects.put(revokeAccessToken())
            } else {
                yield effects.put(actions.loadCatalogBrandsFailure(err))
            }
        }
    }
}

function * getSelectedCatalogBrandIds() {
    yield effects.call(loadCatalogBrandsWorker)
    const { catalogBrandReducer: { catalogBrands } } = yield effects.select()
    const catalogBrandIds = catalogBrands.filter((catalogBrand) => catalogBrand.isSelected).map((catalogBrand) => catalogBrand.id)
    return catalogBrands.length == catalogBrandIds.length ? [] : catalogBrandIds
}

function * updateSelectedCatalogBrand(action) {
    const { catalogBrand, isSelected } = action.payload
    const { catalogBrandReducer: { catalogBrands } } = yield effects.select()
    catalogBrand.isSelected = isSelected
    const idx = catalogBrands.findIndex(c => c.id === catalogBrand.id)
    catalogBrands[idx] = catalogBrand
    yield effects.put(actions.loadCatalogBrandsSuccess(catalogBrands))
}

export { getSelectedCatalogBrandIds }
export { loadCatalogBrandsWorker }
export { updateSelectedCatalogBrand }