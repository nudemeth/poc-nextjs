import aboutActionTypes from './about.actions'
import catalogActionTypes from './catalog.actions'
import catalogTypeActionTypes from './catalogType.actions'

export default {
    ...aboutActionTypes,
    ...catalogActionTypes,
    ...catalogTypeActionTypes
};