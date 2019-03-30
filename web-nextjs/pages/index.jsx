/* eslint-disable no-console */
import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Container from '../components/layout/Container'
import Catalog from '../components/page/index/Catalog'
import HeaderContent from '../components/page/index/HeaderContent'
import { loadItems } from '../actions/catalog.actions'
import { storeUser, storeAuthSites } from '../actions/identity.actions'

class Index extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ ctx: { store, query } }) {
        store.dispatch(loadItems())
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
        this.props.dispatch(loadItems())
    }

    render() {
        return (
            <Container title='Index' header={<HeaderContent/>}>
                <Catalog/>
            </Container>
        )
    }
}

export default connect()(Index)
export { Index }