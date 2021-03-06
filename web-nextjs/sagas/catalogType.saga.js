import * as effects from 'redux-saga/effects'
import * as actions from '../actions/catalogType.actions'
import { revokeAccessToken } from '../actions/identity.actions'
import catalogApi from '../api/catalog.api'

function * loadCatalogTypesWorker() {
    const { catalogTypeReducer: { catalogTypes } } = yield effects.select()
    if (!catalogTypes || catalogTypes.length === 0) {
        try {
            const { identityReducer: { accessToken } } = yield effects.select()
            const options = { headers: { 'Authorization': `Bearer ${accessToken}` } }
            const res = yield effects.call(catalogApi.getCatalogTypes, options)
            if (res.status !== 200) {
                throw res.status
            }
            const data = yield res.json()
            yield effects.put(actions.loadCatalogTypesSuccess(data))
        } catch(err) {
            if (err === 401) {
                yield effects.put(revokeAccessToken())
            } else {
                yield effects.put(actions.loadCatalogTypesFailure(err))
            }
            
        }
    }
}

function * getSelectedCatalogTypeIds() {
    yield effects.call(loadCatalogTypesWorker)
    const { catalogTypeReducer: { catalogTypes } } = yield effects.select()
    const catalogTypeIds = catalogTypes.filter((catalogType) => catalogType.isSelected).map((catalogType) => catalogType.id)
    return catalogTypes.length == catalogTypeIds.length ? [] : catalogTypeIds
}

function * updateSelectedCatalogType(action) {
    const { catalogType, isSelected } = action.payload
    const { catalogTypeReducer: { catalogTypes } } = yield effects.select()
    catalogType.isSelected = isSelected
    const idx = catalogTypes.findIndex(c => c.id === catalogType.id)
    catalogTypes[idx] = catalogType
    yield effects.put(actions.loadCatalogTypesSuccess(catalogTypes))
}

export { getSelectedCatalogTypeIds }
export { loadCatalogTypesWorker }
export { updateSelectedCatalogType }