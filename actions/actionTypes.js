import aboutActionTypes from './about.actions'
import itemActionTypes from './item.actions'
import categoryActionTypes from './category.actions'

export default {
    ...aboutActionTypes,
    ...itemActionTypes,
    ...categoryActionTypes
};