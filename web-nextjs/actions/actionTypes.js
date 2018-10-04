import aboutActionTypes from './about.actions'
import catalogActionTypes from './catalog.actions'
import catalogTypeActionTypes from './catalogType.actions'
import catalogBrandActionTypes from './catalogBrand.actions'

export default {
    ...aboutActionTypes,
    ...catalogActionTypes,
    ...catalogTypeActionTypes,
    ...catalogBrandActionTypes
}