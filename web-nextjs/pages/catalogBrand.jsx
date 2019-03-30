import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import { loadCatalogBrands } from '../actions/catalogBrand.actions'
import CatalogBrandList from '../components/page/catalogBrand/CatalogBrandList'
import { storeUser, storeAuthSites } from '../actions/identity.actions'

class CatalogBrand extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, query } }) {
        store.dispatch(loadCatalogBrands())
        if (query.user) {
            store.dispatch(storeUser(query.user))
        }
        if (query.sites) {
            store.dispatch(storeAuthSites(query.sites))
        }
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired
    }

    componentDidMount() {
        this.props.dispatch(loadCatalogBrands())
    }

    render() {
        return (
            <Container title='Catalog Brand' header={<div/>}>
                <CatalogBrandList/>
            </Container>
        )
    }
}

export default connect()(CatalogBrand)
export { CatalogBrand }