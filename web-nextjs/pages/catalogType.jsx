import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import { loadCatalogTypes } from '../actions/catalogType.actions'
import CatalogTypeList from '../components/page/catalogType/CatalogTypeList'

class CatalogType extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store } }) {
        store.dispatch(loadCatalogTypes())
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired
    }

    componentDidMount() {
        this.props.dispatch(loadCatalogTypes())
    }

    render() {
        return (
            <Container title='Catalog Type' header={<div/>}>
                <CatalogTypeList/>
            </Container>
        )
    }
}

export default connect()(CatalogType)
export { CatalogType }