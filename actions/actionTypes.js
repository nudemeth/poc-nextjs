import aboutActionTypes from './about.actions'
import catalogActionTypes from './catalog.actions'
import categoryActionTypes from './category.actions'

export default {
    ...aboutActionTypes,
    ...catalogActionTypes,
    ...categoryActionTypes
};