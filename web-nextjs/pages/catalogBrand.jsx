import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import { loadCatalogBrands } from '../actions/catalogBrand.actions'
import CatalogBrandList from '../components/page/catalogBrand/CatalogBrandList'

class CatalogBrand extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store } }) {
        store.dispatch(loadCatalogBrands())
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