import aboutActionTypes from './about.actions'
import productActionTypes from './product.actions'
import categoryActionTypes from './category.actions'

export default {
    ...aboutActionTypes,
    ...productActionTypes,
    ...categoryActionTypes
};